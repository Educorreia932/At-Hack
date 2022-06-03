package com.game

import org.cosplay.CPPixel.&&
import org.cosplay.{CPColor, CPEngine, CPGameInfo}

val BLUE_BLACK = CPColor("0x00000F")
val BG_PX = ' ' && (BLUE_BLACK, BLUE_BLACK) // Background pixel.

object Game:
	def main(args: Array[String]): Unit = {
		CPEngine.init(
			CPGameInfo(name = "Text Input Example"),
			System.console() == null || args.contains("emuterm")
		)

		CPEngine.startGame(TerminalScene)

		sys.exit(0)
	}
		
