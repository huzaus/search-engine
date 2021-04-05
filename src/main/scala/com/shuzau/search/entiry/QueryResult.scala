package com.shuzau.search.entiry

final case class QueryResult(value: Map[String, Result]) {
  def score(file: String): QueryResult =
    copy(value = value.updatedWith(file)(_.map(_.score)))

  def score(files: Set[String]): QueryResult =
    copy(value = files.foldLeft(value) { case (value, file) =>
      value.updatedWith(file)(_.map(_.score))
    })
}

object QueryResult {
  def empty: QueryResult = QueryResult(Map.empty)

  def init(files: Set[String], querySize: Long): QueryResult =
    QueryResult(files.map(file => (file, Result.init(querySize)))
                     .toMap)
}