package examples.shared

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
