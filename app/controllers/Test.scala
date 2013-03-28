package controllers

import scala.concurrent._
import ExecutionContext.Implicits.global
import org.joda.time._
import play.api._
import play.api.mvc._
import models._
import dal._

class Test(dao: AsyncStorage)  extends Controller {
  def delete = Action {
    Async {
      for (
        droppedUsers <- dao.deleteAllUsers();
        droppedFeeds <- dao.deleteAllFeeds()
      ) yield {
        if (droppedUsers && droppedFeeds) {
          Ok("Deleted MidorI database.")
        } else {
          Ok("Failed to delete MidorI database.")
        }
      }
    }
  }
  
  def create = Action {
    Async {      
      val insertions = Seq(
        dao.createUser(User(
	      "banana", 
	      Seq(
	        Subscription("homestuck", Seq()),
	        Subscription("skeet", Seq())
	      )
	    )),
	    dao.createFeed(Feed(
	      "homestuck",
	      "MS Paint Adventures",
	      DateTime.now(),
	      Seq()
	    )),
	    dao.createFeed(Feed(
	      "skeet",
	      "Jon Skeet",
	      DateTime.now(),
	      Seq()
	    ))
      )
      
      Future.reduce(insertions) { _ && _ } map { 
        if (_) { 
	      Ok("Created MidorI database.")
	    } else {
	      Ok("Failed to create MidorI database.")
	    }
      }
    }
  }
  
}
