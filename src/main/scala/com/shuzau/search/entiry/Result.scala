package com.shuzau.search.entiry

final case class Result(total: Long, found: Long) {
  def score: Result = copy(found = found + 1)
}

object Result {
  def init(total: Long): Result =
    Result(total, found = 0)
}