package com.vectormobile.fundacionavila.fragments


import android.os.Bundle
import android.util.Log
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
import com.vectormobile.fundacionavila.adapter.MonumentRaceAdapter
import com.vectormobile.fundacionavila.listener.RecyclerMonumentRaceListener
import com.vectormobile.fundacionavila.loadByResource
import com.vectormobile.fundacionavila.models.Direction
import com.vectormobile.fundacionavila.models.Monument
import com.vectormobile.fundacionavila.toast
import kotlinx.android.synthetic.main.fragment_rutasy_monumentos.view.*
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream

import android.os.Parcelable
import androidx.core.view.isVisible



private var TextViewTitle: TextView? = null
private var ImgViewForVideo: ImageView? = null
private var TextViewDescription: TextView? = null
private var TextViewDuration: TextView? = null
private var btnBeginRoute: ImageView? = null
private var btnBackRoutes: ImageView? = null

var arrayOfMonumentlocations = "["





class RouteFragment : Fragment() {
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: MonumentRaceAdapter
    private lateinit var tvTitleRace: TextView
    private lateinit var laneHideable: View
    private lateinit var imageVideo: ImageView
    private val list: ArrayList<Monument> by lazy { getMonuments() }


    var routeId = 0
    var position = 0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val v = inflater.inflate(R.layout.fragment_route, container, false)
        recycler = v.recyclerView as RecyclerView
        TextViewTitle = v.findViewById(R.id.tvTitleRoute)
        ImgViewForVideo = v.findViewById<ImageView>(R.id.ImgMonumentVideo)
        TextViewDescription = v.findViewById(R.id.textDescription)
        TextViewDuration = v.findViewById(R.id.textDuration)
        //tvTitleRace = v.findViewById(R.id.textViewCityName)
        //imageVideo = v.findViewById(R.id.imageViewVideo)
        //laneHideable = v.findViewById(R.id.verticalLineHideable)

      // BackButton
        btnBackRoutes = v.findViewById(R.id.img_back)
        btnBackRoutes!!.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, RutasyMonumentosFragment(), "monumentsandroutes")
                .addToBackStack("routes")
                .commit()

        }

       //monument race visibility






        /*tvTitleRace.setOnClickListener {
            if (imageVideo.isVisible && laneHideable.isVisible){
                imageVideo.visibility = View.VISIBLE
                laneHideable.visibility = View.VISIBLE
            } else{
                imageVideo.visibility = View.INVISIBLE
                laneHideable.visibility = View.INVISIBLE
            }
        }*/

        //Open Map Location

        btnBeginRoute = v.findViewById(R.id.buttonBeginRoute)

        btnBeginRoute!!.setOnClickListener{

            val bundle = Bundle()

            bundle.putString("arrayOfMonumentlocations", arrayOfMonumentlocations)


            val nextFrag = MapFragment()
            nextFrag.arguments = bundle

            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, nextFrag, "map")
                .addToBackStack("monuments")
                .commit()

        }

        // Header

         routeId = arguments!!.getInt("id")
         position = arguments!!.getInt("position")



        if (arguments?.getString("name") != null) {
            val routeName = arguments!!.getString("name")
            TextViewTitle!!.text = routeName

        }

        if (arguments?.getString("image") != null) {
            val routeImage = arguments!!.getString("image")
            val uri = routeImage
            val tempImage: Int = activity!!.getResources().getIdentifier(
                uri, "drawable",
                getActivity()?.getPackageName()
            )
            ImgViewForVideo!!.loadByResource(tempImage)

        }
        if (arguments?.getString("description") != null) {
            val routeDetails = arguments!!.getString("description")
            TextViewDescription!!.text = routeDetails
        }
        if (arguments?.getString("duration") != null) {
            val routeDuration = arguments!!.getString("duration")
            TextViewDuration!!.text = routeDuration
        }

        val sharedCity = activity?.getSharedPreferences("SELECTION", 0)?.getString("CITY", "Avila")
        Log.d("CITY ROUTES", sharedCity.toString())
        var cityfile = "avila.json"
        setRecyclerViewMonuments()
        return v
    }


    private fun setRecyclerViewMonuments() {
        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator()
        recycler.layoutManager = LinearLayoutManager(activity)

        adapter = (MonumentRaceAdapter(list, object : RecyclerMonumentRaceListener {
            override fun onClick(monument: Monument, position: Int) {
               //  activity?.toast("Let's go to ${monument.name}!")
                val bundle = Bundle()
                bundle.putInt("position", position)
                bundle.putString("name", "${monument.id}")
                bundle.putString("name", "${monument.name}")
                bundle.putString("image", "${monument.imgResource}")
                bundle.putString("mainvideo", "${monument.video}")


                val nextFrag = MonumentFragment()
                nextFrag.arguments = bundle

                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, nextFrag, "6")
                    .addToBackStack(null)
                    .commit()

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
                    readJson(cityfile, position)

                } else {
                    val cityfile = "avila.json"
                    readJson(cityfile, position)
                }


            }

            fun readJson(cityfile: String, routeId: Int) {
                try {
                    var json: String? = null
                    val inputSream: InputStream = activity!!.assets.open(cityfile)
                    json = inputSream.bufferedReader().use { it.readText() }
                    var jsonarr = JSONArray(json)
                    var jsonobjc = jsonarr.getJSONObject(position)
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

                        var localization =  monumetJson.getJSONObject(i).getJSONArray("localization")


                        //add(Direction(localization))
                        latitude = localization.getJSONObject(0).getString("latitude")
                        longitude = localization.getJSONObject(0).getString("longitude")





                        if (i != monumetJson!!.length() - 1){
                            arrayOfMonumentlocations += "{\"name\":\"${name}\",\"lat\":\"${latitude}\",\"lon\":\"${longitude}\"},"
                        }else{
                            arrayOfMonumentlocations += "{\"name\":\"${name}\",\"lat\":\"${latitude}\",\"lon\":\"${longitude}\"}]"
                        }

                        //arrayOfMonumentlocationslatitude.set(i, latitude)
                        //arrayOfMonumentlocationslongitude.set(i, longitude)






                        add(Monument(id, name, tempImage, video))
                    }
                } catch (e: IOException) {
                }

            }

        }
    }
}


