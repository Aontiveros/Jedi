package com.jedi.ui

/**
 * @author Tony
 */
class UndefinedException(symbol: String) extends JediException("Undefined identifier: " + symbol)