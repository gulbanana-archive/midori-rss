package actors

import akka.actor.ActorRef

trait ActorComponent {
  def checker: ActorRef
  def notifier: ActorRef
}