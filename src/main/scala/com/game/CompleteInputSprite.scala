package com.game

import org.cosplay.CPColor.*
import org.cosplay.CPKeyboardKey.*
import org.cosplay.*
import org.cosplay.CPPixel.*

import scala.collection.mutable

class CompleteInputSprite(
	id: String = s"input-spr-${CPRand.guid6}",
	x: Int,
	y: Int,
	z: Int,
	visLen: Int,
	maxBuf: Int,
	private var next: Option[String] = None,
	submitKeys: Seq[CPKeyboardKey] = Seq(KEY_ENTER),
	text: String
) extends CPSceneObject(id) :
	require(maxBuf >= visLen, "'maxBuf' must be >= 'visLen'.")

	private val dim = CPDim(visLen, 1)
	private val buf = mutable.ArrayBuffer.empty[Char]
	private var currentPosition = -1
	private var lastStart = 0
	private var ready = false
	private val pxs = mutable.ArrayBuffer.empty[CPPixel]
	private var res: Option[String] = None
	private var lastCorrect = -1

	reset()

	private def reset(): Unit =
		buf.clear()
		val len = buf.length
		lastStart = (len - visLen).max(0)
		currentPosition = if len < maxBuf then len else len - 1

	override def render(ctx: CPSceneObjectContext): Unit =
		val skin = if ctx.isFocusOwner then this.skin(true) else this.skin(false)
		pxs.clear()
		val delta = currentPosition - lastStart
		val start = if delta >= 0 && delta < visLen then lastStart else lastStart + delta.sign
		lastStart = start
		val end = start + visLen
		val len = buf.length
		var i = start

		while i < end do
			val ch = if i < len then buf(i) else ' '
			pxs += skin(ch, i, i == currentPosition)
			i += 1

		ctx.getCanvas.drawPixels(x, y, z, pxs.toSeq)

	override def update(ctx: CPSceneObjectContext): Unit =
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

					case key if submitKeys.contains(key) =>
						if (finished()) {
							done(Option(buf.toString()))

							if (next.isDefined)
								ctx.acquireFocus(next.get)

							// TODO: Finish stage
						}

					case key if key.isPrintable =>
						if currentPosition < maxBuf then
							buf.insert(currentPosition, key.ch)

							currentPosition += 1

							if (correct())
								lastCorrect += 1

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
