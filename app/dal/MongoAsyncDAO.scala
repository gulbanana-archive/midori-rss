package dal

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits._
import play.api.Play.current
import play.api.libs.json._
import play.modules.reactivemongo._
import play.modules.reactivemongo.PlayBsonImplicits._
import reactivemongo.api._
import reactivemongo.bson.handlers.DefaultBSONHandlers._
import reactivemongo.core.commands.LastError
import models._

class MongoAsyncDAO extends AsyncDAO {
  val db = ReactiveMongoPlugin.db
  lazy val users = db("users")
  lazy val feeds = db("feeds")
  
  def tryGetUser() : Future[Option[User]] = {
    val userQuery = QueryBuilder().query(
      Json.obj("username" -> "banana")
    )
    
    users
      .find[JsValue](userQuery)
      .headOption()
      .map(_.map(_.as[User])
    )
  } 
  
  def getAllFeeds(user: User) : Future[Seq[Feed]] = {
    val feedQuery = QueryBuilder().query(
      Json.obj(
        "uri" -> Json.obj(
          "$in" -> new JsArray(user.subscriptions map {s => JsString(s.feed)})
        )
      )
    )
    
    feeds
      .find[JsValue](feedQuery)
      .toList
      .map(_.map(feed => feed.as[Feed])
    )
  }
  
  def createUser(user: User) = users.insert(Json.toJson(user)).map(checkError)
  
  def createFeed(feed: Feed) = feeds.insert(Json.toJson(feed)).map(checkError)
  
  //convert LastErrors to an exception paradigm
  private def checkError(error: LastError) = if (error.ok) {
    true
  } else {
    throw error 
  }
}



/*
 (for (
          encodedUser <- user; 
          decodedUser <- encodedUser.asOpt[User]
        ) yield { tryGetFeeds(decodedUser)}) getOrElse future {Seq.empty[JsValue]}*/