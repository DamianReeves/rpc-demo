package demo.extension.reverser

import sloth._
import boopickle.Default._
import chameleon.ext.boopickle._
import java.nio.ByteBuffer
import cats.implicits._
import scala.concurrent.Future
import demo.common.Extension
import zio._
import zio.interop.catz._

object Main {

  def main(args: Array[String]) = {
    println("Reverser")
  }
}
