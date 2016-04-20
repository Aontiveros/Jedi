package com.jedi.value

import scala.collection.mutable.HashMap
import com.jedi.ui.UndefinedException
import com.jedi.expression.Identifier

/**
 * @author Tony
 */
case class Environment(var previousEnvironment: Environment = null) extends HashMap[Identifier, Value] with Value{
//      def find(id: Identifier): Value = {
//         if(this.contains(id)){
//           return super.apply(id)
//         }
//         else if(previousEnvironment != null){
//             return previousEnvironment.find(id)
//         }
//         else{
//           throw new UndefinedException(id.value)
//         }
//     }
//     
//     def has(id: Identifier): Boolean = {
//         if(super.contains(id)){
//           return true
//         }
//         else if(previousEnvironment != null){
//             return previousEnvironment.has(id)
//         }
//         else{
//            return false
//         }
//     }
     
     override def apply(id: Identifier): Value = {
       if(super.contains(id)){
         super.apply(id)
       }
       else if(previousEnvironment != null){
         previousEnvironment.apply(id)
       }
       else {
         throw new UndefinedException(id.value)
       }
     }
     
     override def contains(id: Identifier): Boolean = {
       super.contains(id) || (previousEnvironment != null && previousEnvironment.contains(id))
     }
}