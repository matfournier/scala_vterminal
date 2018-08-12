package examples.shared

import java.awt._

import com.valkryst.VTerminal.Tile
import com.valkryst.VTerminal.component.Layer


// https://stackoverflow.com/questions/3299776/in-scala-how-can-i-subclass-a-java-class-with-multiple-constructors
// for extending java classes in scala

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

