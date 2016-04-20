package com.jedi.ui

import scala.util.parsing.combinator.RegexParsers

import com.jedi.expression.Block
import com.jedi.expression.Conditional
import com.jedi.expression.Conjunction
import com.jedi.expression.Declaration
import com.jedi.expression.Disjunction
import com.jedi.expression.Expression
import com.jedi.expression.FunCall
import com.jedi.expression.Identifier
import com.jedi.expression.Lambda
import com.jedi.expression.Literal
import com.jedi.value.Boole
import com.jedi.value.Number

class WookieParser extends RegexParsers{
  
   //LITERAL ::= BOOLE | NUMBER
   def literal: Parser[Literal] = boole | number
   
   //FUNCALL ::= TERM~OPERANDS?
   def funcall: Parser[Expression] = term ~ opt(operands) ^^
   {
    case t~None => t
//    case t ~ Some(Nil) => FunCall(t.asInstanceOf[Identifier], Nil)
//    case t~Some(ops) => FunCall(t.asInstanceOf[Identifier], ops)
    case t ~ Some(Nil) => FunCall(t, Nil)
    case t~Some(ops) => FunCall(t, ops)
   }
   
   
   //OPERANDS ::= (~(EXPRESSION~(,~EXPRESSION)*)?)
   def operands: Parser[List[Expression]] = "(" ~> opt(expression ~ rep(","~>expression))<~")" ^^
  {
     case None => Nil 
     case Some(e ~ Nil) => List(e) 
     case Some(e ~ exps) => e::exps 
     case _ => Nil
  }
   
   
    //EQUALITY ::= INEQUALITY~(==~INEQUALITY)*
   def equality: Parser[Expression] = inequality~rep("=="~>inequality) ^^ {
       case inequal~Nil => inequal
       case inequal1~inequals => FunCall(Identifier("equals"), inequal1::inequals)
   }
   
    //INEQUALITY ::= SUM~((<|>|<=|>=|!=)~SUM)*
    def inequality: Parser[Expression] = sum~rep("<"~>sum) ^^{
     case inequal~Nil => inequal
     case inequal~inequalList => FunCall(Identifier("less"), inequal:: inequalList)
    }
   
   //PRODUCT ::= FUNCALL~((\*|/)~FUNCALL)*
   def product: Parser[Expression] = funcall~rep("""\*|/""".r~funcall ^^ {case "*"~s => s case "/"~s => divisor(s)}) ^^ {
       case operand~Nil=> operand   
       case operand1~operand2 => FunCall(Identifier("mul"), operand1::operand2)     
   }
   
   def divisor(exp: Expression): Expression = {
     val div = Identifier("div")
     val one = Number(1.0)
     FunCall(div, List(one, exp))
   }
  

   // DECLARATION ::= def~IDENTIFIER~=~EXPRESSION 
   def declaration: Parser[Declaration] = "def"~identifier~"="~expression ^^ {
       case "def"~id~"="~exp => Declaration(id, exp)
   }
   
   //SUM ::= PRODUCT~((\+|-)~PRODUCT)*
   def sum: Parser[Expression] = 
   product ~ rep(("+"|"-") ~ product ^^ {case "+"~s=>s case "-"~s=>negate(s)})^^{
      case p~Nil=> p
      case p~rest=>FunCall(Identifier("add"), p::rest)
   }
   
   def negate(exp: Expression): Expression = {
     val sub = Identifier("sub")
     val zero = Number(0.0)
     FunCall(sub, List(zero, exp))
   }
  
   //NUMBER ::= (\+|-)?[0-9]+(\.[0-9]+)?  
   def number: Parser[Number] = """(\+|-)?[0-9]+(\.[0-9]+)?""".r ^^ {
     case e => Number(e.toDouble)
   }
   //IDENTIFIER ::= [a-zA-Z][0-9a-zA-Z]*
   def identifier: Parser[Identifier] = """[a-zA-Z][a-zA-Z0-9]*""".r ^^ 
   {
     case e => Identifier(e)
   }

  // BOOLE ::= true | false
   def boole: Parser[Boole] = """true|false""".r ^^ {
    case e => Boole(e.toBoolean)
   // case _ => throw new Exception("Invalid Boolean")
   }
   
   //CONJUNCTION ::= EQUALITY~(&&~EQUALITY)*
   def conjunction: Parser[Expression] = equality ~ rep("""&&""".r ~> equality) ^^ {
     case cond~Nil => cond
     case cond1~conds2 => Conjunction(cond1, conds2.reduce(Conjunction(_,_)))
   }
     
   //DISJUNCTION ::= CONJUNCTION~(||~CONJUNCTION)*
   def disjunction: Parser[Expression] =  conjunction ~ rep("||" ~> conjunction) ^^ {
      case cond~Nil => cond
      case cond1~cond2s => Disjunction(cond1, cond2s.reduce(Disjunction(_,_)))
   }
   
   //CONDITIONAL ::= if~(~EXPRESSION~)~EXPRESSION~(else~EXPRESSION)?
   def conditional: Parser[Conditional] = "if"~"("~expression~")"~expression~opt("else"~expression) ^^ {
      case "if"~"("~conditions~")"~consequent~Some("else"~antecedent) => Conditional(conditions, consequent, antecedent)
      case "if"~"("~conditions~")"~consequent~None => Conditional(conditions, consequent)
   }
   
    //EXPRESSION ::= DECLARATION | CONDITIONAL | DISJUNCTION
    def expression: Parser[Expression] = declaration | conditional | disjunction | failure("Invalid expression")
  
  ///LAMBDA ::= lambda PARAMETERS EXPRESSION
  
  def lambda: Parser[Expression] = "lambda"~parameters~expression ^^ {
    case "lambda"~param~exp => Lambda(param, exp)
    case _ => throw new UndefinedException("Incorrect usage of lambda")
  }
  
  
  //PARAMETERS ::= ((IDENTIFIER (, IDENTIFIER)*))?)
  
  def parameters: Parser[List[Identifier]] =  "(" ~> opt(identifier ~ rep(","~>identifier))<~")" ^^
  {
    case None => Nil 
    case Some(e ~ Nil) => List(e) 
    case Some(e ~ exps) => e::exps 
    case _ => Nil
  }
  
  
  //BLOCK ::- {EXPRESSION (; EXPRESSION)*}
  
  def block: Parser[Expression] = "{"~expression~rep(";"~expression ^^ {case ";"~exp => exp})~"}" ^^ {
    case "{"~exp1~Nil~"}" => Block(List(exp1))
    case "{"~exp1~exprs~"}" => Block(exp1::exprs)
    case _ => throw new Exception()
  }
  
  
  def term: Parser[Expression] = (lambda | block | literal | identifier | "("~>expression<~")")
  
  
  
}