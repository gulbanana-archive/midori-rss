import scala.concurrent.duration._
import play.api.Play.current
import play.api.libs.concurrent.Akka
import akka.actor._
import ioc._
import dal._
import rss._
import push._
import controllers._
import actors._
import rss.ROMERSSComponent

object Composer extends ReflectiveFactory {
  private val _checker = Akka.system.actorOf(Props(new FeedChecker with MongoDAOComponent with ROMERSSComponent), "checker")
  private val _notifier = Akka.system.actorOf(Props(new PushNotifier with MongoDAOComponent with ApplePushComponent), "notifier")
  private trait Actors extends ActorComponent {
    val checker = _checker
    val notifier = _notifier
  }
  
  private val checkInterval = current.configuration.getMilliseconds("midori.checkInterval").get.millis
  private val notifyInterval = current.configuration.getMilliseconds("midori.notifyInterval").get.millis

  override val roots = Map[Class[_], AnyRef](
    classOf[Web] -> new Web with MongoDAOComponent with Authenticator with Actors,
    classOf[Test] -> new Test with MongoDAOComponent,
    classOf[Bootstrapper] -> new Bootstrapper(checkInterval, notifyInterval) with Actors
  ) 
}

