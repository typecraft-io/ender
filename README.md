# Ender

A pure, functional library for Minecraft development with Scala.

# Quick start

```sbt
libraryDependencies += "io.typecraft" %% "ender" % "0.1.0"

```

# Configuration

circe..

### Example

```scala
import io.typecraft.ender.bukkit.implicits._
import io.circe.generic.auto._

class MyPlugin extends JavaPlugin with ScalaPlugin {
  var myConfig: MyConfig = MyConfig.empty

  override def onEnable(): Unit = {
    // Type `MyConfig` will be auto mapped as yaml
    myConfig = readConfig[MyConfig] ?: MyConfig.empty
  }
}

```

### Supported types

- Range
- UUID

# Persistent

doobie..

### Example

```scala
val getCountry: IO[Option[Country]] =
  sql"select * from country where id=$id"
    .query[Country] // Type `Country` will be auto mapped
    .option
    .transact(conn)
```

### Supported types

- Range
- UUID

# Command

WIP

# Inventory

bukkit-view-scala

# Async

```scala
import io.typecraft.ender.bukkit.implicits._

class MyPlugin extends JavaPlugin with ScalaPlugin {
  def asyncAndSleepThenSync: IO[Unit] =
    for {
      _ <- IO.blocking(println("Here is runTaskAsync!"))
      _ <- IO.sleep(5.seconds)
      _ <- IO(println("Here is runTask!"))
    } yield ()

  def runEveryFiveSeconds: IO[Unit] =
    for {
      _ <- IO(println("Do something..."))
      _ <- IO.sleep(5.seconds)
      _ <- repeat // recursive
    } yield ()
}
```
