package com.jedi.value

/**
 * @author Tony
 */
class Notification(message: String) extends Value {
  override def toString():String = {
    return message
  }
}

object Notification {
  def apply(msg: String) = new Notification(msg)
  val OK = Notification("OK")
  val DONE = Notification("DONE")
  val UNSPECIFIED = Notification("UNSPECIFIED")
}