package com.vectormobile.fundacionavila.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView

import com.vectormobile.fundacionavila.R


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var webView: WebView? = null
private var imgBack: ImageView? = null


class VisitasGuiadasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_visitas_guiadas, container, false)
        webView = rootView.findViewById(R.id.webview)
        webView!!.loadUrl("file:///android_asset/files/visitasguiadas.html")
        imgBack = rootView.findViewById(R.id.img_back)
        imgBack!!.setOnClickListener{
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, SettingsFragment(), "settings")
                .addToBackStack(null)
                .commit()
        }
        return rootView
    }


}
