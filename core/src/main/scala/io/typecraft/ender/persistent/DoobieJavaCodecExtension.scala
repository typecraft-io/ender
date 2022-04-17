package io.typecraft.ender.persistent

import doobie.Meta

import java.util.UUID

trait DoobieJavaCodecExtension {
  implicit val uuidMeta: Meta[UUID] =
    Meta[String].imap(UUID.fromString)(_.toString)
}
