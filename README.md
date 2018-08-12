This is a simple guide to get up and running with [Vterminal](https://github.com/Valkryst/VTerminal) in Scala.  *This is not written in idiomatic scala* and is more or less the fastest port I could do of the [VTerminal Tutorial Series](https://github.com/Valkryst/VTerminal_Tutorial/wiki/%23000---Requirements).  Be prepared for thrown nulls!  I have not written any wrappers in scala for VTerminal yet.

Eventually this will be superceded by the [classic python roguelike tutorial](https://www.reddit.com/r/roguelikedev/wiki/python_tutorial_series) in Scala using VTerminal in an idiomatic style.

# SETUP

I'm assuming you are using IntelliJ and making a new SBT project so your build.sbt should look like this 

```scala
name := "scalarogue"

version := "0.1"

scalaVersion := "2.12.6"

resolvers += "jitpack" at "https://jitpack.io"

libraryDependencies += "com.github.Valkryst" %% "VTerminal" % "3.4.0"
```

# 001 HELLO WORLD 

[Java link with code explanation](https://github.com/Valkryst/VTerminal_Tutorial/wiki/%23001-Hello-World)

```scala 

import com.valkryst.VTerminal.Screen

object HelloWorld extends App {
    val screen = new Screen
    screen.addCanvasToFrame
    screen.getTileAt(0, 0).setCharacter('H')
    screen.getTileAt(1, 0).setCharacter('e')
    screen.getTileAt(2, 0).setCharacter('l')
    screen.getTileAt(3, 0).setCharacter('l')
    screen.getTileAt(4, 0).setCharacter('o')
    screen.getTileAt(6, 0).setCharacter('W')
    screen.getTileAt(7, 0).setCharacter('o')
    screen.getTileAt(8, 0).setCharacter('r')
    screen.getTileAt(9, 0).setCharacter('l')
    screen.getTileAt(10, 0).setCharacter('d')
    screen.getTileAt(11, 0).setCharacter('!')
    screen.draw()
}
```

# 002 TileGrids & Tiles 

[Java link with code explanation](https://github.com/Valkryst/VTerminal_Tutorial/wiki/%23002-TileGrids-&-Tiles)

```scala
import com.valkryst.VTerminal.Screen
import com.valkryst.VTerminal.Tile
import java.awt._

object TileGrids extends App {
  val screen = new Screen
  screen.addCanvasToFrame
  screen.getTileAt(0, 0).setCharacter('H')
  screen.getTileAt(1, 0).setCharacter('e')
  screen.getTileAt(2, 0).setCharacter('l')
  screen.getTileAt(3, 0).setCharacter('l')
  screen.getTileAt(4, 0).setCharacter('o')
  screen.getTileAt(6, 0).setCharacter('W')
  screen.getTileAt(7, 0).setCharacter('o')
  screen.getTileAt(8, 0).setCharacter('r')
  screen.getTileAt(9, 0).setCharacter('l')
  screen.getTileAt(10, 0).setCharacter('d')
  screen.getTileAt(11, 0).setCharacter('!')
  val helloTiles = screen.getTiles.getRowSubset(0, 0, 12)
  for (tile <- helloTiles) {
    tile.setBackgroundColor(Color.BLACK)
    tile.setForegroundColor(Color.WHITE)
  }
  screen.draw()
}
```

# 003 Map and Map Tiles 

[Java link with code explanation](https://github.com/Valkryst/VTerminal_Tutorial/wiki/%23003-Maps-&-Map-Tiles)

We stray a bit from the above since scala enum's aren't really used and the tiles are better structured as case classes / case objects

We have a sprite trait: 

```scala
import java.awt.Color
import com.valkryst.VTerminal.misc.ColorFunctions

trait Sprite {
  def character: Char
  def backgroundColor: Color
  def foregroundColor: Color

  def darkBackgroundColor: Color = ColorFunctions.shade(backgroundColor, 0.5)
  def darkForegroundColor: Color = ColorFunctions.shade(foregroundColor, 0.5)
}
```

We have some default sprites for wall, dirt, grass, etc.:

```scala
object DefaultSprites {

  case object UNKNOWNN extends Sprite {
    val character: Char = '?'
    val backgroundColor: Color = Color.BLACK
    val foregroundColor: Color = Color.RED
  }

  case object DARKNESS extends Sprite {
    val character: Char = '█'
    val backgroundColor: Color = Color.BLACK
    val foregroundColor: Color = Color.BLACK
  }

  case object DIRT extends Sprite {
    val character: Char = '▒'
    val backgroundColor: Color = new Color(0x452F09)
    val foregroundColor: Color = new Color(0x372507)
  }

  case object GRASS extends Sprite {
    val character: Char = '▒'
    val backgroundColor: Color = new Color(0x3D4509)
    val foregroundColor: Color = new Color(0x303707)
  }

  case object WALL extends Sprite {
    val character: Char = '#'
    val backgroundColor: Color = new Color(0x494949)
    val foregroundColor: Color = new Color(0x3C3C3C)
  }

  case object PLAYER extends Sprite {
    val character: Char = '@'
    val backgroundColor: Color = new Color(0, 0, 0, 0)
    val foregroundColor: Color = Color.RED
  }

  case object ENEMY extends Sprite {
    val character: Char = 'E'
    val backgroundColor: Color = new Color(0, 0, 0, 0)
    val foregroundColor: Color = Color.GREEN
  }
}
```

We have any random sprite defined by the user: 

```scala
case class CustomSprite(character: Char, backgroundColor: Color, foregroundColor: Color) extends Sprite
```

Next we need the MapTile which is simpler than the java example: 

```scala 
case class MapTile(sprite: Sprite = DefaultSprites.UNKNOWNN,
                   movementCost: Int = 1,
                   solid: Boolean = true,
                   visible: Boolean = false)
```

For the Map itself I call it `VMap` to avoid name collisions with scala's `Map` collection type.  We also have to extend a Java class that has multiple constructors, so we need to match _atleast one of those constructors_.  See [here](https://stackoverflow.com/questions/3299776/in-scala-how-can-i-subclass-a-java-class-with-multiple-constructors) for extending java classes in scala 

```scala
import java.awt._
import com.valkryst.VTerminal.Tile
import com.valkryst.VTerminal.component.Layer

class VMap(dimension: Dimension) extends Layer(dimension) {

  private final val viewWidth: Int = this.getViewWidth
  private final val viewHeight: Int = this.getViewHeight

  // set the layer to display all tiles as empty as black

  for {
    x <- Range(0, viewWidth)
    y <- Range(0, viewHeight)
  } {
    val tile: Tile = this.tiles.getTileAt(x, y)
    tile.setCharacter('#')
    tile.setBackgroundColor(Color.BLACK)
  }
  // initialize the mapTiles array to the base tile
  val mapTiles: Array[Array[MapTile]] = Array.fill[MapTile](viewWidth, viewHeight)(MapTile())

  this.updateLayerTiles()

  // functions ////////////////////////////////////////////////////////

  def getViewWidth: Int = this.tiles.getWidth

  def getViewHeight: Int = this.tiles.getHeight

  def getMapWidth: Int = mapTiles.length

  def getMapHeight: Int = mapTiles(0).length

  // Updates the Map's Layer, so that any changes made to the Map's tiles are displayed on the Layer.
  def updateLayerTiles(): Unit = {
    for {
      y <- Range(0, viewHeight)
      x <- Range(0, viewWidth)
    } {
      val mapTile = mapTiles(x)(y)
      val mapTileSprite = mapTile.sprite
      val layerTile = this.tiles.getTileAt(x, y)
      layerTile.setCharacter(mapTileSprite.character)
      if (mapTile.visible) {
        layerTile.setBackgroundColor(mapTileSprite.backgroundColor)
        layerTile.setForegroundColor(mapTileSprite.foregroundColor)
      } else {
        layerTile.setBackgroundColor(mapTileSprite.darkBackgroundColor)
        layerTile.setForegroundColor(mapTileSprite.darkForegroundColor)
      }
    }
  }
}
```

# 004 - Rooms 

[Java link with code explanations](https://github.com/Valkryst/VTerminal_Tutorial/wiki/%23004-Rooms)

Now that we have a VMap, MapTile, and Sprite class we can create an area in which the player can move around. Note the return type of `Unit` here: we are modifying the VMap that gets passed in the `carve` function. Nulls can be thrown (eek!).  All these should really be mapped in an IO type and possibly using State (cats/scalaz) for the VMap! 


```scala
import java.awt.{Dimension, Point}

case class Room(position: Point, dimensions: Dimension) {
  def carve(map: VMap): Unit = {
    val mapTiles: Array[Array[MapTile]] = map.mapTiles

    val xRange = Range(position.x, position.x + dimensions.width)
    val yRange = Range(position.y, position.y + dimensions.height)

    val tile = MapTile(sprite = DefaultSprites.DIRT,
      solid = false)

    for {
      x <- xRange
      y <- yRange
    } mapTiles(x)(y) = tile

    map.updateLayerTiles()
  }
}

```

And now the Driver class to make a new room at position 10x, 10y with a width of 10 and height of 5 

```scala
object Driver {
  def main(args: Array[String]): Unit = {
    val screen = new Screen(81, 41)
    screen.addCanvasToFrame()
    val x = 80
    val y = 40
    val map = new VMap(new Dimension(x, y))
    screen.addComponent(map)

    val position = new Point(10, 10)
    val dimensions = new Dimension(10, 5)
    val room = new Room(position, dimensions)
    room.carve(map)

    screen.draw()
  }
}
```

# 005 Entity & Player 

[Java link with explanations](https://github.com/Valkryst/VTerminal_Tutorial/wiki/%23005-Entity-&-Player)

I'm starting to deviate here. Valkryst goes with a Class Hierarchy. I find class extension to be fiddly in scala
so I decided to go with a single Entity type with a [phantom type](https://stackoverflow.com/questions/28247543/motivation-behind-phantom-types)
to model the Entity so you are either a generic Entity of Entity[Thing] or an Entity[Player].  Depending on where
this goes in later tutorials, I may have to revisit this if the behavior between them deviates too much.

```scala
import com.valkryst.VTerminal.Tile
import com.valkryst.VTerminal.component.Layer
import java.awt._
import examples.shared.{DefaultSprites, Sprite}


trait EntityTypes
case object Thing extends EntityTypes
case object Player extends EntityTypes

trait GenericEntity[T <: EntityTypes] extends Layer {
  val sprite: Sprite
  val position: Point
  val name: String
  val dimension: Dimension

  def move(dx: Int, dy: Int): GenericEntity[T]

  def setPosition(pos: Point): GenericEntity[T]

  def getPosition: Point

}

/**
  * We deviate pretty hard from the tutorial here!
  * We use a phantom type on the Entity to differentiate between Things and the Player
  * So you are either an Entity[Thing] or an Entity[Player]
  */

case class Entity[T <: EntityTypes](sprite: Sprite,
                  position: Point,
                  name: String,
                  dimension: Dimension) extends Layer(dimension) with GenericEntity[T] {


  this.getTiles.setPosition(position)

  private val tile: Tile = this.getTileAt(new Point(0,0))

  tile.setCharacter(sprite.character)
  tile.setForegroundColor(sprite.foregroundColor)
  tile.setBackgroundColor(sprite.backgroundColor)


  def move(dx: Int, dy: Int): Entity[T] = {
    val newX = dx + this.getTiles.getXPosition
    val newY = dy + this.getTiles.getYPosition
    this.copy(position = new Point(newX, newY))
  }

  def setPosition(pos: Point): Entity[T] = {
    this.copy(position = pos)
  }

  val getPosition: Point = new Point(this.getTiles.getXPosition, this.getTiles.getYPosition)
}

object EntityCreator {

  def makeThing(sprite: Sprite,
               position: Point,
               name: String): Entity[Thing.type] = {
    val dim = new Dimension(1,1)
    Entity(sprite, position, name, dim)
  }

  def makePlayer(position: Point,
                name: String): Entity[Player.type] = {
    val dim = new Dimension(1,1)
    Entity(DefaultSprites.PLAYER, position, name, dim)
  }
}

```

And for Driver

```scala
package examples.part5

import java.awt.{Dimension, Point}

import com.valkryst.VTerminal.Screen
import com.valkryst.VTerminal.font.{Font, FontLoader}
import examples.shared.{Room, VMap}

object Driver {
  def main(args: Array[String]): Unit = {

    val font: Font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png",
                                                "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1)

    val screen = new Screen(81, 41, font)
    screen.addCanvasToFrame()
    val x = 80
    val y = 40
    val map = new VMap(new Dimension(x, y))
    screen.addComponent(map)

    val position = new Point(10, 10)
    val dimensions = new Dimension(10, 5)
    val room = new Room(position, dimensions)
    room.carve(map)

    screen.draw()

    val player: Entity[Player.type] = EntityCreator.makePlayer(new Point(12,12), "Gygax")
    map.addComponent(player)

    screen.draw()
  }
}

```


