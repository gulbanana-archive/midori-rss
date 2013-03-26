package test

import play.api.libs.json._
import controllers._
import models._

object interactive {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(142); 
  
  val u = User("th0mas", "otherThing");System.out.println("""u  : models.User = """ + $show(u ));$skip(30); 
  val u_json = Json.toJson(u);System.out.println("""u_json  : <error> = """ + $show(u_json ));$skip(43); 
  val u2 = Json.fromJson[User](u_json).get;System.out.println("""u2  : <error> = """ + $show(u2 ))}
}
