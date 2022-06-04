package com.game

import org.cosplay.CPColor.*
import org.cosplay.CPPixel.*
import org.cosplay.*
import org.cosplay.CPKeyboardKey.*

object GameState {
	var time = 100.0
}

object TerminalScene extends CPScene("terminal", None, BG_PX) {

	private val timerSprite = new CPLabelSprite(3, 3, 1, text = GameState.time.toString, C_WHITE) :
		override def update(ctx: CPSceneObjectContext): Unit =
			super.update(ctx)

			GameState.time -= 0.1
			this.setText(GameState.time.toInt.toString)

	addObjects(
		new TypingInput(3, 6, 1, 50, 50, "hello world"),
		timerSprite
	)
}
