ThisBuild / version := "1.0.0.0"
ThisBuild / scalaVersion := "3.1.1"

enablePlugins(WindowsPlugin)
enablePlugins(JavaAppPackaging)

maintainer := "Educorreia932"
packageSummary := "Game developed for Level Up! Game Jam"

lazy val root = (project in file("."))
	.settings(
		name := "Cosplay",
		// JVM options for Java 17+.
		javaOptions += "--add-opens",
		javaOptions += "javafx.graphics/com.sun.javafx.application=ALL-UNNAMED",
		// Dependencies.
		libraryDependencies += "org.cosplayengine" % "cosplay" % "0.6.6"
	)
