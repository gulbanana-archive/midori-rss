package models

case class Item(
  entry: Entry, 
  feed: Feed,
  read: Boolean
)