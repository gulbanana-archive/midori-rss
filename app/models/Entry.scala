package models

import java.net.URL
import play.api.libs.json._
import play.api.libs.functional.syntax._
import org.joda.time.DateTime
import JSON._

case class Entry (
  posted: DateTime,
  title: String,
  link: URL,
  content: String
)

object Entry {
  implicit val jsonFormat = (
    (JsPath \ "posted").format[DateTime] and
    (JsPath \ "title").format[String] and
    (JsPath \ "link").format[URL] and
    (JsPath \ "content").format[String]
  )(Entry.apply, unlift(Entry.unapply))
}