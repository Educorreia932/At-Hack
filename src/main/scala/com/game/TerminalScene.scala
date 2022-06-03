package com.game

import org.cosplay.CPColor.*
import org.cosplay.CPPixel.&&
import org.cosplay.*
import org.cosplay.CPKeyboardKey.*

object TerminalScene extends CPScene("terminal", None, BG_PX) {
	val text = "hello world"
	var guess = ""

	def mkSkin(active: Boolean, passwd: Boolean): (Char, Int, Boolean) => CPPixel =
		(ch: Char, pos: Int, isCur: Boolean) => {
			if (pos < text.length && ch == ' ')
				text.charAt(pos) && (C_BLACK, C_WHITE)

			else if (pos < text.length && ch == text.charAt(pos))
				ch && (C_BLACK, C_GREEN)

			else
				ch && (C_BLACK, C_RED)
		}

	private val pwdTin = CPTextInputSprite(
		"text",
		6,
		8,
		1,
		200,
		200,
		"",
		mkSkin(true, true),
		mkSkin(false, true),
		submitKeys = Seq(KEY_ENTER),
		next = Option("user")
	)

	addObjects(
		pwdTin,
	)
}
