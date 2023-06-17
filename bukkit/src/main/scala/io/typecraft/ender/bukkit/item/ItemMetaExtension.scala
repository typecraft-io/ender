package io.typecraft.ender.bukkit.item

import io.typecraft.ender.bukkit.item.ItemMetaExtension.ItemMetaOps
import org.bukkit.inventory.meta.ItemMeta

import scala.jdk.CollectionConverters.{ListHasAsScala, SeqHasAsJava}

trait ItemMetaExtension {
  implicit class ToItemMetaOps(meta: ItemMeta) extends ItemMetaOps {
    override def lore: Seq[String] = ItemMetaExtension.lore(meta)

    override def lore_=(lore: Seq[String]): Unit =
      ItemMetaExtension.setLore(lore)(meta)
  }
}

object ItemMetaExtension {
  def lore(meta: ItemMeta): Seq[String] = {
    val loreList = meta.getLore
    if (loreList != null) loreList.asScala.toSeq else Seq.empty
  }

  def setLore(lore: Seq[String])(meta: ItemMeta): Unit =
    meta.setLore(lore.asJava)

  trait ItemMetaOps {
    def lore: Seq[String]
    def lore_=(lore: Seq[String]): Unit
  }
}
