package models

import java.net.URI
import play.api.libs.json._
import play.api.libs.functional.syntax._
import reactivemongo.bson._

//JSON formatters for various native and BSON types
object JSON {
  //must use "$oid" extension in the path - attempted to build that in, untested
  //alternative: (__ \ "_id" \ "$oid").format[BSONObjectID]
  //$date, $int, $long and $double also exist
  implicit val objectIdFormat = Format[BSONObjectID](
    (JsPath \ "$oid").read[String] map {str => BSONObjectID(str)},
    Writes[BSONObjectID] { id => Json.obj("$oid" -> JsString(id.stringify)) }
  )
  
  implicit val uriFormat = Format[URI](
    (JsPath).read[String] map {str => new URI(str)},
    Writes[URI] { uri => JsString(uri.toString) } 
  )
}