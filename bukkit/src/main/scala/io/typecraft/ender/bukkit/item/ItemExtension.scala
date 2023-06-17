package io.typecraft.ender.bukkit.item

import io.typecraft.ender.bukkit.item.ItemExtension.ItemOps
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

trait ItemExtension {
  implicit class ToItemOps(item: ItemStack) extends ItemOps {
    override def lore: Seq[String] = ItemExtension.lore(item)

    override def lore_=(lore: Seq[String]): Unit =
      updateMeta(ItemMetaExtension.setLore(lore))

    override def name: String = ItemExtension.name(item)

    override def updateMeta(f: ItemMeta => Unit): ItemStack =
      ItemExtension.updateMeta(f)(item)

    override def mapMeta[A](f: ItemMeta => A): Option[A] =
      ItemExtension.mapMeta(f)(item)
  }
}

object ItemExtension {
  @inline def mapMeta[A](f: ItemMeta => A)(item: ItemStack): Option[A] = {
    val meta = item.getItemMeta
    if (meta != null) {
      Some(f(meta))
    } else None
  }

  @inline def updateMeta(f: ItemMeta => Unit)(item: ItemStack): ItemStack = {
    mapMeta(identity)(item) match {
      case Some(meta) =>
        f(meta)
        item.setItemMeta(meta)
        item
      case None =>
        item
    }
  }

  def lore(item: ItemStack): Seq[String] =
    mapMeta(ItemMetaExtension.lore)(item).getOrElse(Seq.empty)

  def name(item: ItemStack): String =
    mapMeta(_.getDisplayName)(item).getOrElse("")

  trait ItemOps {
    def lore: Seq[String]
    def lore_=(lore: Seq[String]): Unit
    def name: String
    def updateMeta(f: ItemMeta => Unit): ItemStack
    def mapMeta[A](f: ItemMeta => A): Option[A]
  }
}
