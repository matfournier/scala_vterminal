package examples

import java.awt.Point

import com.valkryst.VTerminal.Screen
import com.valkryst.VTerminal.printer.EllipsePrinter


object SampleEllipsePrinter extends App {

  def main: Unit = {
    val screen = new Screen
    val printer = new EllipsePrinter
    printer.setWidth(6)
    printer.setHeight(8)
    printer.print(screen.getTiles, new Point(10, 10))
    printer.printFilled(screen.getTiles, new Point(30, 10))
    screen.addCanvasToFrame
  }

  main

}
