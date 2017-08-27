lazy val root = (project in file(".")).
  settings(
    organization  := "tech.magnolia",
    name          := "sbt-dev",
    version       := "0.0.1-SNAPSHOT",
    sbtPlugin     := true
  )
