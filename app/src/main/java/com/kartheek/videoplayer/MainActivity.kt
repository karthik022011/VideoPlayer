package com.kartheek.videoplayer

import android.annotation.SuppressLint
import android.app.PictureInPictureParams
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

@androidx.media3.common.util.UnstableApi
class MainActivity : AppCompatActivity() {
    lateinit var playerView:PlayerView

    private val viewBinding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var mediaController: MediaControllerCompat

    companion object{
        private var isLocked: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        val player: ExoPlayer = ExoPlayer.Builder(this)
            .build()
            .also{
                    exoplayer ->
                viewBinding.videoPlayer.player = exoplayer
            }

        // Build the media item.
        val mediaItem = MediaItem.Builder()
            .setUri("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4")
            .build()

        // Set the media item to be played.
        player.addMediaItem(mediaItem)
        // Prepare the player.
        player.prepare()
        // Start the playback.
        player.play()

      //  lockScreen()
        viewBinding.lockButton.setOnClickListener {
            enterPiPMode()
        }
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

    private fun exitPiPMode() {

    }


    private fun isPipSupported(): Boolean {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
    }



    fun lockScreen(){
        viewBinding.lockButton.setOnClickListener {
            if(!isLocked){
                //for hiding
                isLocked = true
                viewBinding.videoPlayer.hideController()
                viewBinding.videoPlayer.useController = false
                viewBinding.lockButton.setImageResource(R.drawable.close_lock_icon)
            }
            else{
                //for showing
                isLocked = false
                viewBinding.videoPlayer.useController = true
                viewBinding.videoPlayer.showController()
                viewBinding.lockButton.setImageResource(R.drawable.lock_open_icon)
            }
        }
    }


}