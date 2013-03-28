package models

import java.net.URI
import play.api.libs.json._
import play.api.libs.functional.syntax._
import org.joda.time.DateTime
import JSON._

case class Entry (
  id: String,    //this is the id in the external rss
  posted: DateTime,
  title: String,
  link: URI,
  content: String
)

object Entry {
  implicit val jsonFormat = (
    (JsPath \ "id").format[String] and
    (JsPath \ "posted").format[DateTime] and
    (JsPath \ "title").format[String] and
    (JsPath \ "link").format[URI] and
    (JsPath \ "content").format[String]
  )(Entry.apply, unlift(Entry.unapply))
}