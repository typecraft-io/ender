package io.typecraft.ender.codec

object RangeCodec {
  def encode(range: Range): String = {
    val sep = range match {
      case _: Range.Inclusive => ":"
      case _: Range.Exclusive => " until "
    }
    val tail = if (range.step != 1) {
      s" step ${range.step}"
    } else {
      ""
    }
    // 0(:| until )1[ step 1]
    s"${range.start}$sep${range.end}" + tail
  }

  def decode(s: String): Range = {
    val toIndex = s.indexOf(":")
    val sepIndex = if (toIndex >= 0) toIndex else s.indexOf("until")
    val stepIndex = s.indexOf("step")
    val from = s.substring(0, sepIndex).trim
    val to =
      s.substring(sepIndex + 2, if (stepIndex >= 0) stepIndex else s.length)
    val step = if (stepIndex >= 0) s.substring(stepIndex, s.length) else ""

    if (toIndex >= 0) {
      Range.inclusive(from.toInt, to.toInt, step.toIntOption.getOrElse(1))
    } else {
      Range(from.toInt, to.toInt, step.toIntOption.getOrElse(1))
    }
  }
}
