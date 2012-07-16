import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {
    val scalaVersion = "2.9.1"

    val appName         = "signin"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
            "org.openid4java" % "openid4java-consumer" % "0.9.6",
            "org.squeryl" %% "squeryl" % "0.9.5-2",
            "postgresql" % "postgresql" % "8.4-702.jdbc4"
            //,"org.scala-tools" % "javautils" % (scalaVersion+"-0.1")
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
    )

}
