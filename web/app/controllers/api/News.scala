package controllers.api

import scala.concurrent._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import controllers._
import actors._
import dal._
import models._
import models._package._
import models.api._
import scala.runtime.RichBoolean

class News extends Controller { this: DAOComponent with JSONRPC => 
  def get = RPC { (user:User, query:NewsRequest) =>
    for (feeds <- dao.getSubscribedFeeds(user)) yield feeds
      .filter(feed => feed.entries.isDefined)
      .flatMap(feed => feed.entries.get.map(entry => buildResult(user, feed, entry)))
      .filter(result => query.read || !result.read)
      .sorted
      .take(query.limit)
  }
  
  private def buildResult(user: User, feed: Feed, entry: Entry) = NewsResult(
    entry.link,
    entry.title,
    entry.posted,
    user
      .subscriptions
      .filter(sub => sub.feed==feed.url)
      .flatMap(_.entries)
      .contains(entry.link.toString),
    NewsResultFeed(
      feed.link,
      feed.title,
      feed.url
    )
  )
  
  def set = RPC { (user:User, query:Seq[MarkRequest]) => 
    val successes = query.map { req =>
      if (req.read)
    	dao.markRead(user, req.feed, req.item)
      else
        dao.markUnread(user, req.feed, req.item)
    }
    
    Future
      .reduce(successes)(_ && _)
      .map(MarkResult.apply)
  }
}