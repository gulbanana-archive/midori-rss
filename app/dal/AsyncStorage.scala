package dal

import scala.concurrent.Future
import models._

trait AsyncStorage {
  def tryGetUser() : Future[Option[User]]
  def getAllFeeds(user: User) : Future[Seq[Feed]]
  def createUser(user: User) : Future[Boolean]
  def createFeed(feed: Feed) : Future[Boolean]
  def deleteAllUsers() : Future[Boolean]
  def deleteAllFeeds() : Future[Boolean]
}