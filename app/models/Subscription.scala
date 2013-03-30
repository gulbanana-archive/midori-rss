package models

import java.net.URL
import play.api.libs.json._
import play.api.libs.functional.syntax._
import JSON._

case class Subscription (
  feed: URL,
  entries: Seq[String]
)

object Subscription  {
  implicit val jsonFormat = (
    (JsPath \ "feed").format[URL] and
    (JsPath \ "entries").format[Seq[String]]
  )(Subscription .apply, unlift(Subscription .unapply))
}