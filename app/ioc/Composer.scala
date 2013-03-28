package ioc

import scala.reflect._
import play.api.Play.current
import play.api.libs.concurrent.Akka
import akka.actor._

trait Composer {
  protected val roots : Map[Class[_], AnyRef]
  protected val factories : Map[Class[_], Function0[AnyRef]]
  
  def resolve[T](clazz: Class[T]) = roots(clazz).asInstanceOf[T]
  
  def resolver[T](clazz: Class[T]) = factories(clazz).asInstanceOf[()=>T]
  
  def resolveActor[T <: Actor](clazz: Class[T]) = Akka.system.actorOf(Props(resolver(clazz)))
}

