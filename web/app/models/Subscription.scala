package models

import java.net.URL

case class Subscription (
  feed: URL,
  entries: Seq[String]
)