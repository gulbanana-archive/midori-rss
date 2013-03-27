package controllers

import scala.concurrent._
import ExecutionContext.Implicits.global
import play.api._
import play.api.mvc._
import models._

object Web extends Controller {
  val dao = Composer.resolveDAO 

  def index = Action {
    Async {      
      for (
        user <- dao.tryGetUser();
        feeds <- user.map(dao.getAllFeeds) getOrElse Future.successful(Seq.empty[Feed])
      ) yield {
        Ok("got %d feeds for user %s".format(feeds.size, user.get.username))
      }
    }
  }
}