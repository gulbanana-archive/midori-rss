package ioc

trait Composer {
  protected val roots : Map[Class[_], AnyRef]
  protected val factories : Map[Class[_], Function0[AnyRef]]
  def resolve[T](clazz: Class[T]) = roots(clazz).asInstanceOf[T]
  def resolver[T](clazz: Class[T]) = factories(clazz).asInstanceOf[()=>T]
}

