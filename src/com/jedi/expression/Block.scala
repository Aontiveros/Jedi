package com.jedi.expression

import com.jedi.value.Environment
/**
 * Blocks in which each associated local is executed in its repective environment
 */
case class Block(locals: List[Expression]) extends SpecialForm {
  /**
   * Executes the local environment for the block
   */
   def execute(env: Environment) = {
     val tempEnv = new Environment(env)
     var i = 0
     while(i < locals.size - 1){
       locals(i).execute(tempEnv)
       i += 1
     }
     locals(i).execute(tempEnv)
   }
}