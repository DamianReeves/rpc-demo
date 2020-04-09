package demo.common

import zio.Task

trait Extension {
  def connect(shell: ShellInfo): Task[ExtensionId]
}

case class ShellInfo(id: ShellId, extensions: List[ExtensionId])
case class ShellId(value: String) extends AnyVal

case class ExtensionId(value: String) extends AnyVal
