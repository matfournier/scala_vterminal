package examples

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