package ioc

trait Composer {
  protected val roots : Map[Class[_], AnyRef]
  def resolve(clazz: Class[_]) : AnyRef = roots(clazz) 
}

