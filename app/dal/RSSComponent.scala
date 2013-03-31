package dal

import java.net.URL
import org.joda.time._
import models._

trait RSSComponent {
  val source: FeedSource
  trait FeedSource {
    def retrieve(url: URL, lastCheck: DateTime) : Option[Feed]
  }
}