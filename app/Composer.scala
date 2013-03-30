import scala.concurrent.duration._
import play.api.Play.current
import play.api.libs.concurrent.Akka
import akka.actor._
import ioc._
import dal._
import controllers._
import actors._

object MidorIComposer extends Composer {
  private val db = new MongoStorage()
  private val rss = new ROMESource()
  private val checker = Akka.system.actorOf(Props(new FeedChecker(db, rss)), "checker")
  private val notifier = Akka.system.actorOf(Props(new PushNotifier(db)), "notifier")

  override val roots = Map[Class[_], AnyRef](
    classOf[Web] -> new Web(db, checker),
    classOf[Test] -> new Test(db),
    classOf[Bootstrapper] -> new Bootstrapper(
      checker, 
      notifier,
      current.configuration.getMilliseconds("midori.checkInterval").get.millis,
      current.configuration.getMilliseconds("midori.notifyInterval").get.millis
    )
  ) 
}

