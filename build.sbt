name := "binders-typesafe-config"

version := "0.1"

organization := "eu.inn"

scalaVersion := "2.11.6"

crossScalaVersions := Seq("2.11.6", "2.10.4")

resolvers ++= Seq(
    Resolver.sonatypeRepo("public")
  )

libraryDependencies += "com.typesafe" % "config" % "1.2.1"

libraryDependencies += "eu.inn" %% "binders-core" % "0.6.57"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.1" % "test"

libraryDependencies += "org.mockito" % "mockito-all" % "1.10.19" % "test"

libraryDependencies := {
  CrossVersion.partialVersion(scalaVersion.value) match {
    // if scala 2.11+ is used, quasiquotes are merged into scala-reflect
    case Some((2, scalaMajor)) if scalaMajor >= 11 =>
      libraryDependencies.value
    // in Scala 2.10, quasiquotes are provided by macro paradise
    case Some((2, 10)) =>
      libraryDependencies.value ++ Seq(
        compilerPlugin("org.scalamacros" % "paradise" % "2.0.0" cross CrossVersion.full),
        "org.scalamacros" %% "quasiquotes" % "2.0.0" cross CrossVersion.binary)
  }
}