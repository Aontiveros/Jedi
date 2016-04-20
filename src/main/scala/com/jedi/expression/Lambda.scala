package com.jedi.expression

import com.jedi.value.Value
import com.jedi.value.Environment

case class Lambda(params: List[Identifier], body: Expression) extends SpecialForm {
    override def execute(env: Environment): Value = {
      return new Closure(params, body, env)
    }
}