package rss

import java.net.URL
import com.sun.syndication.fetcher.impl._

//XXX either implement this, or use a per-feed cache which is created from an inmemory Feed object
class MongoFeedFetcherCache extends FeedFetcherCache {
  def clear: Unit = ???
  def getFeedInfo(url: URL): SyndFeedInfo = ???
  def remove(url: URL): SyndFeedInfo = ???
  def setFeedInfo(url: URL, info: SyndFeedInfo): Unit = ???
}