package controllers

import scala.concurrent._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import dal._
import models._

trait Authenticator { this: Controller with DAOComponent =>
  def Authenticated(body: User => Future[Result]) = {
    dao.tryGetUser("banana").flatMap(_ match {
      case None => Future.successful(Unauthorized("Predefined user 'banana' does not exist"))
      case Some(user) => body(user)
    })
  }
}