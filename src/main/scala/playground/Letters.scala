package playground

object Letters extends App{

   val s:String = "aaabbcccdeeefgha"
  //  List(
  //    'a' -> 4,
  //    'b' -> 2,
  //    'c' -> 3,
  //  ...
  //  )
  // - решить через groupBy
  // - решить через foldLeft

  def calcGroupBy (s:String)={
    s.toList.groupBy(identity).mapValues(_.size).toList.sortBy(_._1)
  }

  def calcFoldLeft(s:String)={
    s.toList.foldLeft(Map[Char,List[Char]]()){(mapa, element)=>
      if(!mapa.contains(element)) mapa + (element-> List(element))
      else {
        val newMapBalue = (mapa(element):+element)
        mapa + (element-> newMapBalue)
      }
    }
      .mapValues(_.size).toList.sortBy(_._1)
  }

  println(calcGroupBy(s))
  println(calcFoldLeft(s))
}
