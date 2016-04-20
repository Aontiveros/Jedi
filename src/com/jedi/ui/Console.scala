package com.jedi.ui

import com.jedi.value.Environment

/**
 * @author Tony
 */
object console {
   val parser = new Parser
   val globalEnv = new Environment()
   // etc.
   def repl {
//      while(more) {
//         try {
//            print("-> ")
//            val cmmd = readLine();
//            handle meta commands: print, execute, save, open, etc. else
//            val exp = parse cmmd
//            println(exp.exacute(globalEnv);
//         } 
//         catch {
//            // handle each type of exception separately
//         } finally {
//            // flush input stream 
//         }
//      }
   } // repl
    
  def main(args: Array[String]): Unit = { repl }

} // console