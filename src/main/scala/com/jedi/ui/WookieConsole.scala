package com.jedi.ui

import com.jedi.value.Environment

object WookieConsole{
   val parsers = new WookieParser // for now
   val globalEnv = new Environment()

   def execute(cmmd: String): String = {
      val tree = parsers.parseAll(parsers.expression, cmmd)
      tree match {
         case t: parsers.Failure => throw new SyntaxException(t)
         case _ => "" + tree.get.execute(globalEnv)
      }
   }
   
    def repl {
      // declare locals
      var more = true
      while(more) {
         try {
            // read/execute/print
           print("->")
           var input = readLine()
           if(input == "quit")
             more = false
           else
             println(execute(input))
           
         } 
         catch {
            case e: SyntaxException => {
               println(e.msg)
               println(e.result.msg)
               println("line # = " + e.result.next.pos.line)
               println("column # = " + e.result.next.pos.column)
               println("token = " + e.result.next.first)
            }
            case e: TypeException => {
               println(e.msg)

            }
            case e: UndefinedException => {
               println(e.msg)

            }
            case e: JediException => {
               println(e.msg)
            }
            // handle other types of exceptions
         } finally {
            Console.flush 
         }
      }
      println("bye")
   }

    
   def main(args: Array[String]): Unit = { 
    // println("Running Test:")
   //  test                          //UNCOMMENT THESE THREE LINES TO RUN QUICK TEST!
   //  println("Running REPL mode:")
     repl
   }
   
   
   def test{
     var more = 0
     while(more < 12){
      try {
            // read/execute/print
           if(more == 0){
             println("-> def a1 = 2 + 3 * 5")
             println(execute("def a1 = 2 + 3 * 5"))
           }
           if(more == 1){
             println("-> def a2 = 2 + 3 * 5")
             println(execute("def a2 = (2 + 3) * 5"))
           }
           if(more == 2){
             println("-> a2")
             println(execute("a2"))
           }
           if(more ==3){
             println("-> a1 == a2")
             println(execute("a1 == a2"))
           }
           if(more ==4){
             println("-> a1 < a2")
             println(execute("a1 < a2"))
           }
           if(more ==5){
             println("-> a1 < a2 && true && a1 == a2 && a1 == a3")
             println(execute("a1 < a2 && true && a1 == a2 && a1 == a3"))
           }
           if(more ==6){
             println("-> a1 == a2 || false || a1 < a2 || a1 == a3")
             println(execute("a1 == a2 || false || a1 < a2 || a1 == a3"))
           }
           if(more ==7){
             println("-> a1 == a3")
             println(execute("a1 == a3"))
           }
           if(more ==8){
             println("-> def a3 = if (a1 == a2) a4 else 0")
             println(execute("def a3 = if (a1 == a2) a4 else 0"))
           }
           if(more ==9){
             println("-> a3")
             println(execute("a3"))
           }
           if(more ==10){
             println("-> a4")
             println(execute("a4"))
           }
           if(more == 11){
             println("-> quit")
             more = 12
           }
           
         } 
         catch {
            case e: SyntaxException => {
               println(e.msg)
               println(e.result.msg)
               println("line # = " + e.result.next.pos.line)
               println("column # = " + e.result.next.pos.column)
               println("token = " + e.result.next.first)
            }
            case e: TypeException => {
               println(e.msg)

            }
            case e: UndefinedException => {
               println(e.msg)

            }
            case e: JediException => {
               println(e.msg)
            }
            // handle other types of exceptions
         }
         finally{
           more = more + 1
         }
      
    }
     println("bye")
   }
}