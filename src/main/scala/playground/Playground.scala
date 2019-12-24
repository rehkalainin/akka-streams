package playground

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{RunnableGraph, Sink, Source}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object Playground extends App {

  def duplicate[A](list: List[A]): List[A] = {
    list.flatMap(x => List(x, x))
  }

  def penultimate[A](l: List[A]): A = l match {
    case Nil | _ :: Nil => throw new NoSuchElementException
    case x :: _ :: Nil => x
    case _ :: tail => penultimate(tail)
  }

  def last[A](l: List[A]): A = l match {
    case head :: Nil => head
    case Nil => throw new NoSuchElementException
    case head :: tail => last(tail)
  }

//  def transform[A](xs: List[Future[Either[Throwable, Option[A]]]]): Future[List[Either[Throwable, A]]] = {
//    val listFuture = Future.sequence(xs)
//    listFuture.map(list =>
//      list.collect {
//        case Left(e) => Left(e)
//        case Right(Some(x)) => Right(x)
//      }
//    )
//  }

  def transform[A](xs: List[Future[Either[Throwable, Option[A]]]]): Future[List[Either[Throwable, A]]] ={
   val listFuture = Future.sequence(xs)
    listFuture.map{list =>
      list.flatMap{
        case Left(e)=> Some(Left(e))
        case Right(Some(x))=> Some(Right(x))
        case Right(None)=> None
      }
    }
  }


  duplicate(Nil).debug
  duplicate(List(1, 2, 3, 4)).debug
  duplicate(List(List(1, 2), List(3, 4))).debug

  last(List(1, 2, 3, 4)).debug
  penultimate(List(1, 2, 3, 4)).debug

}
