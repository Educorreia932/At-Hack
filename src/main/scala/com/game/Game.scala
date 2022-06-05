package com.game

import org.cosplay.CPColor.*
import org.cosplay.CPKeyboardKey.*
import org.cosplay.*
import org.cosplay.CPPixel.*

val BG_PX = ' ' && (C_BLACK, C_BLACK) // Background pixel.

object Game:
	def main(args: Array[String]): Unit = {
		CPEngine.init(
			CPGameInfo(name = "Level Up 2022"),
			System.console() == null || args.contains("emuterm")
		)

		CPEngine.startGame(TypingScene)

		sys.exit(0)
	}
		
