package io.typecraft.ender.config

import io.circe.{Decoder, Encoder}
import io.typecraft.ender.codec.RangeCodec

import scala.util.Try

trait CirceScalaCodecExtension {
  implicit val rangeEncoder: Encoder[Range] =
    Encoder.encodeString.contramap[Range](RangeCodec.encode)

  implicit val rangeDecoder: Decoder[Range] =
    Decoder.decodeString.emapTry[Range] { str =>
      Try(RangeCodec.decode(str))
    }
}
