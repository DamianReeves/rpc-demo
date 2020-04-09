import mill._
import mill.scalalib._
import mill.scalajslib._

trait JvmRpcDemoModule extends ScalaModule {
  def scalaVersion = "2.13.1"
  def ivyDeps = Agg(
    ivy"dev.zio::zio::1.0.0-RC18-2",
    ivy"io.github.cquiroz::scala-java-time::2.0.0-RC5",
    ivy"com.github.cornerman::sloth::0.2.0",
    ivy"io.suzaku::boopickle::1.3.1",
    ivy"dev.zio::zio-interop-cats::2.0.0.0-RC12",
    ivy"org.typelevel::cats-effect::2.1.2"
    //ivy"com.lihaoyi::scalatags::0.8.6",
    //ivy"com.lihaoyi::upickle::1.0.0"
  )
}

trait JSRpcDemoModule extends ScalaJSModule {
  def scalaVersion = "2.13.1"
  def scalaJSVersion = "0.6.32"
  def ivyDeps = Agg(
    ivy"dev.zio::zio::1.0.0-RC18-2",
    ivy"io.github.cquiroz::scala-java-time::2.0.0-RC5",
    ivy"com.github.cornerman::sloth::0.2.0",
    ivy"io.suzaku::boopickle::1.3.1",
    ivy"dev.zio::zio-interop-cats::2.0.0.0-RC12",
    ivy"org.typelevel::cats-effect::2.1.2"
    //ivy"com.lihaoyi::scalatags::0.8.6",
    //ivy"com.lihaoyi::upickle::1.0.0"
  )
}

trait SharedModule extends ScalaModule {
  def millSourcePath = super.millSourcePath / offset
  def offset: os.RelPath = os.rel
  def sources = T.sources(
    super
      .sources()
      .flatMap(source =>
        Seq(
          PathRef(source.path / os.up / source.path.last),
          PathRef(source.path / os.up / os.up / source.path.last)
        )
      )
  )

}

trait JSCommonModule extends SharedModule with JSRpcDemoModule {}
trait JvmCommonModule extends SharedModule with JvmRpcDemoModule {}

trait JSExtensionModule extends JSRpcDemoModule {
  def moduleDeps = Seq(demo.common.js)
}

trait JvmExtensionModule extends JvmRpcDemoModule {
  def moduleDeps = Seq(demo.common.jvm)
}

object demo extends Module {

  object common extends Module {
    object jvm extends JvmCommonModule {}

    object js extends JSCommonModule {}
  }

  object ext extends Module {
    object echo extends JSExtensionModule {}
    object reverser extends JvmExtensionModule {}
  }

  object shell extends JvmRpcDemoModule {
    def mainClass = Some("demo.Shell")

    def moduleDeps = Seq(common.jvm, ext.reverser)
  }
}
