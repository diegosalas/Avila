package com.vectormobile.fundacionavila.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.vectormobile.fundacionavila.R
import com.vectormobile.fundacionavila.models.Monument

private var textInformation: TextView? = null
private var textVisitasGuiadas: TextView? = null
private var textCentros: TextView? = null
private var textAvilaAccesible: TextView? = null
private var textAvisoLegal: TextView? = null
private var imagenToAvisoLegal: ImageView? = null
private var imgOpen: ImageView? = null



class menuinfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_menuinfo, container, false)
        textInformation = rootView.findViewById(R.id.text_info_open)
        textCentros = rootView.findViewById(R.id.text_centros)
        textVisitasGuiadas = rootView.findViewById(R.id.text_visitas_guiadas)
        textAvilaAccesible = rootView.findViewById(R.id.text_avila_accesible)
        textAvisoLegal = rootView.findViewById(R.id.text_aviso_legal2)
        imagenToAvisoLegal = rootView.findViewById(R.id.img_to_aviso_legal2)

        imgOpen = rootView.findViewById(R.id.img_open)



        textCentros!!.setOnClickListener{
            val nextFrag = infocentrosFragment()
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, nextFrag, "centros")
                .addToBackStack("settings")
                .commit()
        }
        textVisitasGuiadas!!.setOnClickListener{
            val nextFrag = VisitasGuiadasFragment()
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, nextFrag, "visitas")
                .addToBackStack("settings")
                .commit()
        }
        textAvilaAccesible!!.setOnClickListener{
            val nextFrag = AvilaAccesibleFragment()
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, nextFrag, "accesible")
                .addToBackStack("settings")
                .commit()
        }
        textInformation!!.setOnClickListener{
            val nextFrag = SettingsFragment()
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, nextFrag, "settings")
                .addToBackStack("map")
                .commit()
        }
        imgOpen!!.setOnClickListener{
            val nextFrag = SettingsFragment()
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, nextFrag, "settings")
                .addToBackStack("map")
                .commit()
        }
        textAvisoLegal!!.setOnClickListener{
            val nextFrag = AvisoLegalFragment()
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, nextFrag, "aviso")
                .addToBackStack("settings")
                .commit()
        }
        imagenToAvisoLegal!!.setOnClickListener{

            val nextFrag = AvisoLegalFragment()
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, nextFrag, "aviso")
                .addToBackStack("settings")
                .commit()
        }
        return rootView
    }



}
