package com.jedi.ui

/**
 * @author Tony
 */
case class TypeException(symbol: String = "Type error") extends JediException("Type error: " + symbol){}