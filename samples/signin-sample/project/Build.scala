import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "signin-sample"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
		"signin" % "signin_2.9.1" % "1.0-SNAPSHOT"
	)

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
		resolvers += "Local Play Repository" at "file:///c:/apps/play/repository/local"
	)

}
