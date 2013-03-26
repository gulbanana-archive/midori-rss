package models

import play.api.libs.json._
import play.api.libs.functional.syntax._
import org.joda.time.DateTime

case class Entry (
  posted: DateTime,
  content: String,
  id: String		//this is the id in the external rss
)

object Entry {
  implicit val jsonFormat = (
    (JsPath \ "posted").format[DateTime] and
    (JsPath \ "content").format[String] and
    (JsPath \ "id").format[String] 
  )(Entry.apply, unlift(Entry.unapply))
}