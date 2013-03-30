package dal

import java.net.URL
import org.joda.time._
import models._

trait FeedSource {
  def retrieve(url: URL, lastCheck: DateTime) : Feed
}