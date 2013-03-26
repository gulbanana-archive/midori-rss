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

object Web extends Controller with MongoController {
  lazy val db = ReactiveMongoPlugin.db
  lazy val users = db("users")
  lazy val feeds = db("feeds")

  def index = Action {
    Async {      
      for (
        user <- tryGetUser();
        feeds <- (for (
          encodedUser <- user; 
          decodedUser <- encodedUser.asOpt[User]
        ) yield { tryGetFeeds(decodedUser)}) getOrElse future {Seq.empty[JsValue]}
      ) yield {
        feeds map {_.as[Feed]}
        Ok("got %d feeds for user %s".format(feeds.size, user.get.as[User].username))
      }
    }
  }
  
  private def tryGetUser() : Future[Option[JsValue]] = {
    val userQuery = QueryBuilder().query(
      Json.obj("username" -> "banana")
    )
    
    users.find[JsValue](userQuery).headOption()
  } 
  
  private def tryGetFeeds(user: User) : Future[Seq[JsValue]] = {
    val feedQuery = QueryBuilder().query(
      Json.obj(
        "uri" -> Json.obj(
          "$in" -> new JsArray(user.subscriptions map {s => JsString(s.feed)})
        )
      )
    )
    
    feeds.find[JsValue](feedQuery).toList
  }
    

}
