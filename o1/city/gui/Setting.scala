////////////////// NOTE TO STUDENTS //////////////////////////
// For the purposes of our course, it’s not necessary
// that you understand or even look at the code in this file.
//////////////////////////////////////////////////////////////

package o1.city.gui

import o1.gui.swingops.*
import o1.gui.layout.*
import o1.util.DoNothing
import scala.swing.*
import scala.swing.event.ValueChanged
import scala.language.adhocExtensions // enable extension of Swing classes

// A single, labeled slider-based setting in the GUI that selects and Int value.
private[gui] open class Setting(name: String, minValue: Int, initialValue: Int, maxValue: Int, val format: Int => String) extends EasyPanel:

  private val slider = new Slider:
    this.orientation = Orientation.Horizontal
    this.min   = minValue
    this.value = initialValue
    this.max   = maxValue
    this.preferredSize = super.preferredSize.withWidth(125)
  private var previousValue = initialValue
  private val nameLabel = Label(name)
  private val valueLabel = new Setting.FixedLabel { horizontalAlignment = Alignment.Left }

  this.updateLabel()

  this.placeNW(nameLabel,  (0, 0), TwoWide, Slight,            NoBorder)
  this.placeW(slider,      (0, 1), OneSlot, Slight,            (0, 2, 0, 4))
  this.placeNW(valueLabel, (1, 1), OneSlot, FillHorizontal(1), (0, 2, 2, 4))

  this.listenTo(slider)
  this.reactions += { case ValueChanged(source) if source == slider =>
    if this.releasedAtNewValue then
      this.previousValue = this.value
      onAdjust()
    this.updateLabel()
  }

  private def releasedAtNewValue = !this.slider.adjusting && this.previousValue != this.value


  def updateLabel() =
    valueLabel.text = this.format(this.value)

  def value = this.slider.value

  //noinspection ScalaUnusedExpression
  def onAdjust() =
    DoNothing

  override def enabled_=(value: Boolean) =
    super.enabled = value
    this.slider.enabled = value
    this.nameLabel.enabled = value
    this.valueLabel.enabled = value

end Setting


private object Setting:

  private class FixedLabel extends Label: // later: refactor?
    private var fixedSize: Option[Dimension] = None
    override def text_=(newText: String) =
      super.text = newText
      this.fixedSize match
        case Some(fixed) => this.preferredSize = fixed
        case None        => this.fixedSize = Some(this.preferredSize)

end Setting

