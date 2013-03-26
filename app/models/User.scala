package models

import reactivemongo.bson._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import BSON._

case class User (
  username: String,
  subscriptions: Seq[Subscription]
)

object User {
  implicit val jsonFormat = (
    (JsPath \ "username").format[String] and
    (JsPath \ "subscriptions").format[Seq[Subscription]]
  )(User.apply, unlift(User.unapply))
}