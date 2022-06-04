package com.game

import org.cosplay.CPColor.*
import org.cosplay.CPKeyboardKey.*
import org.cosplay.*
import org.cosplay.CPPixel.*

import scala.annotation.static

private var incorrectIndex: Int = 0

private def skin(text: String) =
	(ch: Char, pos: Int, _: Boolean) => {
		if (pos < text.length) {
			if (ch == ' ')
				text.charAt(pos) && (C_WHITE, C_BLACK)

			else if (pos < incorrectIndex)
				ch && (C_GREEN, C_BLACK)

			else
				ch && (C_RED, C_BLACK)
		}

		else
			ch && (C_BLACK, C_BLACK)
	}

class TypingInput(
	x: Int,
	y: Int,
	z: Int,
	visLen: Int,
	maxBuf: Int,
	text: String,
	private var next: Option[String] = None,
) extends CPTextInputSprite(
	s"input-spr-${CPRand.guid6}",
	x, y, z,
	visLen, maxBuf,
	"",
	skin(text), skin(text),
	next,
	Seq(KEY_ESC), Seq(KEY_ENTER)
) {
	var guess: String = ""
	var currentPosition = 0
	var wordScore = 0

	override def update(ctx: CPSceneObjectContext): Unit =
		var update = true

		if (ctx.getKbEvent.nonEmpty) {
			ctx.getKbEvent.get.key match {
				case KEY_BACKSPACE =>
					if (guess.nonEmpty && text.substring(0, guess.length).equals(guess))
						update = false // Don't let the user delete correct answers

					else
						guess = guess.dropRight(1)
						currentPosition -= 1

				case _ =>
					val ch = ctx.getKbEvent.get.key.ch
					guess += ch // Append new character to guess
					println(ch)

					if (guess.charAt(currentPosition) == text.charAt(currentPosition) && text.substring(0, guess.length).equals(guess)) {
						incorrectIndex = currentPosition + 1
						wordScore += 1

						if (ch == ' ') {
							GameState.time += wordScore
							wordScore = 0
						}
					}

					currentPosition += 1
			}
		}

		if (update)
			super.update(ctx)
}
