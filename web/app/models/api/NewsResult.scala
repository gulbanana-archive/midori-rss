package models.api {
  import java.net.URL
  import play.api.libs.json._
  import play.api.libs.functional.syntax._
  import org.joda.time.DateTime
  import models.JSON._
  
  case class NewsResult (
    date: DateTime,
    read: Boolean,
    link: URL,
    title: String, 
    feedLink: URL,
    feedTitle: String
  )
  
  object NewsResult {
    implicit val jsonFormat = (
      (JsPath \ "date").format[DateTime] and
      (JsPath \ "read").format[Boolean] and     
      (JsPath \ "link").format[URL] and
      (JsPath \ "title").format[String] and
      (JsPath \ "feed" \ "link").format[URL] and
      (JsPath \ "feed" \ "title").format[String]
    )(NewsResult.apply, unlift(NewsResult.unapply))
  }
}