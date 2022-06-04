package com.game

import org.cosplay.CPColor.*
import org.cosplay.CPPixel.*
import org.cosplay.*
import CPKeyboardKey.*
import CPArrayImage.*

object TypingScene extends CPScene("terminal", None, BG_PX) {
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

	private val lines =
		"""hello world
world hello""".split("\n")

	private val inputs: Array[TypingInputSprite] = lines.zipWithIndex.map {
		case (line, i) => new TypingInputSprite(
			s"line-${i}",
			31,
			17 + i,
			1,
			if i != lines.length - 1 then Option(s"line-${i + 1}") else None,
			line.trim
		)
	}

	private val focus = CPOffScreenSprite(ctx => if ctx.getSceneFrameCount == 0 then ctx.acquireFocus("line-0"))
	private val countdown: Array[CPSceneObject] = Countdown.objects

	addObjects(
		inputs ++
		countdown :+
		terminalSprite :+
		focus: _*
	)
}
