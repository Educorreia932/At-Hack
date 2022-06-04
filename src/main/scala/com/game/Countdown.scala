package com.game

import org.cosplay.CPColor.*
import org.cosplay.CPPixel.*
import org.cosplay.*
import CPKeyboardKey.*
import CPArrayImage.*
import CPFIGLetFont.*

object Countdown {
	private val timerSprite = new CPLabelSprite(x = 3, y = 3, z = 1, font = FIG_POISON, text = GameState.time.toInt.toString, C_WHITE) :
		override def update(ctx: CPSceneObjectContext): Unit =
			super.update(ctx)

			GameState.time -= 0.05
			this.setText(GameState.time.toInt.toString)

	val objects: Array[CPSceneObject] = Array(timerSprite)
}
