package models

import play.api.libs.json._
import play.api.libs.functional.syntax._
import models._package._

package object api {
  implicit val newsRequestFormat = Json.format[NewsRequest]
  implicit val newsResultFeedFormat = Json.format[NewsResultFeed]
  implicit val newsResultFormat = Json.format[NewsResult]
  implicit val markRequestFormat = Json.format[MarkRequest]
  implicit val markResultFormat = (JsPath \ "marked").format[Boolean].inmap(MarkResult.apply, unlift(MarkResult.unapply))
}