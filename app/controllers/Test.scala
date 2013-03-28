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
import dal._

class Test(dao: AsyncStorage)  extends Controller with MongoController {
  val db = ReactiveMongoPlugin.db
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
