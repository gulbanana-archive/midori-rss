import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "MidorI"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    //"com.google.inject" % "guice" % "3.0",
    "rome" % "rome" % "1.0",
    "rome" % "rome-fetcher" % "1.0",
    "org.reactivemongo" %% "play2-reactivemongo" % "0.8"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
