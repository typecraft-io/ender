package io.typecraft.ender.option

import scala.language.implicitConversions
import scala.util.Try

trait OptionExtension {
  implicit class GetOrElseExtension[A](a: => A) {
    @inline def ?:(aO: Option[A]): A =
      aO.getOrElse(a)
  }

  implicit def tryToOption[A](t: Try[A]): Option[A] = t.toOption

  implicit def eitherToOption[L, R](either: Either[L, R]): Option[R] =
    either.toOption
}
