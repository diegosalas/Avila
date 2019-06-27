package com.vectormobile.fundacionavila.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import com.vectormobile.fundacionavila.R




private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var textInformation: TextView? = null
private var imagenToInformation: ImageView? = null
private var textAvisoLegal: TextView? = null
private var imagenToAvisoLegal: ImageView? = null
private var imgBack: ImageView? = null



class SettingsFragment : Fragment() {



    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_settings, container, false)
        textInformation = rootView.findViewById(R.id.textInfo)
        imagenToInformation = rootView.findViewById(R.id.imgTomenuinfo)
        textAvisoLegal = rootView.findViewById(R.id.text_aviso_legal1)
        imagenToAvisoLegal = rootView.findViewById(R.id.img_to_aviso_legal1)


        textInformation!!.setOnClickListener{
            val nextFrag = menuinfoFragment()
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, nextFrag, "info")
                .addToBackStack(null)
                .commit()
        }
        imagenToInformation!!.setOnClickListener{

            val nextFrag = menuinfoFragment()
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, nextFrag, "info")
                .addToBackStack(null)
                .commit()
        }

        textAvisoLegal!!.setOnClickListener{
            val nextFrag = AvisoLegalFragment()
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, nextFrag, "aviso")
                .addToBackStack(null)
                .commit()
        }
        imagenToAvisoLegal!!.setOnClickListener{

            val nextFrag = AvisoLegalFragment()
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, nextFrag, "aviso")
                .addToBackStack(null)
                .commit()
        }

        return rootView

    }






}

