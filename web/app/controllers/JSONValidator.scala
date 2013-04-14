package controllers

import scala.concurrent._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._

trait JSONValidator { this: Controller =>
  //probably don't need to use this. standard Validated supports Async{} bodies; the 
  //difference is that ValidatedAsync does the actual validation asynchronously 
  def ValidatedAsync[A](action: A => Future[Result])(implicit reader: Reads[A]): EssentialAction = {
    Action(parse.json) { implicit request =>
      Async {
        request.body.validate[A].fold(
          valid = query => action(query),
          invalid = e => Future.successful(BadRequest(JsError.toFlatJson(e)).as("application/json"))
        )
      }
    }
  }
    
  //in this version validation is up-front before any potential async block is dispatched 
  //to an actor. however, this is probably what we want; nothing about the validation 
  //itself is nonblocking, and the body parser is similarly synchronous
  def Validated[A](action: A => Result)(implicit reader: Reads[A]): EssentialAction = {
    Action(parse.json) { implicit request =>
      request.body.validate[A].fold(
        valid = query => action(query),
        invalid = e => BadRequest(JsError.toFlatJson(e)).as("application/json")
      )
    }
  }
  
  //use on a model class to turn it into a json result
  implicit class Encodable[A : Writes](wrapped: A)  {
    def asJson = Ok(Json.toJson(wrapped)).as("application/json")
  }
}