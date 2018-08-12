package examples.part5

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

