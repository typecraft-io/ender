package io.typecraft.ender.config

import io.circe.{Decoder, Encoder}

import java.time.LocalDateTime
import java.util.UUID
import scala.util.Try

trait CirceJavaCodecExtension {
  implicit val uuidEncoder: Encoder[UUID] =
    Encoder.encodeString.contramap[UUID](_.toString)

  implicit val uuidDecoder: Decoder[UUID] =
    Decoder.decodeString.emapTry[UUID] { str =>
      Try(UUID.fromString(str))
    }
}
