package controllers.api

import scala.concurrent._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import controllers._
import actors._
import dal._
import models._
import models._package._
import models.api._
import scala.runtime.RichBoolean

class News extends Controller { this: DAOComponent with JSONRPC => 
  def get = RPC { (user:User, query:NewsRequest) =>
    future {Seq[NewsResult]()}
  }
  
  def set = RPC { (user:User, query:Seq[MarkRequest]) => 
    val successes = query.map { req =>
      if (req.read)
    	dao.markRead(user, req.feed, req.item)
      else
        dao.markUnread(user, req.feed, req.item)
    }
    
    Future
      .reduce(successes)(_ && _)
      .map(MarkResult.apply)
  }
}