package com.game

import org.cosplay.CPPixel.&&
import org.cosplay.{CPColor, CPEngine}

val BLUE_BLACK = CPColor("0x00000F")
val BG_PX = ' ' && (BLUE_BLACK, BLUE_BLACK) // Background pixel.

object Game {
	def main = {
		CPEngine.startGame(TerminalScene)
	}

	sys.exit(0)
}
