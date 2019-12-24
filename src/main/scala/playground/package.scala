package object playground {
  implicit class ShowSyntax[A](private val self: A) extends AnyVal {
    def debug: A = {
      println("-" * 10)
      println(self)
      println("-" * 10)
      self
    }
  }
}
