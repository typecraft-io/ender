package io.typecraft.ender.bukkit.component

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.BaseComponent

trait BaseComponentExtension {
  implicit class ToBaseComponentOps[A <: BaseComponent](component: A) {
    def withFont(s: String): A = {
      component.setFont(s)
      component
    }

    def withColor(color: ChatColor): A = {
      component.setColor(color)
      component
    }

    def withItalic(x: Boolean): A = {
      component.setItalic(x)
      component
    }
  }
}
