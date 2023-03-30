package com.example.mediaplayer

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.SeekBar

class MainActivity : AppCompatActivity() {

    lateinit var mediaPlayer : MediaPlayer //Late initialize
    var  totalTime : Int = 0 //The duration of audio. Primitive data types can't be lateinit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        val btnPlay = findViewById<ImageView>(R.id.play)
        val btnStop = findViewById<ImageView>(R.id.stop)
        val btnPause = findViewById<ImageView>(R.id.pause)
        val btnSeekBar = findViewById<SeekBar>(R.id.seekBar)

        mediaPlayer = MediaPlayer.create(this, R.raw.music) //Initializing media-player. Music is the mp3 file we want to play
        mediaPlayer.setVolume(1f,1f)
        mediaPlayer.isLooping = true
        totalTime = mediaPlayer.duration



        btnPlay.setOnClickListener{
            mediaPlayer.start()
        }

        btnPause.setOnClickListener{
            mediaPlayer.pause()
        }

        btnStop.setOnClickListener{
            mediaPlayer?.stop()
            mediaPlayer.reset()
            mediaPlayer.release() //for not taking too much storage
//            finish()
        }

        // when user changes the time stamp of the music, it should reflect that change
        btnSeekBar.max = totalTime
        btnSeekBar.setOnSeekBarChangeListener(/* l = */ object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser){
                    mediaPlayer.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        // change the seekbar position based on the music.
        val handler = Handler()
        handler.postDelayed( object : Runnable{
            override fun run(){
                try{
                    btnSeekBar.progress = mediaPlayer.currentPosition
                    handler.postDelayed(this, 1000)
                }
                catch (exception : Exception){
                    btnSeekBar.progress = 0 //Will reset the seekbar progress to zero.
                }
            }

        }, 0)
    }
}