package com.jedi.expression

import com.jedi.value.Environment
import com.jedi.value.Value

/**
 * @author Tony
 * Interface for an expression
 */
trait Expression {
   def execute(env: Environment): Value
}