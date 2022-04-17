package io.typecraft.ender.codec

object RangeCodec {
  private val toKeyword: String = ":"
  private val untilKeyword: String = "until"
  private val stepKeyword: String = "step"

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
    val toIndex = s.indexOf(toKeyword)
    val sepIndex = if (toIndex >= 0) toIndex else s.indexOf(untilKeyword)
    val sepSize = if (toIndex >= 0) toKeyword.length else untilKeyword.length
    val stepIndex = s.indexOf(stepKeyword)
    val from = s.substring(0, sepIndex).trim
    val to =
      s.substring(
        sepIndex + sepSize,
        if (stepIndex >= 0) stepIndex else s.length
      ).trim
    val step =
      if (stepIndex >= 0) s.substring(stepIndex + stepKeyword.length, s.length).trim
      else ""

    if (toIndex >= 0) {
      Range.inclusive(from.toInt, to.toInt, step.toIntOption.getOrElse(1))
    } else {
      Range(from.toInt, to.toInt, step.toIntOption.getOrElse(1))
    }
  }
}
