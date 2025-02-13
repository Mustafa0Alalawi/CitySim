package o1.city

import o1._
import scala.util.Random

class Simulator:

  private var cityMap = CityMap(1, Vector())
  private var similarityDesired = 0.0

  def startNew(squaresPerSide: Int, fillPercent: Int, demographics: Vector[Occupied], occupiedRatio: Int, similarityDesired: Int) =
    import o1.util.*
    val residents = fillPercent * (squaresPerSide * squaresPerSide) / 100
    val reds = (occupiedRatio atLeast 0 atMost 100) * residents / 100
    val others = (residents - reds) / (demographics.size - 1)
    val allCounts = reds +: Vector.fill(demographics.size - 1)(others)
    this.cityMap = CityMap(squaresPerSide, demographics zip allCounts)
    this.similarityDesired = similarityDesired atLeast 0 atMost 100

  def squaresPerSide = this.cityMap.width

  def findDemographic(demographic: Demographic): Vector[GridPos] =
    cityMap.allPositions.filter(pos => cityMap(pos) == demographic)

  def allAddresses = this.cityMap.allPositions

  def dissatisfiedResidents: Vector[GridPos] =
    def isDissatisfied(pos: GridPos, demographic: Demographic): Boolean =
      val neighbors = cityMap.neighbors(pos, includeDiagonals = true).filter(_ != Vacant)  
      if neighbors.isEmpty then false
      else neighbors.count(_ == demographic).toDouble / neighbors.size < similarityDesired / 100.0
    cityMap.allPositions.filter(pos =>
      cityMap(pos) match
        case Vacant => false
        case demo: Occupied => isDissatisfied(pos, demo)
    )

  def moveResidents() =
    val vacantAddresses = this.findDemographic(Vacant).toBuffer
    val toMove = Random.shuffle(dissatisfiedResidents)

    toMove.foreach { resident =>
    if (vacantAddresses.nonEmpty) then {
      val newHome = Random.shuffle(vacantAddresses).head  // Pick a random address from the buffer of vacant homes
      cityMap(newHome) = cityMap(resident)               // Move the resident to the new home
      cityMap(resident) = Vacant                         // Mark the old residence as vacant
      
      vacantAddresses += resident                        // Add the old residence to the buffer of vacant homes
      vacantAddresses -= newHome                         // Remove the new home from the buffer of vacant homes
    }
  }
  
  def residents: Map[Demographic, Vector[GridPos]] =
    cityMap.allPositions.groupBy(cityMap(_))

  def satisfactionLevel = 1.0 - this.dissatisfiedResidents.size.toDouble / this.cityMap.size

end Simulator

private[o1] object Simulator:
  def withCustomSeed() =
    // Random.setSeed(123456)
    Simulator()

end Simulator
