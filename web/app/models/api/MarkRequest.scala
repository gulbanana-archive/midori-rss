package models.api

import org.joda.time.DateTime 

case class MarkRequest (
  date: DateTime, 
  read: Boolean
)