package com.vectormobile.fundacionavila.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vectormobile.fundacionavila.R
import kotlinx.android.synthetic.main.activity_full_video.*

import android.net.Uri


class FullVideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_video)
        val link = intent.getStringExtra("link")
        val uri = Uri.parse(link)

        fullvideoView.setVideoURI(uri)
        fullvideoView.start()
    }
}
