package com.shuzau.search.entiry

final case class Words(value: Set[Word]) {
  def size: Int = value.size
}

object Words {
  val separator = " "

  def apply(string: String): Either[String, Words] = {
    val strings = string.trim
                        .split(separator)
                        .map(_.trim)
                        .toSet

    val words = strings.flatMap(Word(_).toOption)

    Either.cond(words.nonEmpty, Words(words), s"There are no valid words: $string")
  }

}