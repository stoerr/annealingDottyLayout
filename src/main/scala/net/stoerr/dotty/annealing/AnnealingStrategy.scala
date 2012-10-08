package net.stoerr.dotty.annealing

/**
 * @author <a href="http://www.stoerr.net/">Hans-Peter Stoerr</a>
 * @since 07.10.12
 */
trait AnnealingStrategy {

  /** @param energydifference difference in energy between steps
    * @param relativeTime between 0 and 1: time relative to full annealing time */
  def admissionProbability(energydifference: Double, relativeTime: Double): Double;

}

/** The probability of admitting an uphill step is independent of the step energy increase */
class EnergyIndependentAnnealing(val endprobability: Double) extends AnnealingStrategy {
  override def admissionProbability(energydifference: Double, relativeTime: Double) =
    if (energydifference < 0) 1 else math.pow(endprobability, relativeTime)
}

/** The probability of admitting an uphill step is independent of the step energy increase */
class EnergyDependentAnnealing(val starthalftime: Double, val endhalftime: Double) extends AnnealingStrategy {

  def halftime(relativeTime: Double) = starthalftime * math.pow((endhalftime / starthalftime), relativeTime)

  override def admissionProbability(energydifference: Double, relativeTime: Double) =
    if (energydifference < 0) 1
    else {
      math.pow(0.5, energydifference / halftime(relativeTime))
    }
}

class GreedyDescent extends AnnealingStrategy {
  /** @param energydifference difference in energy between steps
    * @param relativeTime between 0 and 1: time relative to full annealing time */
  def admissionProbability(energydifference: Double, relativeTime: Double) = if (energydifference < 0) 1 else 0
}
