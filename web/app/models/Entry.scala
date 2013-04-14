package models

import java.net.URL
import org.joda.time.DateTime

case class Entry (
  posted: DateTime,
  title: String,
  link: URL,
  content: String
)