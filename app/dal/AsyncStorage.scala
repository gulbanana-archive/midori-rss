package dal

import scala.concurrent.Future
import org.joda.time._
import models._

trait AsyncStorage {
  def createUser(user: User) : Future[Boolean]
  def tryGetUser(username: String) : Future[Option[User]]
  def deleteAllUsers() : Future[Boolean]
  
  def createFeed(feed: Feed) : Future[Boolean]
  def getSubscribedFeeds(user: User) : Future[Seq[Feed]]
  def getExpiredFeeds(at: DateTime) : Future[Seq[Feed]]
  def deleteAllFeeds() : Future[Boolean]
}