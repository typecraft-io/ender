package io.typecraft.ender.bukkit

import io.typecraft.ender.AllExtension
import io.typecraft.ender.bukkit.plugin.{AsyncExtension, PluginExtension}

trait AllBukkitExtension extends PluginExtension with AsyncExtension

object implicits extends AllBukkitExtension with AllExtension
