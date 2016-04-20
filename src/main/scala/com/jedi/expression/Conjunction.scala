package com.jedi.expression

import com.jedi.value.Value
import com.jedi.value.Environment
import com.jedi.ui.TypeException
import com.jedi.value.Boole

/**
 * @author Tony
 * Evaluates the condition and if the first condition is false it will short circuit fail
 */
case class Conjunction(cond1: Expression, cond2: Expression) extends SpecialForm{
  override def execute(env:Environment):Value= {
    val executedCondition1 = cond1.execute(env)
    if(executedCondition1.isInstanceOf[Boole]){
      if(!executedCondition1.asInstanceOf[Boole].value)
        return Boole(executedCondition1.asInstanceOf[Boole].value)
      val executedCondition2 = cond2.execute(env) 
      if(executedCondition1.isInstanceOf[Boole])
        return Boole(executedCondition1.asInstanceOf[Boole].value && executedCondition2.asInstanceOf[Boole].value)
    }
    throw new TypeException("Coniditons part of a Disjunction must be Booles")
  }
}