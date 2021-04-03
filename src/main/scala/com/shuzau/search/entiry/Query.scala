package com.shuzau.search.entiry

sealed case class Query(words: Vector[Word])

object Query {
  val separator = " "

  def apply(query: String): Either[String, Query] = {
    val strings = query.trim
                       .split(separator)
                       .map(_.trim)
                       .toVector

    val words = strings.map(Word(_).toOption)
                       .flatten

    Either.cond(words.nonEmpty, Query(words), "There are no valid words to search")
  }

}