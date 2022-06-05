package com.game

import org.cosplay.CPColor.*
import org.cosplay.CPPixel.*
import org.cosplay.*
import CPKeyboardKey.*
import CPArrayImage.*
import CPFIGLetFont.*

object FailureScene extends CPScene("failure", None, BG_PX) {
	private def image: CPImage = new CPArrayImage(
		prepSeq(
			"""
			  |                      :::!~!!!!!:.
			  |                  .xUHWH!! !!?M88WHX:.
			  |                .X*#M@$!!  !X!M$$$$$$WWx:.
			  |               :!!!!!!?H! :!$!$$$$$$$$$$8X:
			  |              !!~  ~:~!! :~!$!#$$$$$$$$$$8X:
			  |             :!~::!H!<   ~.U$X!?R$$$$$$$$MM!
			  |             ~!~!!!!~~ .:XW$$$U!!?$$$$$$RMM!
			  |               !:~~~ .:!M"T#$$$$WX??#MRRMMM!
			  |               ~?WuxiW*`   `"#$$$$8!!!!??!!!
			  |             :X- M$$$$       `"T#$T~!8$WUXU~
			  |            :%`  ~#$$$m:        ~!~ ?$$$$$$
			  |          :!`.-   ~T$$$$8xx.  .xWW- ~""##*"
			  |.....   -~~:<` !    ~?T#$$@@W@*?$$      /`
			  |W$@@M!!! .!~~ !!     .:XUW$W!~ `"~:    :
			  |#"~~`.:x%`!!  !H:   !WM$$$$Ti.: .!WUn+!`
			  |:::~:!!`:X~ .: ?H.!u "$$$B$$$!W:U!T$$M~
			  |.~~   :X@!.-~   ?@WTWo("*$$$W$TH$! `
			  |Wi.~!X$?!-~    : ?$$$B$Wu("**$RM!
			  |$R@i.~~ !     :   ~$$$$$B$$en:``
			  |?MXT@Wx.~    :     ~"##*$$$$M~⠀⠀⠀⠀⠀⠀⠀
			  |
			  |
			  |
			  |
			  |                 You failed⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
      """),
		(ch, _, _) => ch & C_WHITE
	)

	private val sprite = new CPImageSprite(x = 55, y = 20, z = 0, image)

	addObjects(sprite)
}
