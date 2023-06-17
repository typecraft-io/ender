package io.typecraft.ender.bukkit.runtime

import scala.reflect.ClassTag

trait RuntimeCasting {
  implicit class ToCastingOps(value: Any) {
    def as[A](implicit classTag: ClassTag[A]): Option[A] = {
      value match {
        case a: A => Some(a)
        case _    => None
      }
    }
  }
}
