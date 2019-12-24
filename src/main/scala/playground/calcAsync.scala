package playground

import scala.concurrent.Future

object calcAsync extends App{
  import scala.concurrent.ExecutionContext.Implicits.global

  //// Написать ф-цию, параллельно вычисляющую некоторую функцию применительно к каждому элементу списка:
  ////
  ////    List(1,2,3,4,5,6)
  ////
  ////  примеры:
  ////    1) параллельно посчитать квадрат:
  ////    f = x^2
  ////  result = List(1, 4, 9, 16, ...)
  ////
  ////  2) параллельно посчитать факториал:
  ////    f = x!
  ////    result = List(1, 2, 6, 24, 120, ...)

  // def squareAsync(n: Int): Future[Int] = ???

  // def collectResults(futures: List[Future[Int]]): Future[List[Int]]

  val list = List(1,2,3,4,5,6)

  def sumAsync(x:Int):Future[Int]={
    Future(x*x)
  }

  def factAsync(x:Int):Future[Int]={
    def helper(number:Int, acc:Int):Future[Int]={
      if(number<=1) Future(acc)
      else helper(number-1, acc*number)
    }
    helper(x, 1)
  }

  def calcAsync(list:List[Int], f: Int=>Future[Int]):List[Future[Int]]={
list.map(x=> f(x))
  }

  def collectResults(listFutures: List[Future[Int]]):Future[List[Int]]={
   // Future.sequence(listFutures)
    def helper (futListRes: Future[List[Int]], remaining: List[Future[Int]] ): Future[List[Int]]={
      if(remaining.isEmpty) futListRes
      else {
      val result = remaining.head.flatMap(res => futListRes.map(listRes=> listRes:+res))
        helper(result, remaining.tail)
      }
    }
    val initial = Future(Nil)
    helper(initial, listFutures)
  }

  println(collectResults(calcAsync(list,factAsync)))
  println(collectResults(calcAsync(list,sumAsync)))



}
