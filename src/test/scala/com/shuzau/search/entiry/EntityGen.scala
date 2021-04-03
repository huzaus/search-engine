package com.shuzau.search.entiry

import org.scalacheck.Gen
import org.scalatest.EitherValues

object EntityGen extends EitherValues {

  val nonLetterCharactersString: Gen[String] = Gen.nonEmptyContainerOf[Vector, Char](Gen.numChar).map(_.mkString)

  val wordString: Gen[String] = Gen.nonEmptyContainerOf[Vector, Char](Gen.alphaChar).map(_.mkString)

  val word: Gen[Word] = wordString.map(Word(_).value)

  val words: Gen[Set[Word]] = Gen.nonEmptyContainerOf[Set, Word](EntityGen.word)

  val query: Gen[Words] = words.map(Words(_))
}
