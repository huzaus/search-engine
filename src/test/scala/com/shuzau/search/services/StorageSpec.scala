package com.shuzau.search.services

import com.shuzau.search.entiry.{EntityGen, QueryResult, Result}
import org.scalacheck.{Gen, Shrink}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.{EitherValues, OptionValues}
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class StorageSpec extends AnyFlatSpec
  with Matchers
  with ScalaCheckPropertyChecks
  with EitherValues
  with OptionValues {

  private implicit def noShrink[T]: Shrink[T] = Shrink.shrinkAny

  behavior of "Storage"

  it should "return empty result for non existing file" in {
    val storage = Storage.inMemory()
    forAll(EntityGen.words) { query =>
      storage.find(query) shouldBe QueryResult.empty
    }
  }

  it should "put and find words" in {
    forAll(Gen.identifier, EntityGen.words) { case (file, words) =>
      val storage = Storage.inMemory()
      storage.put(file, words)

      storage.find(words) shouldBe QueryResult(
        Map(file -> Result(words.size, words.size)))
    }
  }

  it should "put words in two files and find words from files" in {
    forAll(Gen.identifier, EntityGen.words, Gen.identifier, EntityGen.words, Gen.identifier) { case (file1, words1, file2, words2, file3) =>
      val storage = Storage.inMemory()
      storage.put(file1, words1)
      storage.put(file2, words2)
      storage.put(file3, words1)
      storage.put(file3, words2)

      val intersection = words1.value.intersect(words2.value).size
      val words1Size   = words1.size
      val words2Size   = words2.size

      storage.find(words1) shouldBe QueryResult(
        Map(file1 -> Result(words1Size, words1Size),
            file2 -> Result(words1Size, intersection),
            file3 -> Result(words1Size, words1Size)))

      storage.find(words2) shouldBe QueryResult(
        Map(file1 -> Result(words2Size, intersection),
            file2 -> Result(words2Size, words2Size),
            file3 -> Result(words2Size, words2Size)))
    }
  }
}
