package com.jedi.expression

import com.jedi.value.Value
import com.jedi.value.Environment
import com.jedi.value.Boole
import com.jedi.ui.TypeException

case class Disjunction(cond1: Expression, cond2: Expression) extends SpecialForm{
  override def execute(env:Environment):Value= {
    val executedCondition1 = cond1.execute(env)
    if(executedCondition1.isInstanceOf[Boole]){
      if(executedCondition1.asInstanceOf[Boole].value)
        return executedCondition1.asInstanceOf[Boole]
      val executedCondition2 = cond2.execute(env) 
      if(executedCondition1.isInstanceOf[Boole])
        return Boole(executedCondition1.asInstanceOf[Boole].value || executedCondition2.asInstanceOf[Boole].value)
    }
    throw new TypeException("Coniditons part of a Disjunction must be Booles")
  }
}