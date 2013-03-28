package actors

import play.api._
import akka.actor.Actor
import com.sun.syndication.io._

class FeedChecker extends Actor {
  def receive = {
    case "update" => Logger.info("updating feeds")
    case unknown => Logger.warn("received unknown message %s".format(unknown.toString))
  }
}