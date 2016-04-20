package com.jedi.expression

import com.jedi.value.Value
import com.jedi.value.Environment

/**
 * @author Tony
 */
trait SpecialForm extends Expression {
   def execute(env: Environment): Value
}