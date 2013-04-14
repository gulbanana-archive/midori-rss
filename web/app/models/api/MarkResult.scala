package models.api

import play.api.libs.json._
import play.api.libs.functional.syntax._
 
case class MarkResult (
  marked: Boolean
)
  
object MarkResult {
  implicit val jsonFormat = (JsPath \ "marked").format[Boolean].inmap(MarkResult.apply, unlift(MarkResult.unapply))
}