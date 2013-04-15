package controllers.api

import scala.concurrent._
import play.api.mvc._
import play.api.libs.json._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import controllers._
import dal._
import models._

trait JSONRPC extends Controller { this: DAOComponent with Authenticator with Validator => 
  
  implicit class WrapsInResult[A : Writes](wrapped: A)  {
    def asJson = Ok(Json.toJson(wrapped)).as("application/json")
  }
  
  def RPC[Q: Reads, R: Writes](body: (User, Q) => Future[R]) = Validated { query: Q => 
    Async {
      Authenticated { user:User => 
        body(user, query).map(_.asJson)
      }
    }
  }
}