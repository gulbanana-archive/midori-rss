import play.api._

object Global extends GlobalSettings {
  override def onStart(app: Application) {
    Logger.info("onStart")
  }  
  
  override def onStop(app: Application) {
    Logger.info("onStop")
  }
  
  override def getControllerInstance[T](clazz: Class[T]) = MidorIComposer.resolve(clazz).asInstanceOf[T]
}