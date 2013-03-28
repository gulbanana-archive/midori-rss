package ioc

trait Composer {
  protected val roots : Map[Class[_], AnyRef]
  protected val factories : Map[Class[_], ()=>AnyRef]
  def resolve[T](`class`: Class[T]) = roots(`class`).asInstanceOf[T]
  def resolver[T](`class`: Class[T]) = factories(`class`).asInstanceOf[()=>T]
}

