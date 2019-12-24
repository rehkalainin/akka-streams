package part2_primer

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Keep, Sink, Source}

import scala.concurrent.ExecutionContext.Implicits.global

object MaterializingEx extends App{

  implicit val system = ActorSystem("MaterializingEx")
  implicit val materializer = ActorMaterializer()

  /*
  - return the last element out of a source ( use Sink.last)
  - compute the total world count out of a stream of senteces
  - map, fold, reduce

   */
  val fut1 = Source(1 to 10).runWith(Sink.last)
  val fut2 = Source(1 to 10).toMat(Sink.last)(Keep.right).run()
  val fut3 = Source(1 to 10).to(Sink.last).run() // NotUsed ??

  val sentenceSource = Source(List(
    "I love streams",
    "Akka is awesome",
    "Meterialized value is a simple"
  ))
  val wordCountSink = Sink.fold(0)((sum:Int, sentense:String)=> sum+ sentense.split(" ").length)

  val f1 = sentenceSource.runWith(wordCountSink)
  val f2 = sentenceSource.toMat(wordCountSink)(Keep.right).run()
  val f3 = sentenceSource.runFold(0)((sum, sentence)=> sum + sentence.split(" ").length)
}
