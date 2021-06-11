package info.glukhov.rutube.utils

import kotlin.math.max
import kotlin.math.min

object Utils {
    fun sectToTimeStr(timeMillis: Int): String? {
        val sec = timeMillis / 1000
        val hours = sec / 3600
        val minutes = sec % 3600 / 60
        val seconds = sec % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    fun getProgressiveSeekStep(videoDuration: Int, holdingKeyFor: Long): Int {
        val MAX_BASE_STEP_MILLIS = 15000
        val MIN_BASE_STEP_MILLIS = 5000
        val MAX_STEPS_PER_FILE = 20
        var baseStep = min(MAX_BASE_STEP_MILLIS, videoDuration / MAX_STEPS_PER_FILE)
        baseStep = max(MIN_BASE_STEP_MILLIS, baseStep)
        val step: Int
        step = when {
            holdingKeyFor < 1000 -> {
                baseStep
            }
            holdingKeyFor < 3000 -> {
                baseStep * 3
            }
            holdingKeyFor < 6000 -> baseStep * 6
            else -> {
                min(baseStep * 20, videoDuration / MAX_STEPS_PER_FILE)
            }
        }
        return step
    }
}