package com.shuzau.search.services

trait Storage {
  def put(word: String): Void

  def find(word: String): Boolean
}
