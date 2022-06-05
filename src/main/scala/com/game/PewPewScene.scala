package com.game

import org.cosplay.*
import games.*
import CPColor.*
import CPPixel.*
import CPArrayImage.*
import CPFIGLetFont.*
import CPKeyboardKey.*
import prefabs.shaders.*
import pong.shaders.*
import CPSlideDirection.*
import org.apache.commons.lang3.SystemUtils
import org.cosplay.prefabs.particles.confetti.CPConfettiEmitter
import util.control.Breaks._

object PewPewScene extends CPScene("shooter", None, BG_PX) {
	// Game constants
	private final val INIT_VAL = -1f
	private final val PLAYER_SPEED = 2f
	private final val PROJECTILE_SPEED = -2

	// Game variables
	private val playing = true
	private var isPewPew = false
	private var time = 100.0

	private val layoutImage = CPImage.load(
		"window.xp",
		(px, _, _) => px.withBg(None)
	)

	private val layoutSprite = new CPImageSprite(x = 0, y = 0, z = 0, layoutImage)

	// Sprite builder functions
	private def mkEnemyImage(c: CPColor): CPImage =
		new CPArrayImage(
			prepSeq(
				"""
				  | \__/
				  | (oo)
				  |//||\\
      """),
			(ch, _, _) => ch & C_WHITE // Skin function.
		)

	private def mkBrickImage(c: CPColor): CPImage =
		new CPArrayImage(
			prepSeq(s"""|${219.toChar}${219.toChar}${219.toChar}"""),
			(ch, _, _) => ch & C_WHITE // Skin function.
		)

	private def mkPewPewImage(c: CPColor): CPImage =
		new CPArrayImage(
			prepSeq("""||"""),
			(ch, _, _) => ch & C_WHITE // Skin function.
		)

	private def mkTimeBarLabel(c: CPColor): CPImage =
		new CPArrayImage(
			prepSeq("""|TIME""".stripMargin),
			(ch, _, _) => ch & C_WHITE // Skin function.
		)

	private def mkTimeBarImage(c: CPColor, time: Double): CPImage =
		if time <= 0 then
			return new CPArrayImage(
				prepSeq(s"""|GAME OVER""".stripMargin),
				(ch, _, _) => ch & C_WHITE // Skin function.
			)

		val bar = s"${219.toChar}" * (time.toInt + 1)

		var color = C_WHITE

		if (time > 50)
			color = C_WHITE

		else if (time > 20)
			color = C_WHITE.darker(0.3)

		else
			color = C_WHITE.darker(0.5)

		new CPArrayImage(
			prepSeq(
				s"""|$bar""".stripMargin),
			(ch, _, _) => ch & color // Skin function.
		)

	private def mkPlayerImage(c: CPColor): CPImage =
		new CPArrayImage(
			prepSeq(
				"""
				  | 
				  | 
      			"""
			),
			(ch, _, _) => ch & C_WHITE // Skin function.
		)

	// Build sprites
	private val playerImg = mkPlayerImage(C5)
	private val playerW = playerImg.w

	private val enemyImg = mkEnemyImage(C3)
	private val enemyW = enemyImg.w

	private val brickImg = mkBrickImage(C3)
	private val brickH = brickImg.h
	private val brickW = brickImg.w

	private val pewPewImg = mkPewPewImage(C3)

	private val timeBarImg = mkTimeBarImage(C3, time / 25)

	// Player object
	private val playerSpr = new CPImageSprite(x = 99 - playerW / 2, y = 40, z = 2, playerImg, collidable = true) :
		private var x = INIT_VAL
		private var y = INIT_VAL

		// Clips the position of the player on the screen.
		private def clipPaddleX(canv: CPCanvas, currX: Float, dx: Float): Float =
			val maxX = 150 + playerW
			if dx > 0 then Math.min(maxX, currX + dx)
			else if dx < 0 then Math.max(currX + dx, 10 + playerW)
			else currX

		override def reset(): Unit =
			super.reset()
			x = INIT_VAL
			y = INIT_VAL

		override def update(ctx: CPSceneObjectContext): Unit =
			super.update(ctx)
			val canv = ctx.getCanvas

			if playing then
				if x == INIT_VAL then x = getX.toFloat
				if y == INIT_VAL then y = getY.toFloat

				def moveX(dx: Float): Unit =
					x = clipPaddleX(canv, x, dx)
					setX(x.round)

				ctx.getKbEvent match
					case Some(evt) =>
						evt.key match
							case KEY_LO_A | KEY_LEFT => moveX(-PLAYER_SPEED)
							case KEY_LO_D | KEY_RIGHT => moveX(PLAYER_SPEED)
							case KEY_SPACE => isPewPew = true
							case _ => ()
					case None => ()

	// Player projectile object
	private val projectileSpr = new CPImageSprite(x = 99 - playerW / 2, y = 40, z = 2, pewPewImg, collidable = true) :
		private var y = INIT_VAL

		// Initially hidden
		this.hide()

		override def reset(): Unit =
			super.reset()
			this.hide()
			setX(playerSpr.getX + 1)
			setY(playerSpr.getY)

		override def update(ctx: CPSceneObjectContext): Unit =
			super.update(ctx)

			y = getY.toFloat

			if isPewPew then

				if !this.isVisible then this.show()

				if y > 6 then
					setY(Math.max(y.round + PROJECTILE_SPEED, 0))
				else
					isPewPew = false
					this.reset()

				// Check for collisions
				breakable {
					for (col <- ctx.collisions()) {
						if col.getId != playerSpr.getId && col.getId != this.getId then
							col.hide()
							time += 3
							isPewPew = false
							this.reset()
							break
					}
				}

			else
				setX(playerSpr.getX + 1)
				setY(playerSpr.getY)

	// Time Bar objects
	private val timeBarSpr = new CPImageSprite(x = 50, y = 65, z = 2, timeBarImg, false) :
		private var y = INIT_VAL

		override def reset(): Unit =
			super.reset()
			y = INIT_VAL

		override def update(ctx: CPSceneObjectContext): Unit =
			super.update(ctx)

			if (time > 0) {
				time -= 0.2

				this.setImage(mkTimeBarImage(C3, time))
			}

			else
				ctx.addScene(FailureScene, true)

	// Add enemies to the scene
	for (nthEnemy <- 0 to 2) {
		addObjects(new CPImageSprite(x = 70 + nthEnemy * (enemyW + 21), y = 18, z = 2, enemyImg, collidable = true) :
			override def hide(): Unit =
				super.hide()
				setX(200)
		)
	}

	// Add bricks to the scene
	for (nthLevel <- 0 to 1) {
		val evenLevel = nthLevel % 2 == 0
		val maxBricks = if (evenLevel) 14 else 13

		for (nthBrick <- 0 to maxBricks) {
			val offset = if (evenLevel) 0 else 2
			addObjects(new CPImageSprite(x = 70 + offset + nthBrick * (brickW + 1), y = 10 + nthLevel * (brickH + 1), z = 2, brickImg, collidable = true) :
				override def hide(): Unit =
					super.hide()
					setX(200)
			)
		}
	}

	addObjects(
		layoutSprite,
		playerSpr,
		projectileSpr,
		timeBarSpr,
	)
}
