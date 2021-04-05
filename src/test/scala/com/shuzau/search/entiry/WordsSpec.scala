package com.shuzau.search.entiry

import org.scalatest.EitherValues
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class WordsSpec extends AnyFlatSpec
  with Matchers
  with ScalaCheckPropertyChecks
  with EitherValues {

  behavior of "Query"

  it should "contain at least one word" in {
    forAll(EntityGen.wordSet) { words =>
      val queryString = words.map(_.value).mkString(Words.separator)
      Words(queryString).value.value shouldBe words
    }
  }

  it should "not be empty" in {
    forAll(Table("string", "", "  ")) { string =>
      Words(string) shouldBe Left(s"There are no valid words: $string")
    }
  }

  it should "contain only valid word" in {
    forAll(EntityGen.nonLetterCharactersString) { string =>
      Words(string) shouldBe Left(s"There are no valid words: $string")
    }
  }

}
