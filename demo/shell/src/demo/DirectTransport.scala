package demo

import sloth._
import boopickle.Default._
import chameleon.ext.boopickle._
import java.nio.ByteBuffer
import cats.implicits._
import zio._
import zio.interop.catz._
import demo.extension.reverser.ReverserExtension

object DirectTransport extends RequestTransport[ByteBuffer, Task] {
  // implement the transport layer. this example just calls the router directly.
  // in reality, the request would be sent over a connection.
  override def apply(request: Request[ByteBuffer]): Task[ByteBuffer] =
    ReverserExtension.router(request).toEither match {
      case Right(result) => result
      case Left(err)     => Task.fail(new Exception(err.toString))
    }
}
