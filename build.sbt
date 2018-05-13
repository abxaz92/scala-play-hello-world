name := "exampleplay"

version := "1.0"

lazy val `exampleplay` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq( jdbc , ehcache , ws , specs2 % Test , guice, evolutions )
libraryDependencies += "org.playframework.anorm" %% "anorm" % "2.6.1"
libraryDependencies += jdbc
libraryDependencies += evolutions
libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick"      % "2.1.0",
  "org.postgresql"     %  "postgresql" % "9.3-1102-jdbc41"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

      