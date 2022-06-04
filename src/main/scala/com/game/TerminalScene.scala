package com.game

import org.cosplay.CPColor.*
import org.cosplay.CPPixel.*
import org.cosplay.*
import CPKeyboardKey.*
import CPArrayImage.*

object TerminalScene extends CPScene("terminal", None, BG_PX) {
	private def terminalImage: CPImage = new CPArrayImage(
		prepSeq(
			"""
			  |	                               ⎯⠀❐⠀⤬
			  |┌────────────────────────────────────┐
			  |│                                    │
			  |│                                    │
			  |│                                    │
			  |│                                    │
			  |│                                    │
			  |│                                    │
			  |│                                    │
			  |│                                    │
			  |└────────────────────────────────────┘
      """),
		(ch, _, _) => ch & C_WHITE
	)

	private val terminalSprite = new CPImageSprite(x = 30, y = 15, z = 0, terminalImage)

	private val timerSprite = new CPLabelSprite(3, 3, 1, text = GameState.time.toString, C_WHITE) :
		override def update(ctx: CPSceneObjectContext): Unit =
			super.update(ctx)

			GameState.time -= 0.1
			this.setText(GameState.time.toInt.toString)

	val lines =
		"""hello world
world hello"""

	private val inputs = lines.split("\n").zipWithIndex.map {
		case (line, i) => new CompleteInputSprite(
			s"line-${i}",
			31, 
			17 + i, 
			1,
			50,
			50,
			next = Option(s"line-${i + 1}"),
			text = line.trim
		)
	}

	private val focus = CPOffScreenSprite(ctx => if ctx.getSceneFrameCount == 0 then ctx.acquireFocus("line-0"))

	addObjects(
		inputs :+
		timerSprite :+
		terminalSprite :+
		focus: _*
	)
}
