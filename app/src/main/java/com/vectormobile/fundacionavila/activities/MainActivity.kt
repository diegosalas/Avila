package com.vectormobile.fundacionavila.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.vectormobile.fundacionavila.R

class MainActivity : AppCompatActivity() {
    private var citySelected = ""

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageAvila = findViewById(R.id.imageAvila) as ImageView
        val imageBannerAvila = findViewById(R.id.img_banner_av) as ImageView
        val imageValledolid= findViewById(R.id.imageValledolid) as ImageView
        val imageBannerValladoid = findViewById(R.id.img_banner_va) as ImageView

        imageAvila.setOnClickListener{
            citySelected = "Avila"

            goHome ()

        }
        imageBannerAvila.setOnClickListener{
            citySelected = "Avila"
            goHome ()

        }
        imageValledolid.setOnClickListener{
            citySelected= "Valladolid"
            goHome ()

        }
        imageBannerValladoid.setOnClickListener{
            citySelected= "Valladolid"
            goHome ()

        }





    }

    private fun goHome () {
        val sharedCity =  getSharedPreferences("SELECTION", 0)
        var editor = sharedCity.edit()

        editor.putString("CITY",citySelected).apply()
        editor.putBoolean("STARTING_APP", true).apply()
        Log.d("city at Main",citySelected)


        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra( "CITY", citySelected)
        startActivity(intent)

    }


}


