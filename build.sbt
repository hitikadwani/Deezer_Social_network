ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.18"

lazy val root = (project in file("."))
  .settings(
    name := "untitled1"
  )
val sparkVersion="3.5.1"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion

// https://mvnrepository.com/artifact/org.apache.spark/spark-graphx
libraryDependencies += "org.apache.spark" %% "spark-graphx" % sparkVersion

libraryDependencies += "org.jline" % "jline" % sparkVersion

libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion



libraryDependencies += "org.jline" % "jline" % "3.5.1"
//Thanks for using https://jar-download.com