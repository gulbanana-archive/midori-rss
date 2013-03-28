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
  
  override def onStop(app: Application) {
    scheduledUpdate.cancel()
  }
  
  override def getControllerInstance[T](`class`: Class[T]) = MidorIComposer.resolve(`class`)
  
  private def getActor[T <: Actor](`class`: Class[T]) = Akka.system.actorOf(Props(MidorIComposer.resolver(`class`)))
}