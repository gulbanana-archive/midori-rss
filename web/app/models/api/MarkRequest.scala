package models.api

import play.api.libs.json._
import play.api.libs.functional.syntax._
 
case class MarkRequest (
  id: String, 
  set: Boolean
)
  
object MarkRequest {
  implicit val jsonFormat = (
    (JsPath \ "id").format[String] and
    (JsPath \ "set").format[Boolean] 
  )(MarkRequest.apply, unlift(MarkRequest.unapply))
}