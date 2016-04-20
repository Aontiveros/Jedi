package com.jedi.expression

import com.jedi.value.Environment
import com.jedi.value.Value

/**
 * @author Tony
 */
trait Literal extends Value with Expression{
    def execute(env: Environment): Value = this
}