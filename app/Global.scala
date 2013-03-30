import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import play.api._
import play.api.Play.current
import play.api.libs.concurrent.Akka
import akka.actor._
import actors._

object Global extends GlobalSettings {
  override def onStart(app: Application) {
    Akka.system.scheduler.schedule(
      5 seconds, current.configuration.getMilliseconds("midori.checkInterval").get millis, 
      getActor(classOf[FeedChecker]), 
      "check"
    )
    Akka.system.scheduler.schedule(
      5 seconds, current.configuration.getMilliseconds("midori.notifyInterval").get millis, 
      getActor(classOf[PushNotifier]), 
      "notify"
    )
  }  
  
  override def onStop(app: Application) {
  }
  
  //this is called every time we use an @route
  override def getControllerInstance[T](`class`: Class[T]) = MidorIComposer.resolve(`class`) 
  
  private def getActor[T <: Actor](`class`: Class[T]) = Akka.system.actorOf(Props(MidorIComposer.resolver(`class`)))
}