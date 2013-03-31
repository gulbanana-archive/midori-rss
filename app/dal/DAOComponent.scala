package dal

import scala.concurrent.Future
import java.net.URL
import org.joda.time._
import models._

trait DAOComponent {
  def dao: AsyncDAO
  trait AsyncDAO {
    def createUser(user: User) : Future[Boolean]
    def tryGetUser(username: String) : Future[Option[User]]
    def markRead(user: User, item: Item) : Future[Boolean]
    def markUnread(user: User, item: Item) : Future[Boolean]
    def deleteAllUsers() : Future[Boolean]
  
    def createFeed(feed: Feed) : Future[Boolean]
    def createFeed(url: URL) : Future[Boolean]
    def getSubscribedFeeds(user: User) : Future[Seq[Feed]]
    def getExpiredFeeds(at: DateTime) : Future[Seq[Feed]]
    def updateFeed(feed: Feed) : Future[Boolean]
    def deleteAllFeeds() : Future[Boolean]
  }
}