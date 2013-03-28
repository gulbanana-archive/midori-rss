import ioc._
import dal._
import controllers._
import actors._

object MidorIComposer extends Composer {
  private val db = new MongoStorage()
  private val rss = new ROMESource()
  
  override val roots = Map[Class[_], AnyRef](
      classOf[Web] -> new Web(db, rss),
      classOf[Test] -> new Test(db)
  ) 
  
  override val factories = Map[Class[_], () => AnyRef](
      classOf[FeedChecker] -> (() => new FeedChecker())
  )
}

