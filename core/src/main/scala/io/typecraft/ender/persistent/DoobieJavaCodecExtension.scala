package io.typecraft.ender.persistent

import doobie.Meta

import java.time.{LocalDate, LocalDateTime}
import java.util.UUID

trait DoobieJavaCodecExtension {
  implicit val uuidMeta: Meta[UUID] =
    Meta[String].imap(UUID.fromString)(_.toString)

  implicit val localDateTimeMeta: Meta[LocalDateTime] =
    Meta[String].imap(LocalDateTime.parse)(_.toString)
}
