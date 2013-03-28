import scala.concurrent.duration._
import play.api._
import play.api.Play.current
import play.api.libs.concurrent.Akka
import akka.actor._
import actors._

object Global extends GlobalSettings {
  private var scheduledUpdate : Cancellable = null
  private implicit val ec = scala.concurrent.ExecutionContext.global
  
  override def onStart(app: Application) {
    scheduledUpdate = Akka.system.scheduler.schedule(
      10 seconds, 5 minutes, 
      getActor(classOf[FeedChecker]), 
      "update"
    )
  }  
  
  private def getActor[T <: Actor](c: Class[T]) = Akka.system.actorOf(Props(MidorIComposer.resolver(c))) 
  
  override def onStop(app: Application) {
    scheduledUpdate.cancel()
  }
  
  override def getControllerInstance[T](clazz: Class[T]) = MidorIComposer.resolve(clazz)
}