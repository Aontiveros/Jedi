package com.jedi.expression

import com.jedi.value.Environment

case class Block(locals: List[Expression]) extends SpecialForm {
   def execute(env: Environment) = {
     val tempEnv = new Environment(env)
     var i = 0
     while(i < locals.size - 1){
       locals(i).execute(tempEnv)
       i += 1
     }
     locals(i).execute(tempEnv)
     //TODO i may be an issue here
   }
}