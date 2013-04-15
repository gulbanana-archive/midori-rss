package models

case class Feed (
  info: FeedInfo,
  entries: Seq[Entry]
)