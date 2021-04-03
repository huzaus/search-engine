package com.shuzau.search.services

import com.shuzau.search.entiry.{Words, QueryResult, Word}

trait Storage {
  def put(file    : String, word: Word): Unit

  def find(query: Words): QueryResult
}

object Storage {

  def inMemory(): Storage = new Storage {
    var files: Set[String] = Set()

    var storage: Map[Word, Set[String]] = Map()

    override def put(file: String, word: Word): Unit = {
      files = files + file
      storage = storage.updatedWith(word)(files => files.map(_ + file).orElse(Some(Set(file))))
    }

    override def find(query: Words): QueryResult = {
      query.value
           .foldLeft(QueryResult.init(files, query.value.size)) { case (result, word) =>
             storage.get(word).fold(result)(result.score)
           }
    }
  }
}
