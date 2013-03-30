package actors

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play.current
import play.api.libs.concurrent.Akka
import akka.actor._

//starts up the actor system
class Bootstrapper(checker: ActorRef, notifier: ActorRef, checkInterval: FiniteDuration, notifyInterval: FiniteDuration) {
  def bootstrap() {
    Akka.system.scheduler.schedule(5.seconds, checkInterval, checker, "check")
    Akka.system.scheduler.schedule(5.seconds, notifyInterval, notifier, "notify")
  }
}