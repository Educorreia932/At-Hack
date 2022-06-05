ThisBuild / version := "1.0.0.0"
ThisBuild / scalaVersion := "3.1.1"
ThisBuild / assemblyMergeStrategy := {
	case PathList("org", "jline", xs @ _*) ⇒ MergeStrategy.last
	case PathList(ps @ _*) if ps.last endsWith "module-info.class" ⇒ MergeStrategy.rename
	case PathList(ps @ _*) if ps.last endsWith ".json" ⇒ MergeStrategy.rename
	case PathList(ps @ _*) if ps.last endsWith "resourcebundles" ⇒ MergeStrategy.rename
	case PathList(ps @ _*) if ps.last startsWith "Inspector" ⇒ MergeStrategy.rename
	case x =>
		val oldStrategy = (ThisBuild / assemblyMergeStrategy).value
		oldStrategy(x)
}

enablePlugins(WindowsPlugin)
enablePlugins(JavaAppPackaging)

maintainer := "Educorreia932"
packageSummary := "Game developed for Level Up! Game Jam"

lazy val root = (project in file("."))
	.settings(
		name := "at-hack",
		// JVM options for Java 17+.
		javaOptions += "--add-opens",
		javaOptions += "javafx.graphics/com.sun.javafx.application=ALL-UNNAMED",
		// Dependencies.
		libraryDependencies += "org.cosplayengine" % "cosplay" % "0.6.6"
	)
