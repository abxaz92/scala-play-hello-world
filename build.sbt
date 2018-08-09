name := "exampleplay"

version := "1.0"

lazy val `exampleplay` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  jdbc,
  ehcache,
  ws,
  specs2 % Test,
  guice,
  evolutions,
  "org.playframework.anorm" %% "anorm" % "2.6.1",
  "org.postgresql" % "postgresql" % "9.3-1102-jdbc41",
  "org.apache.poi" % "poi-ooxml" % "3.9",
  "org.apache.poi" % "poi" % "3.9"

)
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.2.3"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

      