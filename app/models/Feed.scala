package models

import java.net.URI
import play.api.libs.json._
import play.api.libs.functional.syntax._
import org.joda.time.DateTime
import JSON._

case class Feed (
  uri: String,
  title: String,
  description: String,
  link: URI,
  nextUpdate: DateTime,
  entries: Option[Seq[Entry]]
)

object Feed {
  implicit val jsonFormat = (
    (JsPath \ "uri").format[String] and
    (JsPath \ "title").format[String] and
    (JsPath \ "description").format[String] and
    (JsPath \ "link").format[URI] and
    (JsPath \ "nextUpdate").format[DateTime] and
    (JsPath \ "entries").formatNullable[Seq[Entry]]
  )(Feed.apply, unlift(Feed.unapply))
}