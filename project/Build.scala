import sbt._
import Keys._
import play.Project._
import cloudbees.Plugin._

object ApplicationBuild extends Build {

  val appName         = "playstartjava"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "org.webjars" % "webjars-play" % "2.1.0",
    "org.webjars" % "bootstrap" % "2.1.1",
    "org.webjars" % "html5shiv" % "3.6.1",
    "org.webjars" % "bootswatch" % "2.2.2+1",
    "org.webjars" % "bootstrap-datepicker" % "1.0.1",
    "org.webjars" % "bootstrap-timepicker" % "0.2.1",

    "securesocial" %% "securesocial" % "master-SNAPSHOT",
    "com.typesafe" %% "play-plugins-mailer" % "2.1-RC2",
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here    
    resolvers += Resolver.url("sbt-plugin-snapshots", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots/"))(Resolver.ivyStylePatterns)  
  ).settings(cloudBeesSettings :_*).settings(CloudBees.applicationId := Some("playstartjava"))

}
