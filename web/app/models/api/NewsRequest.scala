package models.api

import org.joda.time.DateTime
 
case class NewsRequest (
  limit: Int, 
  read: Boolean,
  from: Option[DateTime],
  to: Option[DateTime]
)