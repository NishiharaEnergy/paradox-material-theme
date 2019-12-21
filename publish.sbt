val repo = new {
  val org = "jkugiya"
  val name = "paradox-material-theme"
  val path = org + "/" + name
}

inThisBuild(Def.settings(
  organization := "io.github.jkugiya",
  licenses += "MIT" -> url("https://github.com/jkugiya/paradox-material-theme/blob/master/LICENSE"),
  homepage := None,
  scmInfo := Some(
    ScmInfo(
      url(s"https://github.com/jkugiya/paradox-material-theme"),
      s"scm:git:git@github.com:${repo.path}.git"
    )
  ),
  developers := List(
    Developer("jkugiya", "Jiro Kugiya", "j.kugiya@gmail.com", url("https://github.com/jkugiya"))
  ),
  // Workaround NPE when publishing: https://github.com/sbt/sbt/issues/3519
  updateOptions := updateOptions.value.withGigahorse(false),
  publishMavenStyle := true,
  publishArtifact in Test := false,
  pomIncludeRepository := { _ => false },
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  },
  credentials ++= (
    for {
      username <- Option(System.getenv().get("SONATYPE_USERNAME"))
      password <- Option(System.getenv().get("SONATYPE_PASSWORD"))
    } yield Credentials(
      "Sonatype Nexus Repository Manager",
      "oss.sonatype.org",
      username,
      password
    )
  ).toSeq,
  versionWithGit,
  git.useGitDescribe := true,
  git.remoteRepo := s"git@github.com:${repo.path}.git"
))
