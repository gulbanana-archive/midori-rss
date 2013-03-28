package ioc

import scala.reflect._

//this is not actually useful because pattern matching requires stable identifiers; at least until SI-884 is fixed 
case class Is[T: ClassTag]() {
  def unapply(clazz: Class[_]) : Boolean = clazz == classTag[T].getClass
}