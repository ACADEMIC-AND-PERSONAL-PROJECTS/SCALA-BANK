scalaVersion := "3.9.0"

lazy val root = rootProject
  .settings(
    name := "ScalaBank",
    idePackagePrefix := Some("me.khadimprojects"),
    libraryDependencies ++= Seq(
      //You can add library dependencies here, for example,
      //"org.scalatest" %% "scalatest" % "3.2.19" % Test,
      //"org.scalameta" %% "munit" % "1.2.3" % Test
    )
  )
