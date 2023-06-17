package io.typecraft.ender.bukkit

import io.typecraft.ender.AllExtension
import io.typecraft.ender.bukkit.runtime.RuntimeCasting
import io.typecraft.ender.bukkit.circe.CirceLocationCodec
import io.typecraft.ender.bukkit.codec.LocationCodec
import io.typecraft.ender.bukkit.component.BaseComponentExtension
import io.typecraft.ender.bukkit.item.{ItemExtension, ItemMetaExtension}
import io.typecraft.ender.bukkit.plugin.{AsyncExtension, PluginExtension}

trait AllBukkitExtension
  extends BukkitExtension
    with PluginExtension
    with AsyncExtension
    with ItemMetaExtension
    with ItemExtension
    with BaseComponentExtension
    with RuntimeCasting
    with CirceLocationCodec

object implicits extends AllBukkitExtension with AllExtension
