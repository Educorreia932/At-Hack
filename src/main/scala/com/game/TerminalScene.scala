package com.game

import org.cosplay.CPColor.*
import org.cosplay.CPPixel.&&
import org.cosplay.*
import org.cosplay.CPKeyboardKey.*

object TerminalScene extends CPScene("terminal", None, BG_PX) {
	var time = 100.0

	private val timerSprite = new CPLabelSprite(3, 3, 1, text = time.toString, C_WHITE) :
		override def update(ctx: CPSceneObjectContext): Unit =
			super.update(ctx)

			time -= 0.1
			this.setText(time.toInt.toString)

	addObjects(
		new TypingInput(3, 6, 1, 50, 50, "hello world"),
		timerSprite
	)
}
