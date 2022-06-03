ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.1.1"

lazy val root = (project in file("."))
	.settings(
		name := "Cosplay",
		// JVM options for Java 17+.
		javaOptions += "--add-opens",
		javaOptions += "javafx.graphics/com.sun.javafx.application=ALL-UNNAMED",
		// Dependencies.
		libraryDependencies += "org.cosplayengine" % "cosplay" % "0.6.6"
	)
