package io.typecraft.ender.bukkit.circe

import io.circe.{Decoder, Encoder}
import io.typecraft.ender.bukkit.codec.LocationCodec
import org.bukkit.Location

trait CirceLocationCodec {
  implicit val locationEncoder: Encoder[Location] =
    Encoder.encodeString.contramap[Location](LocationCodec.encode)

  implicit val locationDecoder: Decoder[Location] =
    Decoder.decodeString.emap { xs =>
      Right(LocationCodec.decode(xs))
    }
}
