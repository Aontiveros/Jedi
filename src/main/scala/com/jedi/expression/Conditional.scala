package com.jedi.expression

import com.jedi.value.Value
import com.jedi.value.Environment
import com.jedi.value.Boole
import com.jedi.ui.TypeException
import com.jedi.value.Notification

/**
 * @author Tony
 * Executes the conditional and executes the consequent or antecendent based on the condition.
 */
case class Conditional(condition: Expression, consequent: Expression, antecedent: Expression = null) extends SpecialForm {
    override def execute(env: Environment): Value = {
      val value = condition.execute(env)
      if(!value.isInstanceOf[Boole]) throw new TypeException("Condition must be of type Boole, got: " + value.getClass)
        val valueData = value.asInstanceOf[Boole].value
      if(valueData) consequent.execute(env)
      else if(null != antecedent) antecedent.execute(env)
      else new Notification("Undefined result from Conditional!")
    }
}