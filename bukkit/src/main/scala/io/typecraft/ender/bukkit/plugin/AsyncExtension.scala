package io.typecraft.ender.bukkit.plugin

import cats.effect.unsafe.{IORuntime, IORuntimeConfig, Scheduler}
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin

import java.util.logging.Level
import scala.concurrent.ExecutionContext
import scala.concurrent.duration.FiniteDuration

trait AsyncExtension {
  def computeContext(plugin: Plugin): ExecutionContext =
    new ExecutionContext {
      override def execute(runnable: Runnable): Unit =
        Bukkit.getScheduler.runTask(plugin, runnable)

      override def reportFailure(cause: Throwable): Unit =
        plugin.getLogger.log(
          Level.WARNING,
          cause,
          () => "Error while compute execution."
        )
    }

  def blockingContext(plugin: Plugin): ExecutionContext =
    new ExecutionContext {
      override def execute(runnable: Runnable): Unit =
        Bukkit.getScheduler.runTaskAsynchronously(plugin, runnable)

      override def reportFailure(cause: Throwable): Unit =
        plugin.getLogger.log(
          Level.WARNING,
          cause,
          () => "Error while blocking execution."
        )
    }

  def scheduler(plugin: Plugin): Scheduler = new Scheduler {
    override def sleep(delay: FiniteDuration, task: Runnable): Runnable = {
      val bukkitTask =
        Bukkit.getScheduler.runTaskLater(plugin, task, delay.toMillis / 50L)
      () => {
        bukkitTask.cancel()
      }
    }

    override def nowMillis(): Long = System.currentTimeMillis()

    override def monotonicNanos(): Long = System.nanoTime()
  }

  def pluginRuntime(plugin: Plugin): IORuntime =
    IORuntime(
      computeContext(plugin),
      blockingContext(plugin),
      scheduler(plugin),
      () => (),
      IORuntimeConfig()
    )
}

object AsyncExtension extends AsyncExtension
