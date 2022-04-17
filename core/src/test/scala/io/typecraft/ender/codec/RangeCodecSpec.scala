package io.typecraft.ender.codec

import org.scalatest.flatspec.AnyFlatSpec

class RangeCodecSpec extends AnyFlatSpec {
  "Range" should "be encoded to String" in {
    assertResult("0:1")(RangeCodec.encode(0 to 1))
    assertResult("0 until 1")(RangeCodec.encode(0 until 1))
    assertResult("0:2 step 2")(RangeCodec.encode(Range.inclusive(0, 2, 2)))
    assertResult("0 until 2 step 2")(RangeCodec.encode(Range(0, 2, 2)))
  }
  "Range" should "be decoded from String" in {
    assertResult(0 to 1)(RangeCodec.decode("0:1"))
    assertResult(0 until 1)(RangeCodec.decode("0 until 1"))
    assertResult(Range.inclusive(0, 2, 2))(RangeCodec.decode("0:2 step 2"))
    assertResult(Range(0, 2, 2))(RangeCodec.decode("0 until 2 step 2"))
  }
}
