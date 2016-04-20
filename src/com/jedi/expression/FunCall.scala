package com.jedi.expression

import com.jedi.ui.TypeException
import com.jedi.value.Environment
import com.jedi.value.Value
import com.jedi.ui.system
import com.jedi.ui.JediException
import com.jedi.ui.UndefinedException
import com.jedi.ui.UndefinedException

//Ewok FunCall
//case class FunCall(operator: Identifier, operands: List[Expression] = Nil) extends Expression {
//  //TODO
//  override def execute(env: Environment):Value = {
//    val args: List[Value] = operands.map(_.execute(env))// eagerly execute operands
//    //override contains to look in all environments
//    if(env.contains(operator)){
//      //apply operator to args
//      //assume its a lambda argument
//      throw new JediException("To be implemented in Wookie!")
//      //if defined and a closure, then apply closure to args
//    }
//    else{
//      system.execute(operator.value, args)
//    }
//  }
//}

case class FunCall(val operator: Expression, val operands: List[Expression] = Nil) extends Expression {
  def execute(env: Environment): Value = {
    val args: List[Value] = operands.map(_.execute(env))//result of evaluating operands
    // 1. check to see if operator is operator.execute(env) is a closure, if so, apply it
   // 2. if undefined exception is thrown and operator is an identifier, catch it and try system.execute
    try{
        operator.execute(env).isInstanceOf[Closure]
        return operator.execute(env).asInstanceOf[Closure].apply(args)
    }
    catch{
      case e: UndefinedException => return system.execute(operator.asInstanceOf[Identifier].value, args)
      case e: Exception => throw e
    }
  }    
}