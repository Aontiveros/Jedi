package com.jedi.ui

import scala.util.Failure


/**
 * @author Tony
 */
case class SyntaxException( result: com.jedi.ui.WookieConsole.parsers.Failure, message: String = "Syntax Exception") extends JediException(message){

}