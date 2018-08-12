package examples.shared

import java.awt.Color

import com.valkryst.VTerminal.misc.ColorFunctions

trait Sprite {
  def character: Char
  def backgroundColor: Color
  def foregroundColor: Color

  def darkBackgroundColor: Color = ColorFunctions.shade(backgroundColor, 0.5)
  def darkForegroundColor: Color = ColorFunctions.shade(foregroundColor, 0.5)

}

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

// not going to bother w/ null handling here
case class CustomSprite(character: Char, backgroundColor: Color, foregroundColor: Color) extends Sprite
