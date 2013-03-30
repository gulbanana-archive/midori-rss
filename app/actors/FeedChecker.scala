package actors

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import play.api._
import akka.actor.Actor
import com.sun.syndication.io._
import org.joda.time._
import dal._

class FeedChecker(dao: AsyncStorage) extends Actor {
  def receive = {
    case "check" => update
    case unknown => Logger.warn("FeedChecker received unknown message %s".format(unknown.toString))
  }
  
  private def update {
    Logger.info("updating feeds")
    
    Await.result(for(
      feedsToCheck <- dao.getExpiredFeeds(DateTime.now)
      //
    ) yield {
      Logger.info("%d feeds expired".format(feedsToCheck.size))
    }, 15 seconds)
  }
}