name := "typesafe-config-binders"

version := "0.13-SNAPSHOT"

organization := "com.hypertino"

scalaVersion := "2.12.1"

crossScalaVersions := Seq("2.12.1", "2.11.8", "2.10.6")

resolvers ++= Seq(
  Resolver.sonatypeRepo("public")
)

libraryDependencies ++= Seq(
  "com.typesafe" % "config" % "1.2.1",
  "com.hypertino" %% "binders" % "1.0-SNAPSHOT",
  "com.hypertino" %% "scalamock-scalatest-support" % "3.4-SNAPSHOT" % "test"
)

libraryDependencies := {
  CrossVersion.partialVersion(scalaVersion.value) match {
    // if scala 2.11+ is used, quasiquotes are merged into scala-reflect
    case Some((2, scalaMajor)) if scalaMajor >= 11 =>
      libraryDependencies.value
    // in Scala 2.10, quasiquotes are provided by macro paradise
    case Some((2, 10)) =>
      libraryDependencies.value ++ Seq(
        compilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
        "org.scalamacros" %% "quasiquotes" % "2.1.0" cross CrossVersion.binary)
  }
}

// Sonatype repositary publish options
publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false}

pomExtra :=
  <url>https://github.com/hypertino/typesafe-config-binders</url>
    <licenses>
      <license>
        <name>BSD-style</name>
        <url>http://opensource.org/licenses/BSD-3-Clause</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:hypertino/typesafe-config-binders.git</url>
      <connection>scm:git:git@github.com:hypertino/typesafe-config-binders.git</connection>
    </scm>
    <developers>
      <developer>
        <id>maqdev</id>
        <name>Magomed Abdurakhmanov</name>
        <url>https://github.com/maqdev</url>
      </developer>
      <developer>
        <id>hypertino</id>
        <name>Hypertino</name>
        <url>https://github.com/hypertino</url>
      </developer>
    </developers>

// Sonatype credentials

credentials ++= (for {
  username <- Option(System.getenv().get("sonatype_username"))
  password <- Option(System.getenv().get("sonatype_password"))
} yield Credentials("Sonatype Nexus Repository Manager", "oss.sonatype.org", username, password)).toSeq

// pgp keys and credentials

pgpSecretRing := file("./travis/ht-oss-private.asc")

pgpPublicRing := file("./travis/ht-oss-public.asc")

usePgpKeyHex("F8CDEF49B0EDEDCC")

pgpPassphrase := Option(System.getenv().get("oss_gpg_passphrase")).map(_.toCharArray)
