package controllers

import scala.concurrent._
import ExecutionContext.Implicits.global
import play.api._
import play.api.mvc._
import org.joda.time._
import models._
import dal._
import akka.actor.ActorRef

class Web(dao: AsyncStorage, feedChecker: ActorRef) extends Controller {
  implicit val timeSort = Ordering.by[DateTime,Long](date => date.getMillis).reverse
  
  def index = Action {
    Async {      
      for (
        user <- dao.tryGetUser("banana");
        feeds <- user.map(dao.getSubscribedFeeds) getOrElse Future.successful(Seq.empty[Feed])
      ) yield {
        Ok(views.html.index(
          user.get, 
          feeds
            .filter(feed => feed.entries.isDefined)
            .flatMap(feed => feed.entries.get.map(entry => (entry, feed)))
            .sorted(Ordering.by[(Entry,Feed),DateTime](item => item._1.posted))
            .take(20)
        ))
      }
    }
  }
}