package playground

object SumOption extends App {

  val x:Option[Int]=Some(2)
  val y:Option[Int]=Some(3)
  val z:Option[Int]=Some(4)

  def sumFor (x:Option[Int],y:Option[Int],z:Option[Int]):Option[Int]= {
    for{
      sx <- x
      sy <- y
      sz <- z
    } yield sx+sy+sz
  }


  def sum (x:Option[Int],y:Option[Int],z:Option[Int]):Option[Int] ={
    x.flatMap { sx =>
      y.flatMap{ sy=>
        z.map(sz=> sx+sy+sz )
    }}
  }
  println(sumFor(x,y,z))
  println(sum(x,y,z))
}
