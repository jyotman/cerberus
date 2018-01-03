name := "cerberus"

version := "0.0.4"

scalaVersion := "2.12.4"

organization := "xyz.jyotman"

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/jyotman/cerberus"),
    "scm:git:git@github.com:jyotman/cerberus.git"
  )
)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"