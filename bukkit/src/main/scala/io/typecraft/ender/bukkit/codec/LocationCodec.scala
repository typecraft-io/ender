package io.typecraft.ender.bukkit.codec

import org.bukkit.{Bukkit, Location}

object LocationCodec {
  def encode(xs: Location): String = {
    val builder = new StringBuilder()
    val worldName = if (xs != null) xs.getWorld.getName else ""
    builder.append(xs.getX).append(",")
      .append(xs.getY).append(",")
      .append(xs.getZ).append(",")
      .append(worldName)
    if (xs.getPitch == 0f && xs.getYaw == 0f) {
      builder.append(",").append(xs.getPitch).append(",").append(xs.getYaw)
    }
    builder.toString()
  }

  def decode(xs: String): Location = {
    val pieces = xs.split(",")
    val x = pieces.lift(0).flatMap(_.toDoubleOption).getOrElse(0.0)
    val y = pieces.lift(1).flatMap(_.toDoubleOption).getOrElse(0.0)
    val z = pieces.lift(2).flatMap(_.toDoubleOption).getOrElse(0.0)
    val world = pieces.lift(4).getOrElse("")
    val pitch = pieces.lift(5).flatMap(_.toFloatOption).getOrElse(0f)
    val yaw = pieces.lift(6).flatMap(_.toFloatOption).getOrElse(0f)
    new Location(
      if (world.nonEmpty) Bukkit.getWorld(world) else null,
      x, y, z,
      pitch, yaw
    )
  }
}
