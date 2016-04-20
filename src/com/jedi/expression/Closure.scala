package com.jedi.expression

import com.jedi.ui.JediException
import com.jedi.value.Environment
import com.jedi.value.Value
import com.jedi.ui.UndefinedException
/**
 * Applies the respective parameters to the cosure.
 */
case class Closure(params: List[Identifier], body: Expression, defEnv: Environment) extends Value{
  /**
   * applys the respective arguments to the closure's environment
   */
   def apply(args: List[Value]): Value = {
     if(params.size != args.size)
       throw new UndefinedException("Argument count must parameter count for the respective closure.")
     var tempEnv = new Environment(defEnv)
     for(i <- 0 until params.size){
       tempEnv.put(params(i), args(i))
     }     
     return body.execute(tempEnv)
   }
   
   
}