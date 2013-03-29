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
import org.joda.time._
import models._

class MongoStorage extends AsyncStorage {
  val db = ReactiveMongoPlugin.db
  lazy val users = db("users")
  lazy val feeds = db("feeds")
  
  def tryGetUser(username: String) : Future[Option[User]] = {
    val userQuery = QueryBuilder()
      .query(Json.obj(
        "username" -> username
      ))
    
    users
      .find[JsValue](userQuery)
      .headOption()
      .map(_.map(_.as[User])
    )
  } 
  
  def getSubscribedFeeds(user: User) : Future[Seq[Feed]] = {
    val feedQuery = QueryBuilder()
      .query(Json.obj(
        "uri" -> Json.obj(
          "$in" -> new JsArray(user.subscriptions map {s => JsString(s.feed)})
      )))
    
    feeds
      .find[JsValue](feedQuery)
      .toList
      .map(_.map(feed => feed.as[Feed])
    )
  }
  
  def getExpiredFeeds(at: DateTime) : Future[Seq[Feed]] = {
    val query = QueryBuilder()
      .query(Json.obj(
        "nextUpdate" -> Json.obj("$lte" -> Json.toJson(at))
      ))
      .projection(Json.obj(
        "entries" -> 0
      ))
    
    feeds
      .find[JsValue](query)
      .toList
      .map(_.map(feed => feed.as[Feed])
    )
  }
  
  def createUser(user: User) = users.insert(Json.toJson(user)).map(checkError)
  
  def createFeed(feed: Feed) = feeds.insert(Json.toJson(feed)).map(checkError)
  
  def deleteAllUsers() = users.drop()
  
  def deleteAllFeeds() = feeds.drop()
  
  //convert LastErrors to an exception paradigm
  private def checkError(error: LastError) = if (error.ok) {
    true
  } else {
    throw error 
  }
}