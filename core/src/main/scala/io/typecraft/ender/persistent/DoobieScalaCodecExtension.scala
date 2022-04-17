package io.typecraft.ender.persistent

import doobie.Meta
import io.typecraft.ender.codec.RangeCodec

trait DoobieScalaCodecExtension {
  implicit val rangeMeta: Meta[Range] =
    Meta[String]
      .imap[Range](RangeCodec.decode)(RangeCodec.encode)
}
