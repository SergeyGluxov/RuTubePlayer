package info.glukhov.rutube

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import info.glukhov.rutube.models.RVideo
import info.glukhov.rutube.player.rutube.RTubePlayer
import info.glukhov.rutube.utils.Utils
import kotlinx.android.synthetic.main.activity_main.*
import ru.rutube.RutubePlayer
import ru.rutube.RutubePlayerListenerHelper
import ru.rutube.interfaces.IRutubePlayerListener
import ru.rutube.models.RutubePlayerState
import ru.rutube.models.context.PlayerContext


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var plugin: RTubePlayer
    private var handler = Handler()
    private var handlerHider = Handler()

    private var isUserTakeControl = false

    private val videoDuration = -1
    private var videoCurrentTime = 0

    private var rVideo = RVideo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPlayer()
        setupPlayerControl()
        playUrl("54954f15a3bee1485f783dbcb592337d")
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        Log.d(TAG, "Window keyCode: $keyCode")
        isUserTakeControl = true
        showPlayerControlAndHeader()
        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_CENTER -> {
                plugin.pause()
                return true
            }
        }
        return false
    }

    private fun initPlayer() {
        val fragmentManager = fragmentManager
        plugin = RTubePlayer()
        val player = fragmentManager.findFragmentById(R.id.fragment) as RutubePlayer
        player.attachPlugin(plugin)
        player.attachPlugin(EndscreenPlugin())
        plugin.setListener(listener)
    }

    private fun playUrl(url: String) {
        plugin.playUrl(url)
        handler.post(getVideoData)
        handler.post(changeQuality)
        handler.postDelayed({ hidePlayerControlAndHeader() }, 3000)
        handler.postDelayed(userControlTimeout, 3000)
        handlerHider.postDelayed(hidePlayerControl, 3000)

    }


    //Раз в 2 секунды меняет isUserTakeControl на false
    private var userControlTimeout = object : Runnable {
        override fun run() {
            isUserTakeControl = false
            handler.postDelayed(this, 2000)
        }
    }

    private var hidePlayerControl = object : Runnable {
        override fun run() {
            Log.d(TAG, "TRY HIDE PLAYER: $isUserTakeControl")
            if (!isUserTakeControl) {
                hidePlayerControlAndHeader()
            }
            handlerHider.postDelayed(this, 3000)
        }
    }

    private var changeQuality = object : Runnable {
        override fun run() {
            if (plugin.isPlaying()!!) {
                if (plugin.currentQuality() < rVideo.quality.size) {
                    plugin.quality(rVideo.quality.size - 1)
                    handler.removeCallbacks(this)
                } else {
                    handler.removeCallbacks(this)
                }
            } else {
                handler.postDelayed(this, 500)
            }
        }
    }

    var getVideoData = object : Runnable {
        override fun run() {
            if (plugin.isPlaying()!!) {
                Log.d(TAG, "run: " + plugin.context())
                rVideo = RVideo(
                    (plugin.context() as PlayerContext).trackinfo,
                    (plugin.context() as PlayerContext).options.balancer.videoStreams
                )
                Log.d(
                    TAG, "VideoData: \n" +
                            "[Title] = ${rVideo.trackInfo.title} \n" +
                            "[Quality Size] = ${rVideo.quality.size} \n" +
                            "[Duration] = ${rVideo.trackInfo.duration} \n"
                )
                setTitle(rVideo.trackInfo.title)
                setupSeekBar(rVideo.trackInfo.duration)
                handler.removeCallbacks(this)
            } else {
                handler.postDelayed(this, 500)
            }
        }
    }


    fun setTitle(value: String) {
        tvTitle.text = value
    }

    fun setupSeekBar(duration: Int) {
        endTime.text = Utils.sectToTimeStr(rVideo.trackInfo.duration)
        seekBar.max = duration
        seekBar.keyProgressIncrement = Utils.getProgressiveSeekStep(videoDuration, 0)
        seekBar.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(p0: View?, keyCode: Int, p2: KeyEvent?): Boolean {
                Log.d(TAG, "seek bar onKey: $keyCode")
                isUserTakeControl = true
                when (keyCode) {
                    KeyEvent.KEYCODE_DPAD_RIGHT -> {
                        tvTimeCurrent.text = Utils.sectToTimeStr(seekBar.progress)
                    }
                    KeyEvent.KEYCODE_DPAD_LEFT -> {
                        tvTimeCurrent.text = Utils.sectToTimeStr(seekBar.progress)
                    }
                    KeyEvent.KEYCODE_DPAD_CENTER -> {
                        plugin.seek(seekBar.progress)
                        return true
                    }
                }
                return false
            }
        })
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.d(TAG, "onProgressChanged: ")
                if (fromUser){
                    isUserTakeControl = true
                    plugin.seek(progress)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
        seekBar.requestFocus()
    }

    fun updateSeekBar(progress: Int) {
        tvTimeCurrent.text = Utils.sectToTimeStr(progress)
        seekBar.progress = progress
        videoCurrentTime = progress
    }

    private fun setupPlayerControl() {
        btnQuality.setOnClickListener { plugin.quality(5) }
        btnPlayOrPause.setOnClickListener { playOrPause() }
        btnPrev.setOnClickListener { playUrl("ce35b7e95e1279a463d1011e536c2ce8") }
        btnNext.setOnClickListener { playUrl("c0f4305a8eacd4fe2fddb2ed3006a2a1") }
    }


    //-------------------------------Callbacks-------------------------------------------------------

    private val listener: IRutubePlayerListener = object : RutubePlayerListenerHelper() {
        override fun changeCurrentTime(currentTime: Int) {
            super.changeCurrentTime(currentTime)
            Log.d(TAG, "changeCurrentTime: $currentTime")
            updateSeekBar(currentTime)
        }

        override fun changeQuality(qualityIndex: Int?) {
            super.changeQuality(qualityIndex)
            Log.d(TAG, "changeQuality: ")
        }

        override fun changeVideo() {
            super.changeVideo()
            Log.d(TAG, "changeVideo: ")
        }

        override fun changePlayerState(state: RutubePlayerState?) {
            super.changePlayerState(state)
            Log.d(TAG, "changePlayerState: ${state!!.index}")
            if (state == RutubePlayerState.SeekingState) {
                plugin.pause()
                plugin.play()

            } else if (state == RutubePlayerState.SeekComplete) {
                plugin.play()
            }
        }

        override fun changeDuration(duration: Int) {
            super.changeDuration(duration)
            Log.d(TAG, "changeDuration: $duration")
            handler.post(getVideoData)
        }
    }

    private fun playOrPause() {
        if (plugin.isPlaying()!!) {
            plugin.pause()
        } else {
            plugin.resume()
        }
    }

    private fun showPlayerControlAndHeader() {
        headerPlayer.visibility = View.VISIBLE
        controlPlayer.visibility = View.VISIBLE
    }

    private fun hidePlayerControlAndHeader() {
        headerPlayer.visibility = View.GONE
        controlPlayer.visibility = View.GONE
        btnPlayOrPause.requestFocus()
    }

}