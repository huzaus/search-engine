package com.shuzau.search.entiry

sealed trait Word {
  def value: String
}

object Word {
  def apply(value: String): Either[String, Word] = {
    val trimmedValue = value.trim
    for {
      _ <- Either.cond(trimmedValue.nonEmpty, (), "Empty string cannot be a word")
      _ <- Either.cond(trimmedValue.forall(_.isLetter), (), s"Word cannot contain non letter character: $value")
    } yield HiddenWord(trimmedValue.toLowerCase)
  }

  private case class HiddenWord(value: String) extends Word

}
