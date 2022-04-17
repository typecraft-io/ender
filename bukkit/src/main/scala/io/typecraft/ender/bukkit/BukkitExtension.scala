package io.typecraft.ender.bukkit

import org.bukkit.Bukkit
import org.bukkit.entity.Player

import java.util
import scala.jdk.CollectionConverters.CollectionHasAsScala

trait BukkitExtension {
  def getOnlinePlayers: Seq[Player] =
    Bukkit.getOnlinePlayers
      .asInstanceOf[util.Collection[Player]]
      .asScala
      .toSeq
}
