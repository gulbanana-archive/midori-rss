package models

case class Item(
  entry: Entry, 
  feed: FeedInfo,
  read: Boolean
)