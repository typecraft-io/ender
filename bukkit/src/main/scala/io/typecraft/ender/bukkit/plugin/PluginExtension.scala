package io.typecraft.ender.bukkit.plugin

import cats.effect.SyncIO
import cats.implicits._
import io.circe.yaml.parser._
import io.circe.yaml.printer
import io.circe.{Decoder, Encoder}
import io.circe.generic.auto._
import io.typecraft.ender.bukkit.implicits.configFileName
import io.typecraft.ender.bukkit.plugin.PluginExtension.PluginOps
import io.typecraft.ender.implicits._
import org.bukkit.plugin.Plugin

import java.io.File
import scala.util.{Success, Try}

trait PluginExtension {
  val configFileName: String = "config.yml"

  implicit class ToPluginOps(plugin: Plugin) extends PluginOps {
    override def getConfigFile: File =
      PluginExtension.getConfigFile(plugin)

    override def readDataFile[A: Decoder](
        fileName: String
    ): Either[io.circe.Error, A] =
      PluginExtension.readDataFile[A](fileName)(plugin)

    override def readConfig[A: Decoder]: Either[io.circe.Error, A] =
      PluginExtension.readConfig[A](plugin)

    override def writeConfig[A: Encoder](a: A): Try[Unit] =
      PluginExtension.writeConfig[A](a)(plugin)

    override def writeDefaultConfig[A: Encoder](a: A): Try[Unit] =
      PluginExtension.writeDefaultConfig[A](a)(plugin)
  }
}

object PluginExtension {
  def getConfigFile(plugin: Plugin): File =
    new File(plugin.getDataFolder, configFileName)

  def readDataFile[A: Decoder](
      fileName: String
  )(plugin: Plugin): Either[io.circe.Error, A] = {
    val yaml = new File(plugin.getDataFolder, fileName)
      .readText[SyncIO]
      .recover(_ => "")
      .unsafeRunSync()
    for {
      node <- parse(yaml)
      a <- Decoder[A].decodeJson(node)
    } yield a
  }

  def readConfig[A: Decoder](plugin: Plugin): Either[io.circe.Error, A] =
    readDataFile[A](configFileName)(plugin)

  def writeConfig[A: Encoder](a: A)(plugin: Plugin): Try[Unit] = {
    val node = Encoder[A].apply(a)
    Try(
      getConfigFile(plugin)
        .writeText[SyncIO](printer.print(node))
        .unsafeRunSync()
    )
  }

  def writeDefaultConfig[A: Encoder](a: A)(plugin: Plugin): Try[Unit] = {
    if (!getConfigFile(plugin).isFile) {
      writeConfig(a)(plugin)
    } else Success(())
  }

  trait PluginOps {
    def getConfigFile: File
    def readDataFile[A: Decoder](
        fileName: String
    ): Either[io.circe.Error, A]
    def readConfig[A: Decoder]: Either[io.circe.Error, A]
    def writeConfig[A: Encoder](a: A): Try[Unit]
    def writeDefaultConfig[A: Encoder](a: A): Try[Unit]
  }
}
