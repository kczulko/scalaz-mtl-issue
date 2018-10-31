package example

import scalaz.{ EitherT, Monad, MonadState, StateT }
import scalaz.MonadError

import scalaz.syntax.all._
import scalaz.Id._

object Main {

  def dummyProgram[F[_]](MS: MonadState[F,Int])(implicit ME: MonadError[F,Throwable]): F[Unit] = {
    for {
      i      <- MS.get
      _      <- MS.put(i + 1)
      result <- if (i < 0) ME.raiseError(new Throwable(":/")) else ME.point(())
    } yield result
  }

  def main(args: Array[String]): Unit = {
    implicit val etme = EitherT.eitherTMonadError[Id,Throwable]
    implicit val stms = StateT.stateTMonadState[Int, EitherT[Id,Throwable,?]]
    implicit val stme = StateT.stateTMonadError[Int,EitherT[Id,Throwable,?],Throwable]

    dummyProgram[StateT[EitherT[Id,Throwable,?],Int,?]](stms)
      .run(2)
      .run
      .fold(l => println(s"error: $l"), r => println(s"success $r"))
  }
}
