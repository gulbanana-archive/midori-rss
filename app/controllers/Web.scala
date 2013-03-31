package controllers

import scala.concurrent._
import ExecutionContext.Implicits.global
import play.api._
import play.api.mvc._
import org.joda.time._
import models._
import dal._
import actors._

class Web extends Controller { this: DAOComponent with ActorComponent with Authenticator => 
  implicit val timeSort = Ordering.by[DateTime,Long](date => date.getMillis).reverse
  
  def index = Authenticated { implicit user => 
    paginatedItems(0, 20)
  }
  
  private def paginatedItems(skip: Int, take: Int)(implicit user: User) = for (
    feeds <- dao.getSubscribedFeeds(user)
  ) yield {
    Ok(views.html.index(
      user, 
      feeds
        .filter(feed => feed.entries.isDefined)
        .flatMap(feed => feed.entries.get.map(entry => Item(entry, feed, user.subscriptions.flatMap(_.entries).contains(entry.id))))
        .sorted(Ordering.by[Item,DateTime](item => item.entry.posted))
        .drop(skip)
        .take(take)
    ))
  }
}