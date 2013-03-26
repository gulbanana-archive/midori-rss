package models

import play.api.libs.json._
import play.api.libs.functional.syntax._
import org.joda.time.DateTime

case class Feed (
  uri: String,
  description: String,
  next_update: DateTime,
  entries: Seq[Entry]
)

object Feed {
  implicit val jsonFormat = (
    (JsPath \ "uri").format[String] and
    (JsPath \ "description").format[String] and
    (JsPath \ "next_update").format[DateTime] and
    (JsPath \ "entries").format[Seq[Entry]]
  )(Feed.apply, unlift(Feed.unapply))
}