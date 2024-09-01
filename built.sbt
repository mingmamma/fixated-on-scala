name := "fixated-on-scala"

version := "0.0.1"

scalaVersion := "3.3.3"

// sbt resolvers (configuration to specify where SBT find and download dependencies) of the project is defined in the following section
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

// dependencies of the project is defined in the following section
libraryDependencies ++= Seq(
    "io.circe" %% "circe-parser" % "0.14.9",
    "io.circe" %% "circe-generic" % "0.14.9",
    "io.circe" %% "circe-core" % "0.14.9"
)

