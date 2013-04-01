package models

import java.net.URL
import play.api.libs.json._
import play.api.libs.functional.syntax._
import org.joda.time.DateTime
import JSON._

case class Feed (
  url: URL,
  title: String,
  description: String,
  link: URL,
  lastUpdate: DateTime,
  nextUpdate: DateTime,
  entries: Option[Seq[Entry]]
)

object Feed {
  implicit val jsonFormat = (
    (JsPath \ "url").format[URL] and
    (JsPath \ "title").format[String] and
    (JsPath \ "description").format[String] and
    (JsPath \ "link").format[URL] and
    (JsPath \ "lastUpdate").format[DateTime] and
    (JsPath \ "nextUpdate").format[DateTime] and
    (JsPath \ "entries").formatNullable[Seq[Entry]]
  )(Feed.apply, unlift(Feed.unapply))
}