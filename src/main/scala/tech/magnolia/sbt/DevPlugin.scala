package tech.magnolia.sbt

import sbt._
import Keys._
import Def.Initialize

import java.nio.file.{Files, Paths}

object DevPlugin extends AutoPlugin {
  override def trigger = allRequirements

  object autoImport {
    lazy val mkdir      = taskKey[Unit]("make directories")
    lazy val mkreadme   = taskKey[Unit]("make README file")
    lazy val mklicense  = taskKey[Unit]("make license file")
  }

  import autoImport._

  private def mkdirTask: Initialize[Task[Unit]] = Def.task {
    val base = baseDirectory.value
    val dirs = Seq("src/main/scala/", "src/test/scala/")
    val org  = organization.value.replaceAll("""\.""", "/")

    organization.value match {
      case "default"  => createDirectories( dirs.map { dir => base / dir } )
      case _          => createDirectories( dirs.map { dir => base / dir / org } )
    }
  }

  private def createDirectories(dirs: Seq[File]): Unit = {
    dirs.foreach( dir => IO.createDirectory(dir) )
  }

  private def mkreadmeTask: Initialize[Task[Unit]] = Def.task {
    val base   = baseDirectory.value
    val readme = base / "README.md"

    val header = "## " + name.value

    IO.writeLines(readme, Seq(header))
  }

  private def mklicenseTask: Initialize[Task[Unit]] = Def.task {
    // TBD
  }

  override lazy val projectSettings = Seq(
    mkdir     := mkdirTask.value,
    mkreadme  := mkreadmeTask.value,
    mklicense := mklicenseTask.value
  )
}
