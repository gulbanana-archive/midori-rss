package models.api

import play.api.libs.json._
import play.api.libs.functional.syntax._
import org.joda.time.DateTime 

case class MarkRequest (
  date: DateTime, 
  read: Boolean
)
  
object MarkRequest {
  implicit val jsonFormat = (
    (JsPath \ "date").format[DateTime] and
    (JsPath \ "read").format[Boolean] 
  )(MarkRequest.apply, unlift(MarkRequest.unapply))
}