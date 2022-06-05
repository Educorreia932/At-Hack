package com.game

import org.cosplay.CPColor.*
import org.cosplay.CPPixel.*
import org.cosplay.*
import CPKeyboardKey.*
import CPArrayImage.*

object TypingScene extends CPScene("terminal", None, BG_PX) {
	private def playMusic() = {
		val music = CPSound(src = "bgMusic.wav")

		new CPOffScreenSprite : // Off-screen sprite to start music auto-play.
			override def onStart(): Unit =
				super.onStart()

			music.loop(1500)
	}

	playMusic()

	private val layoutImage = CPImage.load(
		"window.xp",
		(px, _, _) => px.withBg(None)
	)

	private val layoutSprite = new CPImageSprite(x = 0, y = 0, z = 0, layoutImage)

	private val lines = prepSeq(
		"""
		  |sudo apt-get update
		  |sudo apt-get upgrade
		  |sudo apt-get install openssh-client
		  |
		  |sudo systemctl enable ssh
		  |sudo ufw allow ssh
		  |
		  |ssh nyaa@192.168.2.13
		  |scp hack.sh nyaa@192.168.2.13:/tmp/
		  |
		  |vim hack.sh
		  |:wq
		  |
		  |chmod +x hack.sh
		  |./hack
		"""
	)

	private val inputs = lines.zipWithIndex.map {
		case (line, i) =>
			new TypingInputSprite(
				s"line-${i}",
				50,
				5 + i,
				1,
				if i != lines.length - 1 then Option(s"line-${i + 1}") else None,
				line.trim
			)
	}

	private val focus = CPOffScreenSprite(ctx => if ctx.getSceneFrameCount == 0 then ctx.acquireFocus("line-0"))
	private val countdown: Array[CPSceneObject] = Countdown.objects

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

	private val timeBarImg = mkTimeBarImage(C_WHITE, GameState.time / 25)
	private final val INIT_VAL = -1f
	private val timeBarSpr = new CPImageSprite(x = 50, y = 67, z = 2, timeBarImg, false) :
		private var y = INIT_VAL

		override def reset(): Unit =
			super.reset()
			y = INIT_VAL

		override def update(ctx: CPSceneObjectContext): Unit =
			super.update(ctx)

			if (GameState.time > 0) {
				GameState.time -= 0.2

				this.setImage(mkTimeBarImage(C_WHITE, GameState.time))
			}

			else
				ctx.addScene(FailureScene, true)

	addObjects(
		inputs ++
			countdown :+
			layoutSprite :+
			timeBarSpr :+
			focus: _*
	)
}
