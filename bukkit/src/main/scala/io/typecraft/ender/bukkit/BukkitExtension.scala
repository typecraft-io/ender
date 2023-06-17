package io.typecraft.ender.bukkit

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

import java.util
import java.util.UUID
import scala.jdk.CollectionConverters.CollectionHasAsScala
import scala.reflect.ClassTag

trait BukkitExtension {
  def getOnlinePlayers: Seq[Player] =
    Bukkit.getOnlinePlayers
      .asInstanceOf[util.Collection[Player]]
      .asScala
      .toSeq

  def getPlayer(id: UUID): Option[Player] =
    Option(Bukkit.getPlayer(id))

  def pluginOf[A <: JavaPlugin](implicit tag: ClassTag[A]): A = {
    JavaPlugin.getPlugin(tag.runtimeClass.asInstanceOf[Class[A]])
  }
}
