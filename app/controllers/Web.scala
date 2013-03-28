package controllers

import scala.concurrent._
import ExecutionContext.Implicits.global
import play.api._
import play.api.mvc._
import org.joda.time._
import models._
import dal._

class Web(dao: AsyncStorage, internet: FeedSource) extends Controller {
  implicit val timeSort = Ordering.by[DateTime,Long](date => date.getMillis)
  
  def index = Action {
    Async {      
      for (
        user <- dao.tryGetUser();
        feeds <- user.map(dao.getAllFeeds) getOrElse Future.successful(Seq.empty[Feed])
      ) yield {
        
        Ok(views.html.index(
            user.get, 
            feeds.flatMap(feed => 
              feed.entries.map(entry =>
                (entry, feed)
              )
            ).sorted(Ordering.by[(Entry,Feed),DateTime](item => item._1.posted))
        ))
      }
    }
  }
}