package o1.city

import o1.grid.*
import scala.util.Random
import o1.util.nice.pairs.*

////////////////// NOTE TO STUDENTS ///////////////////////////
// For the purposes of our course, it’s not necessary that you
// understand the implementation of this class. You will need
// to use some of its methods, though.
///////////////////////////////////////////////////////////////

/** A `CityMap` is a square grid that represents a simplified map of a city. It is a
  * [[o1.grid.Grid Grid]] whose elements are [[Demographic]] objects. That is, each
  * [[o1.grid.GridPos GridPos]] (“street address”) on a `CityMap` is either `Vacant`
  * or stores a household that belongs to aparticular demographic.
  *
  * A `CityMap` is mutable. As a household moves, the corresponding `Demographic`
  * object moves to a different `GridPos` on the `CityMap`.
  *
  * @param homesPerSide  the number of street addresses in each row and each column of
  *                      the city grid (which is always square)
  * @param populations   a vector of pairs in which each `Demographic` is matched with
  *                      a count of how many times it appears on this city map */
class CityMap(homesPerSide: Int, private val populations: Vector[(Demographic, Int)]) extends Grid[Demographic](homesPerSide, homesPerSide):

  /** Generates the elements that initially occupy the grid. In the case of a `CityMap`
    * grid, this means generating new `Demographic` objects at random locations so
    * that their distribution matches the `populations` parameter of the `CityMap`. */
  def initialElements =
    def copies(demographic: Demographic, copyCount: Int) = Vector.fill(copyCount)(demographic)
    val empties = this.size - this.populations.seconds.sum
    val allContents = this.populations.flatMap(copies) ++ copies(Vacant, empties)
    Random.shuffle(allContents)

  /** Determines whether the household at the given “street address” (`GridPos`)
    * belongs to the given demographic.
    * @param address        a location on the city grid to examine
    * @param householdType  a demographic that the household at the given address is compared to (may be
    *                       `Vacant`, too, in which case this method checks if the address is vacant) */
  def matches(address: GridPos, householdType: Demographic) =
    (this(address), householdType) match
      case (dg1: Occupied, dg2: Occupied) => dg1.label == dg2.label
      case (dg1, dg2) => dg1 == dg2

  /** Returns all the locations in the city. */
  val allAddresses = this.allPositions

end CityMap

