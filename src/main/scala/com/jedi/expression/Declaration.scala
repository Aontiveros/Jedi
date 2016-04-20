package com.jedi.expression

import com.jedi.value.Environment
import com.jedi.value.Notification
import com.jedi.value.Value

/**
 * Places a new variable for the respective environment
 */
case class Declaration(id: Identifier, expression: Expression ) extends SpecialForm{
  override def execute(env:Environment):Value = {
    env.put(id, expression.execute(env))
    //return Notification(id.name + " = " + expression.execute(env))
    return Notification.OK
  }
}