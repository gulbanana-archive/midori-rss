package models.api

import play.api.libs.json._
import play.api.libs.functional.syntax._
import org.joda.time.DateTime
 
case class NewsRequest (
  limit: Int, 
  read: Boolean,
  from: Option[DateTime],
  to: Option[DateTime]
)
  
object NewsRequest {
  implicit val jsonFormat = 
    (JsPath \ "limit").format[Int] and
    (JsPath \ "read").format[Boolean] and
    (JsPath \ "from").formatNullable[DateTime] and
    (JsPath \ "to").formatNullable[DateTime] tupled 
}