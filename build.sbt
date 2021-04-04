ThisBuild / scalaVersion := "2.13.5"
ThisBuild / version := "0.0.1"
ThisBuild / organization := "com.shuzau"
ThisBuild / organizationName := "Corpo"

ThisBuild / scalacOptions := Seq("-unchecked",
                                 "-deprecation",
                                 "-encoding",
                                 "utf8",
                                 "-feature",
                                 "literal-types",
                                 "-Xfatal-warnings",
                                 "-Ymacro-annotations")

lazy val scalaTest  = "org.scalatest" %% "scalatest" % "3.2.6" % Test
lazy val scalaCheck = "org.scalatestplus" %% "scalacheck-1-14" % "3.1.0.0" % Test


lazy val root = (project in file("."))
  .settings(
    name := "search-engine",
    libraryDependencies ++= Seq(
      scalaTest,
      scalaCheck
      )
    )
