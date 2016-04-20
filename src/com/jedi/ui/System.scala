package com.jedi.ui

import com.jedi.value.Number
import com.jedi.value.Value
import com.jedi.value.Boole

object system {
  
  def execute(operator: String, operands: List[Value]) = {
    operator match{
      case "sum" => Number(sum(operands))
      case "sub" => Number(sub(operands))
      case "add" => Number(sum(operands))
      case "mul" => Number(mul(operands))
      case "div" => Number(div(operands))
      case "equals" => Boole(equal(operands))
      case "less" => Boole(less(operands))
      case "not" => Boole(not(operands))
      //other operands --- no AND or OR
      case _     => throw new UndefinedException(operator)
    }
  }
  
  def not(args:List[Value]):Boolean = {
    var result = true
    for(i <- 0 until args.length){
      var param1 = args(i)
      if(param1.isInstanceOf[Boole]){
        var param3 = param1.asInstanceOf[Boole]
        result = result && param3.value
      }
      else 
        throw new TypeException(" Expected ! Boole Got: ! " + param1)
        
    }
    return !result
  }
  
  def less(args:List[Value]):Boolean = {
    var result = true
    for(i <- 0 until args.length - 1){
      var param1 = args(i)
      var param2 = args(i+1)
      if(param1.isInstanceOf[Number] && param2.isInstanceOf[Number]){
        var param3 = param1.asInstanceOf[Number]
        var param4 = param2.asInstanceOf[Number]
        result = result && param3.value < param4.value
      }
      else 
        throw new TypeException(" Expected Number < Number Got: " + param1 + " < " + param2)
        
    }
    return result
  }
  
  def equal(args:List[Value]):Boolean = {
    var result = true
    for(i <- 0 until args.length - 1){
      var param1 = args(i)
      var param2 = args(i+1)
      if(param1.isInstanceOf[Number] && param2.isInstanceOf[Number]){
        var param3 = param1.asInstanceOf[Number]
        var param4 = param2.asInstanceOf[Number]
        result = result && param3.value == param4.value
      }
      else if(param1.isInstanceOf[Boole] && param2.isInstanceOf[Boole]){
        var param3 = param1.asInstanceOf[Boole]
        var param4 = param2.asInstanceOf[Boole]
        result = result && param3.value == param4.value
      }
      else 
        result = false
        
    }
    return result
  }
  
  def div(args:List[Value]) = {
    //type check first!
    //Type exception for non numbers
    var count = 0
    for(value <- args){
      if(!value.isInstanceOf[Number])
        throw new TypeException("Expected: " + Number + " Got: " + value + " when dividing")
      else if(value.asInstanceOf[Number].value == 0 && count != 0){
        throw new JediException("Cannot divide by 0")
      }
      count = count + 1
    }
    args.map(_.asInstanceOf[Number]).map(_.value).reduce(_ / _)
  }
  
  def mul(args:List[Value]) = {
    //type check first!
    //Type exception for non numbers
    for(value <- args){
      if(!value.isInstanceOf[Number])
        throw new TypeException("Expected: " + Number + " Got: " + value + " when multiplying")
    }
    args.map(_.asInstanceOf[Number]).map(_.value).reduce(_ * _)

  }
  
  def sub(args:List[Value]) = {
    //type check first!
    //Type exception for non numbers
    for(value <- args){
      if(!value.isInstanceOf[Number])
        throw new TypeException("Expected: " + Number + " Got: " + value + " when subtracting")
    }
    args.map(_.asInstanceOf[Number]).map(_.value).reduce(_ - _)
  }
  
  def sum(args:List[Value]) = {
    //type check first!
    //Type exception for non numbers
    for(value <- args){
      if(!value.isInstanceOf[Number])
        throw new TypeException("Expected: " + Number + " Got: " + value + " when adding")
    }
    args.map(_.asInstanceOf[Number]).map(_.value).reduce(_ + _)
  }

}