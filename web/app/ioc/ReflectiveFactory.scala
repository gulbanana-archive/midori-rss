package ioc

trait ReflectiveFactory {
  protected val roots : Map[Class[_], AnyRef]
  def resolve[T](`class`: Class[T]) = roots(`class`).asInstanceOf[T]
}

