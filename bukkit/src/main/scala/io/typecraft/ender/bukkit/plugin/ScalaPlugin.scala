package io.typecraft.ender.bukkit.plugin

import cats.effect.unsafe.IORuntime
import io.circe
import io.circe.{Decoder, Encoder}
import io.typecraft.ender.bukkit.plugin.PluginExtension.PluginOps
import org.bukkit.plugin.Plugin

import java.io.File
import scala.util.Try

trait ScalaPlugin extends Plugin with PluginOps {
  implicit val runtime: IORuntime =
    AsyncExtension.pluginRuntime(this)

  @inline override def getConfigFile: File =
    PluginExtension.getConfigFile(this)

  @inline override def readDataFile[A: Decoder](
      fileName: String
  ): Either[circe.Error, A] =
    PluginExtension.readDataFile(fileName)(this)

  @inline override def readConfig[A: Decoder]: Either[circe.Error, A] =
    PluginExtension.readConfig(this)

  @inline override def writeConfig[A: Encoder](a: A): Try[Unit] =
    PluginExtension.writeConfig(a)(this)

  @inline override def writeDefaultConfig[A: Encoder](a: A): Try[Unit] =
    PluginExtension.writeDefaultConfig(a)(this)
}
