package com.shuzau.search.services

trait ConsoleService {
  def read(): String

  def print(message: String): Unit
}
