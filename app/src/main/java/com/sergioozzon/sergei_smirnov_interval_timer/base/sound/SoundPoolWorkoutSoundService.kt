package com.sergioozzon.sergei_smirnov_interval_timer.base.sound

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool
import android.os.Handler
import android.os.Looper
import com.sergioozzon.sergei_smirnov_interval_timer.R

private const val MAX_STREAMS = 2
private const val BEEP_PRIORITY = 1
private const val BEEP_LOOP = 0
private const val BEEP_RATE = 1f
private const val BEEP_VOLUME = 1f
private const val FINISH_BEEP_DELAY_MS = 300L

class SoundPoolWorkoutSoundService(
    context: Context,
) : WorkoutSoundService {

    private val mainHandler = Handler(Looper.getMainLooper())
    private var isBeepLoaded = false
    private var pendingBeeps = 0

    private val soundPool = SoundPool.Builder()
        .setMaxStreams(MAX_STREAMS)
        .setAudioAttributes(
            AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        )
        .build()

    private val beepSoundId = soundPool.load(context.applicationContext, R.raw.beep, BEEP_PRIORITY)

    init {
        soundPool.setOnLoadCompleteListener { _, sampleId, status ->
            if (sampleId == beepSoundId && status == 0) {
                isBeepLoaded = true
                repeat(pendingBeeps) {
                    playBeep()
                }
                pendingBeeps = 0
            }
        }
    }

    override fun playWorkoutStart() {
        playBeepWhenReady()
    }

    override fun playNextInterval() {
        playBeepWhenReady()
    }

    override fun playWorkoutFinished() {
        playBeepWhenReady()
        mainHandler.postDelayed(
            { playBeepWhenReady() },
            FINISH_BEEP_DELAY_MS
        )
    }

    private fun playBeepWhenReady() {
        if (isBeepLoaded) {
            playBeep()
        } else {
            pendingBeeps += 1
        }
    }

    private fun playBeep() {
        soundPool.play(
            beepSoundId,
            BEEP_VOLUME,
            BEEP_VOLUME,
            BEEP_PRIORITY,
            BEEP_LOOP,
            BEEP_RATE
        )
    }
}
