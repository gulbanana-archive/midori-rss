package actors

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import play.api._
import akka.actor.Actor
import com.sun.syndication.io._
import org.joda.time._
import dal._
import push._

abstract class PushNotifierMessage
case class Notify() extends PushNotifierMessage

class PushNotifier extends Actor { this: DAOComponent with PushComponent =>
  def receive = {
    case Notify() => update
    case unknown => Logger.warn("PushNotifier received unknown message %s".format(unknown.toString))
  }
  
  private def update {
    Logger.info("sending push notifications")
  }
}