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
  private val pageSize = 15
  
  def index = Action { 
    Async { 
      Authenticated { implicit user => 
        for (items <- paginatedItems(0, pageSize)) yield Ok(views.html.index(user,items))
      }
    }
  }
  
  def more = Action(parse.json) { request =>
    request.body.validate[Int].map {
      case start => Async { 
        Authenticated { implicit user => 
          for (items <- paginatedItems(start, pageSize)) yield Ok(views.html.items(items))
        }
      } 
    }.recoverTotal { error =>
      BadRequest("Invalid request body.")
    }
  }
  
  private def paginatedItems(skip: Int, take: Int)(implicit user: User) = for (feeds <- dao.getSubscribedFeeds(user)) yield feeds
    .filter(feed => feed.entries.isDefined)
    .flatMap(feed => feed.entries.get.map(entry => Item(entry, feed, user.subscriptions.flatMap(_.entries).contains(entry.id))))
    .sorted(Ordering.by[Item,Long](item => item.entry.posted.getMillis).reverse)
    .drop(skip)
    .take(take)
}