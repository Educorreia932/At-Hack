package com.game

import org.cosplay.CPColor.*
import org.cosplay.CPKeyboardKey.*
import org.cosplay.*
import org.cosplay.CPPixel.*

import scala.collection.mutable

class TypingInputSprite(
	id: String = s"input-spr-${CPRand.guid6}",
	x: Int,
	y: Int,
	z: Int,
	private var next: Option[String] = None,
	text: String,
) extends CPSceneObject(id) :
	private val dim = CPDim(text.length, 1)
	private val buf = mutable.ArrayBuffer.empty[Char]
	private var currentPosition = -1
	private var lastStart = 0
	private var ready = false
	private val pxs = mutable.ArrayBuffer.empty[CPPixel]
	private var res: Option[String] = None
	private var lastCorrect = -1
	private val submitKeys = Seq(KEY_ENTER)
	private var wordScore = 0

	private val correctSnd = CPSound("correct.wav")
	private val incorrectSnd = CPSound("error.wav")

	reset()

	private def reset(): Unit =
		buf.clear()
		val len = buf.length
		lastStart = (len - text.length).max(0)
		currentPosition = if len < text.length then len else len - 1

	override def render(ctx: CPSceneObjectContext): Unit =
		val skin = if ctx.isFocusOwner then this.skin(true) else this.skin(false)
		pxs.clear()
		val delta = currentPosition - lastStart
		val start = if delta >= 0 && delta < text.length then lastStart else lastStart + delta.sign
		lastStart = start
		val end = start + text.length
		val len = buf.length
		var i = 0

		while i < end do
			val ch = if i < len then buf(i) else ' '
			pxs += skin(ch, i, i == currentPosition)
			i += 1

		ctx.getCanvas.drawPixels(x, y, z, pxs.toSeq)

	override def update(ctx: CPSceneObjectContext): Unit =
		if (text.isEmpty && ctx.isFocusOwner) {
			ctx.acquireFocus(next.get)
		}

		ctx.getKbEvent match
			case Some(evt) =>
				evt.key match
					case KEY_BACKSPACE =>
						// Don't let the user delete correct answers
						if (!correct()) {
							if (currentPosition > 0)
								currentPosition -= 1

							buf.remove(currentPosition)
						}

					case KEY_DEL =>
						if (buf.nonEmpty && currentPosition < buf.length)
							buf.remove(currentPosition)

					case KEY_TAB =>
						ctx.addScene(PewPewScene, true, true)

					case key if submitKeys.contains(key) =>
						if (finished()) {
							GameState.time += wordScore * 1.5

							done(Option(buf.toString()))

							if (next.isDefined)
								ctx.acquireFocus(next.get)

							else
								ctx.addScene(PewPewScene, true, true)
						}

					case key if key.isPrintable =>
						if (key == KEY_SPACE) {
							GameState.time += wordScore
							wordScore = 0
						}

						if currentPosition < text.length then
							buf.insert(currentPosition, key.ch)

							if (key.ch != text.charAt(currentPosition))
								incorrectSnd.play()

							else
								correctSnd.play()

							currentPosition += 1

							if (correct()) {
								lastCorrect += 1
								wordScore += 1
								correctSnd.play()
							}


					case _ => ()

			case None => ()

	private def done(optRes: Option[String]): Unit =
		ready = true
		res = optRes

		if res.isEmpty then
			reset()

	def clear(): Unit =
		ready = false
		res = None
		reset()

	private def correct() =
		text.substring(0, buf.length) == buf.mkString("")

	private def finished() =
		text == buf.mkString("")

	private def skin(active: Boolean) =
		(ch: Char, pos: Int, _: Boolean) => {
			if (pos < text.length) {
				if (!active)
					text.charAt(pos) && (C_WHITE.darker(0.3), C_BLACK)

				else if (pos <= lastCorrect)
					ch && (C_GREEN, C_BLACK)

				else if (ch == ' ')
					text.charAt(pos) && (C_WHITE, C_BLACK)

				else
					ch && (C_RED, C_BLACK)
			}

			else
				ch && (C_BLACK, C_BLACK)
		}

	def getNext: Option[String] = next

	def setNext(next: Option[String]): Unit = this.next = next

	def isReady: Boolean = ready

	def getResult: Option[String] = res

	def getDim: CPDim = dim

	def getX: Int = x

	def getY: Int = y

	def getZ: Int = z
