package io.typecraft.ender.rw

import cats.effect.kernel.Sync
import cats.implicits._

import java.io._

trait FileIOExtension extends StreamIOExtension {
  implicit class ToFileIOOps(file: File) {
    def readText[F[_]: Sync]: F[String] =
      for {
        inStream <- Sync[F].blocking(new FileInputStream(file))
        fileText <- inStream.readText
      } yield fileText

    def writeText[F[_]: Sync](text: String): F[Unit] = {
      for {
        _ <- Sync[F].blocking(file.getParentFile.mkdirs())
        outStream <- Sync[F].blocking(new FileOutputStream(file))
        _ <- outStream.writeText(text)
      } yield ()
    }
  }
}
