import sbt._
import Keys._
import play.Project._
import cloudbees.Plugin._

object ApplicationBuild extends Build {

  val appName         = "playstartjava"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    "org.webjars" % "webjars-play" % "2.1.0-1",
    "org.webjars" % "bootstrap" % "2.3.1",
    "org.webjars" % "html5shiv" % "3.6.1",
    "org.webjars" % "bootswatch" % "2.3.1",
    "org.webjars" % "bootstrap-datepicker" % "1.0.1",
    "org.webjars" % "bootstrap-timepicker" % "0.2.1",

    "be.objectify"  %%  "deadbolt-java"     % "2.1-RC2",
    "com.feth"     %%  "play-authenticate" % "0.3.0-SNAPSHOT",
    "com.typesafe" %% "play-plugins-mailer" % "2.1-RC2",
    "postgresql"    %   "postgresql"        % "9.1-901-1.jdbc4",
    javaCore,
    javaJdbc,
    javaEbean
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here    
    resolvers += Resolver.url("sbt-plugin-snapshots", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots/"))(Resolver.ivyStylePatterns),  

    resolvers += Resolver.url("Objectify Play Repository (release)", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns),
    resolvers += Resolver.url("Objectify Play Repository (snapshot)", url("http://schaloner.github.com/snapshots/"))(Resolver.ivyStylePatterns),

    resolvers += Resolver.url("play-easymail (release)", url("http://joscha.github.com/play-easymail/repo/releases/"))(Resolver.ivyStylePatterns),
    resolvers += Resolver.url("play-easymail (snapshot)", url("http://joscha.github.com/play-easymail/repo/snapshots/"))(Resolver.ivyStylePatterns),	  

    resolvers += Resolver.url("play-authenticate (release)", url("http://joscha.github.com/play-authenticate/repo/releases/"))(Resolver.ivyStylePatterns),
    resolvers += Resolver.url("play-authenticate (snapshot)", url("http://joscha.github.com/play-authenticate/repo/snapshots/"))(Resolver.ivyStylePatterns)

  ).settings(cloudBeesSettings :_*).settings(CloudBees.applicationId := Some("playstartjava"))

}
