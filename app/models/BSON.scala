package models

import reactivemongo.bson._
import play.api.libs.json._
import play.api.libs.functional.syntax._

//formatters for native BSON types, in json
object BSON {
  //must use "$oid" extension in the path - attempted to build that in, untested
  //alternative: (__ \ "_id" \ "$oid").format[BSONObjectID]
  implicit val objectIdFormat = Format[BSONObjectID](
    (__ \ "$oid").read[String] map (BSONObjectID.apply _),
    Writes[BSONObjectID] { id => Json.obj("$oid" -> JsString(id.stringify)) }
  )
  
  //$date, $int, $long and $double also exist
}