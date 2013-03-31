import play.api._

object Global extends GlobalSettings {
  //called every time we use an @route
  override def getControllerInstance[T](`class`: Class[T]) = Composer.resolve(`class`)
  
  override def onStart(app: Application) {
    Composer.resolve(classOf[actors.Bootstrapper]).bootstrap()
  }  
}