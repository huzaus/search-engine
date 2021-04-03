package com.shuzau.search.entiry

import org.scalacheck.Gen
import org.scalatest.EitherValues

object EntityGen extends EitherValues {

  val wordString: Gen[String] = Gen.nonEmptyContainerOf[Vector, Char](Gen.alphaChar).map(_.mkString)

  val word: Gen[Word] = wordString.map(Word(_).value)
}
