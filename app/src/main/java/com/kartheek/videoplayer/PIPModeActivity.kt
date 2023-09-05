package com.kartheek.videoplayer

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.PictureInPictureParams
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Rational
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.kartheek.videoplayer.databinding.ActivityMainBinding
import com.kartheek.videoplayer.databinding.ActivityPipBinding

@androidx.media3.common.util.UnstableApi
class PIPModeActivity : AppCompatActivity() {

    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityPipBinding.inflate(layoutInflater)
    }

    companion object{
        private var isLocked: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.enterPipButton.setOnClickListener{
            enterPiPMode()
        }

        // Set the video to play
        viewBinding.videoView.setVideoPath("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4")
        viewBinding.videoView.start()

    }

    private fun enterPiPMode() {
        // Check if PiP mode is supported
        if (isPipSupported()) {
            // Create a PictureInPictureParams object
            val pipParams = PictureInPictureParams.Builder()
                .setAspectRatio(Rational(16, 9))
                .build()

            // Enter PiP mode
            enterPictureInPictureMode(pipParams)
        } else {
            Log.d("PIP", "PiP mode is not supported")
        }
    }


    private fun isPipSupported(): Boolean {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
    }


}