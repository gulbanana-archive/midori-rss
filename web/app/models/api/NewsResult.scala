package models {
  package object api {
    type NewsResult = Seq[NewsItem]
  }
}

package models.api {
  import java.net.URL
  import play.api.libs.json._
  import play.api.libs.functional.syntax._
  import org.joda.time.DateTime
  import models.JSON._
  
  case class NewsItem (
    link: URL,
    title: String, 
    read: Boolean,
    date: DateTime,
    feedLink: URL,
    feedTitle: String
  )
  
  object NewsItem {
    implicit val jsonFormat =
      (JsPath \ "link").format[URL] and
      (JsPath \ "title").format[String] and
      (JsPath \ "read").format[Boolean] and
      (JsPath \ "date").format[DateTime] and
      (JsPath \ "feed" \ "link").format[URL] and
      (JsPath \ "feed" \ "title").format[String] tupled
  }
}