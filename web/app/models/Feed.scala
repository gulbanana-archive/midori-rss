package models

import java.net.URL
import org.joda.time.DateTime

case class Feed (
  url: URL,
  title: String,
  description: String,
  link: URL,
  lastUpdate: DateTime,
  nextUpdate: DateTime,
  entries: Option[Seq[Entry]]
)