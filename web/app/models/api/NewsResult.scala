package models.api

import java.net.URL
import org.joda.time.DateTime
  
case class NewsResultFeed (
  link: URL,
  title: String,
  url: URL
)

case class NewsResult (
  link: URL,
  title: String, 
  posted: DateTime,
  read: Boolean,
  feed: NewsResultFeed
)

object NewsResult {
  implicit val ordering : Ordering[NewsResult] = Ordering.by[NewsResult,Long](res => res.posted.getMillis).reverse
}