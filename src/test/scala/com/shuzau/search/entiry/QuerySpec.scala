package com.shuzau.search.entiry

import org.scalacheck.{Gen, Shrink}
import org.scalatest.EitherValues
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class QuerySpec extends AnyFlatSpec
  with Matchers
  with ScalaCheckPropertyChecks
  with EitherValues {

  private implicit def noShrink[T]: Shrink[T] = Shrink.shrinkAny

  behavior of "Query"

  it should "contain at least one word" in {
    forAll(Gen.nonEmptyContainerOf[Vector, Word](EntityGen.word)) { words =>
      val queryString = words.map(_.value).mkString(Query.separator)
      Query(queryString).value.words shouldBe words
    }
  }

  it should "not be empty" in {
    forAll(Table("string", "", "  ")) { string =>
      Query(string) shouldBe Left("There are no valid words to search")
    }
  }

  it should "contain only valid word" in {
    forAll(EntityGen.nonLetterCharactersString) { string =>
      Query(string) shouldBe Left("There are no valid words to search")
    }
  }

}
