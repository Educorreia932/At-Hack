package com.game

import org.cosplay.*
import games.*
import CPColor.*
import CPPixel.*
import CPArrayImage.*
import CPFIGLetFont.*
import CPKeyboardKey.*
import prefabs.shaders.*
import pong.shaders.*
import CPSlideDirection.*
import org.apache.commons.lang3.SystemUtils
import org.cosplay.prefabs.particles.confetti.CPConfettiEmitter
import util.control.Breaks._


object StartScreen extends CPScene("start", None, BG_PX) {
	private val titleSprite = new CPLabelSprite(x = 50, y = 20, z = 1, font = FIG_POISON, text = "@hack", C_WHITE)
	private val infoSprite = new CPLabelSprite(x = 65, y = 40, z = 1, text = "Press Tab to hack", C_WHITE)
	private val inptuSprite = CPOffScreenSprite(ctx => ctx.getKbEvent match {
		case Some(evt) =>
			evt.key match 
				case KEY_TAB =>
					ctx.addScene(TypingScene, true)

				case _ => ()
		case None => ()
	})

	addObjects(titleSprite, infoSprite, inptuSprite)
}
