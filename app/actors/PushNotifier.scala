package actors

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import play.api._
import akka.actor.Actor
import com.sun.syndication.io._
import org.joda.time._
import dal._

class PushNotifier(dao: AsyncStorage) extends Actor {
  def receive = {
    case "notify" => update
    case unknown => Logger.warn("PushNotifier received unknown message %s".format(unknown.toString))
  }
  
  private def update {
    Logger.info("sending push notifications")
  }
}