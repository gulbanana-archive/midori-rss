package models

import java.net.URL
import org.joda.time.DateTime

case class FeedInfo (
  url: URL,
  title: String,
  description: String,
  link: URL,
  lastUpdate: DateTime,
  nextUpdate: DateTime
)