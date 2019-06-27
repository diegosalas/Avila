package com.vectormobile.fundacionavila.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.vectormobile.fundacionavila.R
import com.vectormobile.fundacionavila.models.Monument
import kotlinx.android.synthetic.main.fragment_rutasy_monumentos.view.*


import com.vectormobile.fundacionavila.adapter.MonumentAdapter
import com.vectormobile.fundacionavila.adapter.RouteAdapter
import com.vectormobile.fundacionavila.listener.RecyclerMonumentListener
import com.vectormobile.fundacionavila.listener.RecyclerRouteListener
import com.vectormobile.fundacionavila.models.Route
import com.vectormobile.fundacionavila.toast
import com.vectormobile.fundacionavila.activities.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream


class RutasyMonumentosFragment : Fragment() {

    private val list: ArrayList<Monument> by lazy { getMonuments() }
    private val listOfRoutes: ArrayList<Route> by lazy { getRoutes() }
    private lateinit var recycler: RecyclerView
    private lateinit var recyclerRoutes: RecyclerView
    private lateinit var adapter: MonumentAdapter
    private lateinit var adapterRoutes: RouteAdapter
    private val layoutManager by lazy { LinearLayoutManager(context) }

    override fun onCreateView(


        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_rutasy_monumentos, container, false)
        recycler = rootView.recyclerView as RecyclerView
        recyclerRoutes = rootView.recyclerViewRoutes as RecyclerView
        setRecyclerViewRoutes()
        setRecyclerViewMonuments()
        return rootView
    }

    public fun setRecyclerViewRoutes() {
        recyclerRoutes.setHasFixedSize(true)
        recyclerRoutes.itemAnimator = DefaultItemAnimator()
        val horizontalLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerRoutes.setLayoutManager(horizontalLayoutManager)

        adapterRoutes = (RouteAdapter(listOfRoutes, object : RecyclerRouteListener {
            override fun onClick(route: Route, position: Int) {


                //activity?.toast("Let's go to ${route.name}!")

                val bundle = Bundle()
                bundle.putString("position", "${position}")
                bundle.putInt("id", route.id)
                bundle.putString("name", "${route.name}")
                bundle.putString("image", "${route.imgResource}")
                bundle.putString("description", "${route.description}")
                bundle.putString("duration", "${route.duration}")


                val nextFrag = RouteFragment()
                nextFrag.arguments = bundle
                if(!nextFrag.isAdded()) {
                    activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, nextFrag, "routes")
                        .addToBackStack("routesandmonuments")
                        .commit()
                }
            }

            override fun onDelete(route: Route, position: Int) {
                listOfRoutes.remove(route)
                adapterRoutes.notifyItemRemoved(position)
                activity?.toast("${route.name} has been removed!")
            }
        }))
        recyclerRoutes.adapter = adapterRoutes
    }

    public fun setRecyclerViewMonuments() {
        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.layoutManager = LinearLayoutManager(activity)

        adapter = (MonumentAdapter(list, object : RecyclerMonumentListener {
            override fun onClick(monument: Monument, position: Int) {
                //  activity?.toast("Let's go to ${monument.name}!")

                val bundle = Bundle()
                bundle.putInt("position", position)
                bundle.putString("id", "${monument.id}")
                bundle.putString("name", "${monument.name}")
                bundle.putString("image", "${monument.imgResource}")
                bundle.putString("mainvideo", "${monument.video}")


                val nextFrag = MonumentFragment()
                nextFrag.arguments = bundle
                if(!nextFrag.isAdded()){
                    activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, nextFrag, "monument")
                        .addToBackStack("routesandmonuments")
                        .commit()
                }
            }

            override fun onDelete(monument: Monument, position: Int) {
                list.remove(monument)
                adapter.notifyItemRemoved(position)
                activity?.toast("${monument.name} has been removed!")
            }
        }))
        recycler.adapter = adapter
    }

    private fun getMonuments(): ArrayList<Monument> {
        return object : ArrayList<Monument>() {
            init {

                val sharedCity = activity?.getSharedPreferences("SELECTION", 0)?.getString("CITY", "Avila")
                Log.d("CITY ROUTES", sharedCity.toString())
                var cityfile = "avila.json"

                if (sharedCity!!.equals("Valladolid")) {

                    val cityfile = "valladolid.json"
                    readJson(cityfile)

                } else {
                    val cityfile = "avila.json"
                    readJson(cityfile)
                }


            }

            fun readJson(cityfile: String) {
                try {
                    var json: String? = null
                    val inputSream: InputStream = activity!!.assets.open(cityfile)
                    json = inputSream.bufferedReader().use { it.readText() }
                    var jsonarr = JSONArray(json)
                    /*  Despliga todos los munumentos por ruta ó solo lo de las primera ruta  */
                    //        for(i in 0..jsonarr.length()-1){

                    var jsonobjc = jsonarr.getJSONObject(0)
                    val monumetJson = jsonobjc.getJSONArray("monuments")
                    for (i in 0..monumetJson!!.length() - 1) {
                        val id: Int = monumetJson.getJSONObject(i).getInt("id")
                        val name = monumetJson.getJSONObject(i).getString("name")
                        val uri = "@drawable/" + monumetJson.getJSONObject(i).getString("image")

                        val tempImage: Int = activity!!.getResources().getIdentifier(
                            uri, "drawable",
                            getActivity()?.getPackageName()
                        )
                        val video = monumetJson.getJSONObject(i).getString("video")


                        add(Monument(id, name, tempImage, video))

                    }
                    //   }
                } catch (e: IOException) {
                }

            }


        }
    }


    private fun getRoutes(): ArrayList<Route> {
        return object : ArrayList<Route>() {

            init {
                val sharedCity = activity?.getSharedPreferences("SELECTION", 0)?.getString("CITY", "Avila")
                Log.d("CITY ROUTES", sharedCity.toString())
                var cityfile = "avila.json"

                if (sharedCity!!.equals("Valladolid")) {

                    val cityfile = "valladolid.json"
                    readJson(cityfile)

                } else {
                    val cityfile = "avila.json"
                    readJson(cityfile)
                }


            }

            fun readJson(cityfile: String) {
                try {
                    var json: String? = null
                    val inputSream: InputStream = activity!!.assets.open(cityfile)
                    json = inputSream.bufferedReader().use { it.readText() }
                    var jsonarr = JSONArray(json)

                    for (i in 0..jsonarr.length() - 1) {
                        var jsonobjc = jsonarr.getJSONObject(i)
                        val uri = "@drawable/" + jsonobjc.getString("image")
                        val tempImage: Int = activity!!.getResources().getIdentifier(
                            uri, "drawable",
                            getActivity()?.getPackageName()
                        )

                        add(
                            Route(
                                jsonobjc.getInt("id"),
                                jsonobjc.getString("name"),
                                tempImage,
                                jsonobjc.getString("duration"),
                                jsonobjc.getString("video"),
                                jsonobjc.getString("image"),
                                jsonobjc.getString("description")
                            )
                        )
                    }
                } catch (e: IOException) {
                }

            }


        }
    }


}


