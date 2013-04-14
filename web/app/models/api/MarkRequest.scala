package models.api

import java.net.URL

case class MarkRequest (
  feed: URL,
  item: URL, 
  read: Boolean
)