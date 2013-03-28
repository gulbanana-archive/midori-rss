package dal

import org.joda.time._
import models._

trait FeedSource {
  def retrieve(uri: String, lastCheck: DateTime) : Feed
}