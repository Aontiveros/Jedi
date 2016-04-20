package com.jedi.expression

import com.jedi.value.Environment
import com.jedi.value.Value
import com.jedi.ui.UndefinedException

/**
 * @author Tony
 */
case class Identifier(value: String) extends Expression with Serializable {
  //override def execute(env: Environment): Value = env.getValue(this);
   override  def execute(env: Environment) = 
     if(env.contains(this)) env.apply(this) else throw new UndefinedException(this + "")
   override def toString:String = {
       return value
   }
   
}