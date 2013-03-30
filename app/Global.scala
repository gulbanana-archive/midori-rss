import play.api._

object Global extends GlobalSettings {
  //called every time we use an @route
  override def getControllerInstance[T](`class`: Class[T]) = MidorIComposer.resolve(`class`)
  
  override def onStart(app: Application) {
    MidorIComposer.resolve(classOf[actors.Bootstrapper]).bootstrap()
  }  
}