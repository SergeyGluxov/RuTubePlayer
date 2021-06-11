package info.glukhov.rutube.player.rutube

import android.content.Context
import android.view.View
import ru.rutube.interfaces.IPlayerContext
import ru.rutube.interfaces.IRutubePlayer
import ru.rutube.interfaces.IRutubePlayerListener
import ru.rutube.interfaces.IRutubePlayerPlugin
import ru.rutube.models.RutubePlayerOperation
import ru.rutube.models.RutubePlayerState

class RTubePlayer : IRutubePlayerPlugin {

    private var player: IRutubePlayer? = null

    override fun hasView(): Boolean {
        return false
    }

    override fun requestOperation(operation: RutubePlayerOperation?): Boolean {
        return true
    }

    override fun deactivate(player: IRutubePlayer?) {
    }

    override fun activate(player: IRutubePlayer?) {
        this.player = player
    }

    override fun getView(player: Context?): View? {
        return null
    }

    fun isPlaying(): Boolean? {
        return player?.isPlaying
    }

    fun getPlayerState(): RutubePlayerState? {
        return player?.playerState
    }

    fun setListener(listener: IRutubePlayerListener) {
        player!!.addListener(listener)
    }


    fun play() {
        player!!.play(this)
    }

    fun pause() {
        player!!.pause(this)
    }

    fun resume() {
        player!!.play(this)
    }

    fun getDuration(): Int {
        return player?.duration!!
    }

    fun context(): IPlayerContext? {
        return player!!.playerContext
    }

    fun playUrl(url: String) {
        player!!.changeVideo(url, this)
        player!!.play(this)
    }

    fun quality(index: Int) {
        player!!.changeQuality(index, this)
        player!!.pause(this)
        player!!.play(this)
    }

    fun currentQuality(): Int {
        return player!!.qualityIndex
    }

    fun seek(time: Int) {
        player!!.seek(time, this)
    }


    fun currentTime(): Int {
        return player!!.currentTime
    }

    fun runTestFailed() {
        player!!.changeVideo("0e55cada5e97feb53a1d81616d7e74fc1", null)
        player!!.play(this)
    }

}