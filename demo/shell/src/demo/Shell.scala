package demo

import demo.common.Extension
import scala.concurrent.Future
import demo.common.ExtensionId
import sloth._
import boopickle.Default._
import chameleon.ext.boopickle._
import java.nio.ByteBuffer

import zio._
import zio.console._
import zio.interop.catz._
import demo.common.ShellId
import demo.common.ShellInfo

object Shell extends App {

  val client = Client[ByteBuffer, Task, ClientException](DirectTransport)
  val reverserExt = client.wire[Extension]

  def run(args: List[String]) =
    myAppLogic.fold(_ => 1, _ => 0)

  val myAppLogic =
    for {
      _ <- putStrLn("Hello, world!")
      shell = ShellInfo(ShellId("shell:MyShell"), List.empty)
      res <- reverserExt.connect(shell)
      _ <- putStr(s"Connect Result: $res")
    } yield ()
}
