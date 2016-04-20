package com.jedi.value

import com.jedi.expression.Literal

/**
 * @author Tony
 */
case class Number(value: Double) extends Literal{
  override def toString:String = {
    return value + ""
  }
}