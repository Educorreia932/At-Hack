package com.game

import org.cosplay.CPColor.*
import org.cosplay.CPPixel.*
import org.cosplay.*
import CPKeyboardKey.*
import CPArrayImage.*
import CPFIGLetFont.*

object Countdown {
	private val timerSprite = new CPLabelSprite(x = 50, y = 59, z = 1, font = FIG_ALLIGATOR2, text = GameState.time.toInt.toString, C_WHITE) :
		override def update(ctx: CPSceneObjectContext): Unit =
			super.update(ctx)

			GameState.time -= 0.05
			this.setText(BigDecimal(GameState.time).setScale(1, BigDecimal.RoundingMode.HALF_UP).toString + "%")
			
			if (GameState.time <= 0)
				ctx.addScene(FailureScene, true, true)

	val objects: Array[CPSceneObject] = Array(timerSprite)
}
