package examples

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