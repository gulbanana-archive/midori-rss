package models.api

import java.net.URL
import org.joda.time.DateTime
  
case class NewsResultFeed (
  link: URL,
  title: String
)

case class NewsResult (
  date: DateTime,
  read: Boolean,
  link: URL,
  title: String, 
  feed: NewsResultFeed
)