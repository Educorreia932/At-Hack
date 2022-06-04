package com.game

import org.cosplay.games.*
import org.cosplay.*
import CPColor.*
import CPPixel.*
import CPKeyboardKey.*

val BLUE_BLACK = CPColor("0x00000F")
val BG_PX = ' ' && (BLUE_BLACK, BLUE_BLACK) // Background pixel.

object Game:
	def main(args: Array[String]): Unit = {
		val gameInfo = CPGameInfo(name = "Hack Attack")

		// Initialize the engine.
		CPEngine.init(gameInfo, System.console() == null || args.contains("emuterm"))

		try
			CPEngine.startGame(PewPewScene)
		finally
			CPEngine.dispose()

		sys.exit(0)
	}

