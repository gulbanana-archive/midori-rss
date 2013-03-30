package actors

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import play.api._
import akka.actor._
import akka.pattern.pipe
import com.sun.syndication.io._
import org.joda.time._
import models._
import dal._

abstract class FeedCheckerMessage
case class CheckAll() extends FeedCheckerMessage
case class UpdateOne(feed: Feed) extends FeedCheckerMessage
case class UpdateMany(feeds: Seq[Feed]) extends FeedCheckerMessage

class FeedChecker(dao: AsyncStorage, source: FeedSource) extends Actor {
  def receive = {
    case CheckAll() => checkAll
    case UpdateOne(feed) => update(feed)
    case UpdateMany(feeds) => feeds.map(update)
    case unknown => Logger.warn("FeedChecker received unknown message %s".format(unknown.toString))
  }
  
  private def checkAll {
    dao
      .getExpiredFeeds(DateTime.now)
      .map(UpdateMany(_))
      .pipeTo(self)
  }
  
  private def update(before: Feed) {
    Logger.info("update %s".format(before.url))
    val after = source.retrieve(before.url, before.lastUpdate)
  }
}