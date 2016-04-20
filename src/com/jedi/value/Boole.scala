package com.jedi.value

import com.jedi.expression.Literal

/**
 * @author Tony
 */
case class Boole(value: Boolean) extends Literal {
  override def toString:String = {
    return value + ""
  }
}