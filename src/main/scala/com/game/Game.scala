package com.game

import org.cosplay.CPColor.*
import org.cosplay.CPKeyboardKey.*
import org.cosplay.*
import org.cosplay.CPPixel.*

val BG_PX = ' ' && (C_BLACK, C_BLACK) // Background pixel.

object Game:
	def main(args: Array[String]): Unit = {
		val dimension = CPDim(150, 70) 
		
		System.setProperty("COSPLAY_EMUTERM_FONT_NAME", "Perfect DOS VGA 437")
		System.setProperty("COSPLAY_EMUTERM_FONT_SIZE", "12")
		System.setProperty("COSPLAY_EMUTERM_CH_HEIGHT_OFFSET", "-1")

		CPEngine.init(
			CPGameInfo(name = "Level Up 2022", initDim = Option(dimension)),
			System.console() == null || args.contains("emuterm")
		)

		CPEngine.startGame(StartScreen)

		sys.exit(0)
	}
