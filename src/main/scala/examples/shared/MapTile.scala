package examples.shared

case class MapTile(sprite: Sprite = DefaultSprites.UNKNOWNN,
                   movementCost: Int = 1,
                   solid: Boolean = true,
                   visible: Boolean = false)
