package controllers

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.mvc._
import dal._
import models._
import ioc._

trait Authenticator { this: Controller with DAOComponent =>
  def Authenticated(f: (User) => Future[Result]): Action[AnyContent] = {
    Action { request =>
      Async {
        dao.tryGetUser("banana").flatMap(_ match {
          case None => Future.successful(Unauthorized("Predefined user 'banana' does not exist"))
          case Some(user) => f(user)
        })
      }
    }
  }
}