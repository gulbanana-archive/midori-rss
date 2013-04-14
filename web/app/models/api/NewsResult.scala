package models.api

import java.net.URL
import org.joda.time.DateTime
  
case class NewsResultFeed (
  link: URL,
  title: String
)

case class NewsResult (
  link: URL,
  title: String, 
  posted: DateTime,
  read: Boolean,
  feed: NewsResultFeed
)