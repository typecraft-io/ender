package io.typecraft.ender.rw

import cats.effect.Resource
import cats.effect.kernel.Sync

import java.io._

trait StreamIOExtension {
  implicit class ToInputStreamOps(inStream: InputStream) {
    def readText[F[_]: Sync]: F[String] =
      Resource
        .make {
          Sync[F].blocking(
            new BufferedReader(new InputStreamReader(inStream))
          )
        } { reader =>
          Sync[F].blocking(reader.close())
        }
        .use { reader =>
          Sync[F].blocking(
            Iterator
              .continually(reader.readLine())
              .takeWhile(_ != null)
              .mkString("\n")
          )
        }
  }

  implicit class ToOutputStreamOps(outStream: OutputStream) {
    def writeText[F[_]: Sync](text: String): F[Unit] =
      Resource
        .make {
          Sync[F].blocking(
            new BufferedWriter(
              new OutputStreamWriter(outStream)
            )
          )
        } { writer =>
          Sync[F].blocking(writer.close())
        }
        .use { writer =>
          Sync[F].blocking(writer.write(text))
        }
  }
}
