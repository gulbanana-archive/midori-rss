package test

import play.api.libs.json._
import controllers._
import models._

object interactive {
  
  val u = User("th0mas", "otherThing")            //> u  : models.User = User(th0mas,otherThing)
  val u_json = Json.toJson(u)                     //> u_json  : <error> = {"username":"th0mas","other":"otherThing"}
  val u2 = Json.fromJson[User](u_json).get        //> u2  : <error> = User(th0mas,otherThing)
}