package com.game

import org.cosplay.CPColor.*
import org.cosplay.CPPixel.*
import org.cosplay.*
import org.cosplay.CPKeyboardKey.*

object TerminalScene extends CPScene("terminal", None, BG_PX) {
	private val timerSprite = new CPLabelSprite(3, 3, 1, text = GameState.time.toString, C_WHITE) :
		override def update(ctx: CPSceneObjectContext): Unit =
			super.update(ctx)

			GameState.time -= 0.1
			this.setText(GameState.time.toInt.toString)

	val lines =
		"""hello world
world hello"""

	private val inputs = lines.split("\n").zipWithIndex.map {
		case (line, i) => new CompleteInputSprite(
			s"line-${i}",
			3, 6 + i, 1,
			50,
			50,
			next = Option(s"line-${i + 1}"),
			text = line.trim
		)
	}

	private val focus = CPOffScreenSprite(ctx => if ctx.getSceneFrameCount == 0 then ctx.acquireFocus("line-0"))

	addObjects(
		inputs :+ timerSprite :+ focus: _*
	)
}
