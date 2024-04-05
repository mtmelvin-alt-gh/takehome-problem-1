ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

lazy val root = (project in file("."))
  .settings(
    name := "tst takehome problem 1"
  )
 libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.17" % Test