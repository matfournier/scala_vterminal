package examples.part4

import java.awt.{Dimension, Point}

import com.valkryst.VTerminal.Screen

import examples.shared.{Room, VMap}

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
