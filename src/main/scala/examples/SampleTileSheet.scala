package examples

import java.io.IOException

import com.valkryst.VTerminal.Screen
import com.valkryst.VTerminal.font.FontLoader


object SampleTileSheet {
    val font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1)
    val screen = new Screen(100, 33, font)
    var counter = 0

    val xRange = Range(0, screen.getWidth)
    val yRange = Range(0, screen.getHeight)

    val characters = Range(0, Char.MaxValue)
    val validCharacters = characters.filter(c => font.isCharacterSupported(c.toChar)).iterator

    val hsdf= 9

    for {
      y <- yRange
      x <- xRange
    } yield {
      if (validCharacters.hasNext) {
        val tile = screen.getTileAt(x, y)
        val character = validCharacters.next
        tile.setCharacter(character.toChar)
      }
    }
    screen.addCanvasToFrame()
}