package com.shuzau.search.entiry

import org.scalacheck.{Gen, Shrink}
import org.scalatest.EitherValues
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class WordSpec extends AnyFlatSpec
  with Matchers
  with ScalaCheckPropertyChecks
  with EitherValues {

  private implicit def noShrink[T]: Shrink[T] = Shrink.shrinkAny

  behavior of "Word"

  it should "contain only letter characters" in {
    forAll(EntityGen.wordString) { string =>
      Word(string).value.value shouldBe string.toLowerCase
    }
  }

  it should "not be empty" in {
    forAll(Table("string", "", "  ")) { string =>
      Word(string) shouldBe Left("Empty string cannot be a word")
    }
  }

  it should "contain non letter characters" in {
    forAll(Gen.nonEmptyContainerOf[Vector, Char](Gen.numChar).map(_.mkString)) { string =>
      Word(string) shouldBe Left(s"Word cannot contain non letter character: $string")
    }
  }

  it should "case insensitive" in {
    Word("Hello") shouldBe Word("hEllO")
  }

  it should "ignore leading or trailing white space" in {
    Word("Hello ") shouldBe Word(" Hello")
  }

}
