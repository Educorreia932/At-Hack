package com.game

import org.cosplay.CPColor.*
import org.cosplay.CPPixel.&&
import org.cosplay.*
import org.cosplay.CPKeyboardKey.*

object TerminalScene extends CPScene("terminal", None, BG_PX) {
	val text = "hello world"
	var time = 100.0
	var guess: String = ""
	var currentPosition = 0

	def mkSkin(active: Boolean, passwd: Boolean): (Char, Int, Boolean) => CPPixel =
		(ch: Char, pos: Int, isCur: Boolean) => {
			if (pos < text.length) {
				if (ch == ' ')
					text.charAt(pos) && (C_WHITE, C_BLACK)

				else if (ch == text.charAt(pos))
					ch && (C_GREEN, C_BLACK)


				else
					ch && (C_RED, C_BLACK)
			}

			else
				ch && (C_BLACK, C_BLACK)
		}

	private val pwdTin = new CPTextInputSprite(
		"text",
		6,
		8,
		1,
		50,
		50,
		"",
		mkSkin(true, true),
		mkSkin(false, true),
		submitKeys = Seq(KEY_ENTER),
		next = Option("user")
	) :
		override def update(ctx: CPSceneObjectContext): Unit =
			var update = true

			if (ctx.getKbEvent.nonEmpty) {
				ctx.getKbEvent.get.key match {
					case KEY_BACKSPACE =>
						if (guess.nonEmpty && text.substring(0, guess.length).equals(guess))
							update = false
							
						else
							guess = guess.dropRight(1)

					case _ =>
						guess = guess + ctx.getKbEvent.get.key.ch
				}
			}

			if (update)
				super.update(ctx)

	private val timerSprite = new CPLabelSprite(6, 3, 1, text = time.toString, C_WHITE) :
		override def update(ctx: CPSceneObjectContext): Unit =
			super.update(ctx)

			time -= 0.1
			this.setText(time.toInt.toString)

	addObjects(
		pwdTin,
		timerSprite
	)
}
