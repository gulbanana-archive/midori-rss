package controllers

import scala.concurrent._

import org.joda.time._

import reactivemongo.api._
import reactivemongo.bson.handlers.DefaultBSONHandlers._

import play.modules.reactivemongo._
import play.modules.reactivemongo.PlayBsonImplicits._
import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.Play.current

import models._

object Test extends Controller with MongoController {
  lazy val db = ReactiveMongoPlugin.db
  lazy val users = db("users")
  lazy val feeds = db("feeds")

  def delete = Action {
    
    Async {   
      for (
        droppedUsers <- users.drop();
        droppedFeeds <- feeds.drop();
        droppedDB <- db.drop()
      ) yield {
        if (droppedDB) {
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
        users.insert(Json.toJson(User(
	      "banana", 
	      Seq(
	        Subscription("homestuck", Seq()),
	        Subscription("skeet", Seq())
	      )
	    ))),
	    feeds.insert(Json.toJson(Feed(
	      "homestuck",
	      "MS Paint Adventures",
	      DateTime.now(),
	      Seq()
	    ))),
	    feeds.insert(Json.toJson(Feed(
	      "skeet",
	      "Jon Skeet",
	      DateTime.now(),
	      Seq()
	    )))
      )
      
      Future.fold(insertions)("") { (currentErrors, insertion) =>  
        if(insertion.ok) {
          currentErrors
        } else {
          currentErrors + insertion.errMsg.get
        }
      } map { s => 
        Ok("Created MidorI database.\n%s".format(s))
      }
    }
  }
  
}
