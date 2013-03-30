package dal

import java.net.URL
import play.api._
import com.sun.syndication.io._
import com.sun.syndication.feed.synd._
import com.sun.syndication.fetcher.impl._
import org.joda.time._


class ROMESource extends FeedSource {
  val fetcher = new HttpClientFeedFetcher(new HashMapFeedInfoCache()) //XXX replace this with a custom cache, db-backed or created from the feed
  
  def retrieve(url: URL, lastCheck: DateTime) = {
    Logger.info("retrieving uri %s, last checked at %s".format(url, lastCheck.toString()))
    val feed = fetcher.retrieveFeed(url)
    Logger.info("retrieved feed: %s".format(feed.getTitle()))
    null
  }
}