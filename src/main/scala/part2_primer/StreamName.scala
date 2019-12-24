package part2_primer

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}

object StreamName extends App{

  /*
  Есть список имен, выбрать 2 имени с длиной более 5 символов
   */

  implicit val actor = ActorSystem("StreamName")
  implicit val meterialaizer = ActorMaterializer()
  // exercise
  val nameList = List("Bob", "Alex", "Billy", "Ira", "Kostya","AkkaName")

  // val collectResultName = Source(nameList).filter(x=>x.size >=5).take(2).to(Sink.foreach(println)).run()
  Source(nameList).filter(_.length>5).take(2).runForeach(println) // same

}
