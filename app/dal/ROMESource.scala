package dal

import scala.collection.JavaConversions.asScalaBuffer
import java.net.URL
import play.api._
import com.sun.syndication.io._
import com.sun.syndication.feed.synd._
import com.sun.syndication.fetcher.impl._
import org.joda.time._
import models._

class ROMESource extends FeedSource {
  val fetcher = new HttpClientFeedFetcher(new HashMapFeedInfoCache()) //XXX replace this with a custom cache, db-backed or created from the feed
  val ttl = Duration.standardHours(1) //XXX adapt this to the feed
  
  def retrieve(url: URL, lastCheck: DateTime) = {
    Logger.debug("retrieving uri %s, last checked at %s".format(url, lastCheck.toString()))
    
    val feed = fetcher.retrieveFeed(url)
    val title = feed.getTitle()
    val entries = feed.getEntries().map(_.asInstanceOf[SyndEntry])
    val pubDate = new DateTime(if (feed.getPublishedDate != null) feed.getPublishedDate else entries.head.getPublishedDate)
    
    if (pubDate.isAfter(lastCheck)) {
      Logger.debug("feed '%s' updated at %s".format(title, pubDate.toString()))
      val lastUpdate = DateTime.now
      val nextUpdate = lastUpdate.plus(ttl)
      Some(Feed(
        url, 
        title, 
        feed.getDescription(), 
        new URL(feed.getLink()), 
        lastUpdate, 
        nextUpdate, 
        Some(entries.map(asEntry))
      ))
    } else {
      Logger.debug("feed '%s' not updated since %s".format(title, pubDate.toString()))
      None
    }
  }
  
  private def asEntry(item: SyndEntry) = Entry(
    "", 
    new DateTime(item.getPublishedDate()), 
    item.getTitle(), 
    new URL(item.getLink()), 
    item.getContents().toString()
  ) 
}