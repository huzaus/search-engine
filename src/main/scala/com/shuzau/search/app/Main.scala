package com.shuzau.search.app

import com.shuzau.search.entiry.{QueryResult, Words}
import com.shuzau.search.services.Storage

import java.io.File
import scala.io.Source
import scala.io.StdIn.readLine
import scala.util.Using

object Main extends App {
  if (args.length == 0) {
    Console.println("No directory given to index.")
    System.exit(1)
  } else {
    val dir     = args(0)
    val files   = Util.files(dir)
    val storage = Storage.inMemory()

    println(s"${files.size} files to read in directory $dir")

    files.foreach { file =>
      val result = for {
        content <- Util.readFile(file)
        words <- Words(content)
      } yield words

      result.fold[Unit](error => Console.println(s"Skipping ${file.getName} due to $error"),
                        storage.put(file.getName, _))
    }

    println(s"To exit type `${Util.quit}`.")
    println()

    var line: String = ""
    do {
      print("search>")
      line = readLine()
      if (line != Util.quit) {
        println(Words(line).fold(s => s, query => Util.format(storage.find(query))))
        println()
      }
    } while (line != Util.quit)
  }
}

object Util {
  val quit = ":quit"

  def files(dir: String): List[File] = {
    val d = new File(dir)
    if (d.exists && d.isDirectory) {
      d.listFiles.filter(_.isFile).toList
    } else {
      Console.println(s"$dir is not directory or it doesn't exist.")
      System.exit(1)
      List[File]()
    }
  }

  def format(result: QueryResult): String = {
    result.value
          .toVector
          .sortBy { case (_, result) => -result.score.found }
          .take(10)
          .map { case (name, result) => s"$name: ${result.found}/${result.total}" }
          .mkString(System.lineSeparator())

  }

  def readFile(file: File): Either[Throwable, String] =
    Using(Source.fromFile(file))(_.getLines().mkString).toEither

}