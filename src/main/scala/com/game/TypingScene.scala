package com.game

import org.cosplay.CPColor.*
import org.cosplay.CPPixel.*
import org.cosplay.*
import CPKeyboardKey.*
import CPArrayImage.*

object TypingScene extends CPScene("terminal", None, BG_PX) {
	private val layoutImage = CPImage.load(
		"window.xp",
		(px, _, _) => px.withBg(None)
	)
	
	private val layoutSprite = new CPImageSprite(x = 0, y = 0, z = 0, layoutImage)

	private val lines = prepSeq(
		"""
		  |sudo apt-get update
		  |sudo apt-get upgrade
		  |sudo apt-get install openssh-client
		  |
		  |sudo systemctl enable ssh
		  |sudo ufw allow ssh
		  |
		  |ssh nyaa@192.168.2.13
		  |scp hack.sh nyaa@192.168.2.13:/tmp/
		  |
		  |vim hack.sh
		  |:wq
		  |
		  |chmod +x hack.sh
		  |./hack
      	"""
	)

	private val inputs = lines.zipWithIndex.map {
		case (line, i) =>
			new TypingInputSprite(
				s"line-${i}",
				50,
				5 + i,
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
		layoutSprite :+
		focus: _*
	)
}
