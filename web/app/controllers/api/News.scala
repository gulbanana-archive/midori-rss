package controllers.api

import scala.concurrent._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import controllers._
import actors._
import dal._
import models._
import models.api._

class News extends Controller { this: DAOComponent with ActorComponent with Authenticator with JSONValidator => 
  def get = Validated { query: NewsRequest =>
    Seq[NewsItem]().asJson
  }
  
  def set = Validated { query: MarkRequest => 
    Async {
      Authenticated { user:User => 
        Future.successful(user.asJson)
      }
    }
  }
}