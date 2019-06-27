package com.vectormobile.fundacionavila.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.vectormobile.fundacionavila.R
import com.vectormobile.fundacionavila.databinding.ActivityHomeBinding
import com.vectormobile.fundacionavila.fragments.*
import kotlinx.android.synthetic.main.fragment_rutasy_monumentos.*


class HomeActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    val fragment1: Fragment = MapFragment()
    val fragment2: Fragment = RutasyMonumentosFragment()
    val fragment3: Fragment = SettingsFragment()
    val fm = supportFragmentManager
    var active = fragment1
    var citySelected = ""

    private val toolbarIcons = arrayOf(
        R.drawable.avila_spinner,
        R.drawable.valladolid_spinner
    )
    private val toolbarIcons2 = arrayOf(
        R.drawable.valladolid_spinner,
        R.drawable.avila_spinner
    )
    private lateinit var binding: ActivityHomeBinding
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_map -> {

                fragmentTransaction(fragment1, "map")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_view -> {
                fragmentTransaction(fragment2,"whattosee")
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                fragmentTransaction(fragment3,"settings")
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
    private fun fragmentTransaction(fragment: Fragment, tag: String) {
        if(!fragment.isAdded())
        {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, fragment, tag)
                .commit()
            return;
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fm.beginTransaction().add(R.id.main_container,fragment1, "map").commit();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        val sharedApp = getSharedPreferences("SELECTION", 0)

        citySelected = sharedApp?.getString("CITY","Avila").toString()
        if( citySelected.equals("Avila")){
            binding.toolbarSpinner.adapter =
                ToolbarSpinnerAdapter(this, toolbarIcons)
        }else{

            binding.toolbarSpinner.adapter =
                ToolbarSpinnerAdapter(this, toolbarIcons2)
        }
        binding.toolbarSpinner.onItemSelectedListener = this

        binding.guiaTv.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        var startingApp: Boolean
        val sharedApp = getSharedPreferences("SELECTION", 0)
        var editor = sharedApp.edit()
        citySelected = sharedApp?.getString("CITY","Avila").toString()
        startingApp = sharedApp?.getBoolean("STARTING_APP", true)!!

        if (startingApp.equals(true)) {
            editor.putBoolean("STARTING_APP",false).apply()
        }else{
            editor.putBoolean("STARTING_APP",false).apply()
            if(position.equals(0)){
                editor.putString("CITY","Avila").apply()
                Log.d("spinner status","Selected: $position city sellected Avila $citySelected starting app $startingApp")

            }else{
                editor.putString("CITY","Valladolid").apply()
                Log.d("spinner status","Selected: $position city sellected Valladolid $citySelected starting app $startingApp")
            }
        }

    }

    override fun onBackPressed() {
        //super.onBackPressed()
    }
}

