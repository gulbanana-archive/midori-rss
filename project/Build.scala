import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "MidorI"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "rome" % "rome" % "1.0",
    "org.reactivemongo" %% "reactivemongo" % "0.8"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
