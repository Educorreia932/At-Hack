package com.game

import org.cosplay.CPColor.*
import org.cosplay.CPPixel.&&
import org.cosplay.*
import org.cosplay.CPKeyboardKey.*

object TerminalScene extends CPScene("terminal", None, BG_PX) {
	val text = "hello world"

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

	val pwdTin = new CPTextInputSprite(
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
	)

	private val timerSprite = new CPLabelSprite(6, 3, 1, text = "100", C_WHITE):
		override def update(ctx: CPSceneObjectContext): Unit = 
			this.setText((this.getText.toInt - 1).toString)

	addObjects(
		pwdTin,
		timerSprite
	)
}
