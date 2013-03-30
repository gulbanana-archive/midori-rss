package dal

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits._
import java.net.URL
import play.api.Play.current
import play.api.libs.json._
import play.modules.reactivemongo._
import play.modules.reactivemongo.PlayBsonImplicits._
import reactivemongo.api._
import reactivemongo.bson.handlers.DefaultBSONHandlers._
import reactivemongo.core.commands.LastError
import org.joda.time._
import models._
import models.JSON._

class MongoStorage extends AsyncStorage {
  val db = ReactiveMongoPlugin.db
  lazy val users = db("users")
  lazy val feeds = db("feeds")

  /*******************
   * user repository *
   *******************/
  def createUser(user: User) = users.insert(Json.toJson(user)).map(checkError)
  
  def tryGetUser(username: String): Future[Option[User]] = {
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
  
  def deleteAllUsers() = users.drop()
  
  /*******************
   * feed repository *
   *******************/
  def createFeed(feed: Feed) = feeds.insert(Json.toJson(feed)).map(checkError)
  
  def createFeed(url: URL) = {
    val insert = Feed(
      url, 
      "Unknown Feed", 
      "No description.",
      new URL("http://"),
      new DateTime(1970, 1, 1, 1, 1),
      DateTime.now,
      None
    )
    
    feeds
      .insert(Json.toJson(insert))
      .map(checkError)
  }
  
  def getExpiredFeeds(at: DateTime): Future[Seq[Feed]] = {
    val query = QueryBuilder()
      .query(Json.obj(
        "nextUpdate" -> Json.obj("$lte" -> at)
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
  
  def getSubscribedFeeds(user: User): Future[Seq[Feed]] = {
    val feedQuery = QueryBuilder()
      .query(Json.obj(
        "url" -> Json.obj(
          "$in" -> user.subscriptions.map(_.feed)
      )))
    
    feeds
      .find[JsValue](feedQuery)
      .toList
      .map(_.map(feed => feed.as[Feed])
    )
  }
  
  def updateFeed(feed: Feed): Future[Boolean] = {
    val selector = Json.obj("url" -> feed.url) 
    val update = Json.obj("$set" -> Json.obj(
      "title" -> feed.title,
      "description" -> feed.description,
      "link" -> feed.link,
      "lastUpdate" -> feed.lastUpdate,
      "nextUpdate" -> feed.nextUpdate
    ), "$addToSet" -> Json.obj(
      "entries" -> Json.obj("$each" -> feed.entries.getOrElse[Seq[Entry]](Seq.empty[Entry]))
    ))
    
    feeds
      .update(selector, update)
      .map(checkError)
  }
  
  def deleteAllFeeds() = feeds.drop()
  
  //convert LastErrors to an exception paradigm
  private def checkError(error: LastError) = if (error.ok) {
    true
  } else {
    throw error 
  }
}