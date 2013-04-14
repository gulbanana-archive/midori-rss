package models

import java.net.URL
import play.api.libs.json._
import play.api.libs.functional.syntax._
import reactivemongo.bson._

object _package {
  //must use "$oid" extension in the path - attempted to build that in, untested
  //alternative: (__ \ "_id" \ "$oid").format[BSONObjectID]
  //$date, $int, $long and $double also exist
  implicit val objectIdFormat = Format[BSONObjectID](
    (JsPath \ "$oid").read[String] map {str => BSONObjectID(str)},
    Writes[BSONObjectID] { id => Json.obj("$oid" -> JsString(id.stringify)) }
  )
  
  implicit val urlFormat = Format[URL](
    (JsPath).read[String] map {str => new URL(str)},
    Writes[URL] { url => JsString(url.toString) } 
  )
  
  implicit val entryFormat = Json.format[Entry]
  implicit val feedFormat = Json.format[Feed]
  implicit val subscriptionFormat = Json.format[Subscription]
  implicit val userFormat = Json.format[User]
  implicit val itemFormat = Json.format[Item]
}