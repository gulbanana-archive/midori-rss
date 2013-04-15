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
import models._package._

trait MongoDAOComponent extends DAOComponent {
  val dao = new MongoDAO()

  class MongoDAO extends AsyncDAO {
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
    
    def markRead(user: User, feed: URL, entry: URL) : Future[Boolean] = {
      val selector = Json.obj(
        "username" -> user.username,
        "subscriptions.feed" -> feed
      ) 
      val update = Json.obj("$addToSet" -> Json.obj(
        "subscriptions.$.entries" -> entry
      ))
      
      users
        .update(selector, update)
        .map(checkError)
    }
    
    def markUnread(user: User, feed: URL, entry: URL) : Future[Boolean] = {
      val selector = Json.obj(
        "username" -> user.username,
        "subscriptions.feed" -> feed
      ) 
      val update = Json.obj("$pull" -> Json.obj(
        "subscriptions.$.entries" -> entry
      ))
      
      users
        .update(selector, update)
        .map(checkError)
    }
    
    
    def deleteAllUsers() = users.drop()
    
    /*******************
     * feed repository *
     *******************/
    def createFeed(feed: Feed) = feeds.insert(Json.toJson(feed)).map(checkError)
    
    def createFeed(url: URL) = {
      val insert = Feed(
        FeedInfo(
	      url, 
	      "Unknown Feed", 
	      "No description.",
	      new URL("http://"),
	      new DateTime(1970, 1, 1, 1, 1),
	      DateTime.now
        ),
        Seq()
      )
      
      feeds
        .insert(Json.toJson(insert))
        .map(checkError)
    }
    
    def getExpiredFeeds(at: DateTime): Future[Seq[FeedInfo]] = {
      val query = QueryBuilder()
        .query(Json.obj(
          "info.nextUpdate" -> Json.obj("$lte" -> at)
        ))
        .projection(Json.obj(
          "entries" -> 0
        ))
      
      feeds
        .find[JsValue](query)
        .toList
        .map(_.map(feed => (feed \ "info").as[FeedInfo])
      )
    }
    
    def getSubscribedFeeds(user: User): Future[Seq[Feed]] = {
      val feedQuery = QueryBuilder()
        .query(Json.obj(
          "info.url" -> Json.obj(
            "$in" -> user.subscriptions.map(_.feed)
        )))
      
      feeds
        .find[JsValue](feedQuery)
        .toList
        .map(_.map(feed => feed.as[Feed])
      )
    }
    
    def updateFeed(feed: Feed): Future[Boolean] = {
      val selector = Json.obj("info.url" -> feed.info.url) 
      val update = Json.obj("$set" -> Json.obj(
        "info" -> Json.toJson(feed.info)
      ), "$addToSet" -> Json.obj(
        "entries" -> Json.obj("$each" -> feed.entries)
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
}