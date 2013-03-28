package models

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Subscription (
  feed: String,
  entries: Seq[String]
)

object Subscription  {
  implicit val jsonFormat = (
    (JsPath \ "feed").format[String] and
    (JsPath \ "entries").format[Seq[String]]
  )(Subscription .apply, unlift(Subscription .unapply))
}