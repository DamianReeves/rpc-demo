package demo.extension.reverser

import demo.common.Extension
import demo.common.{ExtensionId, ShellInfo}
import sloth._
import boopickle.Default._
import chameleon.ext.boopickle._
import java.nio.ByteBuffer
import cats.implicits._
import scala.concurrent.Future
import demo.common.Extension
import zio._
import zio.interop.catz._

object ReverserExtension {
  val router =
    Router[ByteBuffer, Task].route[Extension](ReverserExtension.ExtensionImpl)

  private object ExtensionImpl extends Extension {
    override def connect(shell: ShellInfo): Task[ExtensionId] =
      Task.succeed(ExtensionId("ext:reverser"))
  }
}
