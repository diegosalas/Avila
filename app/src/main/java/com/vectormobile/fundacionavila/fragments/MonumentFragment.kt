package com.vectormobile.fundacionavila.fragments


import android.content.Intent
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
import com.vectormobile.fundacionavila.activities.FullVideoActivity
import com.vectormobile.fundacionavila.adapter.MonumentPhotoAdapter
import com.vectormobile.fundacionavila.adapter.MonumentVideoAdapter
import com.vectormobile.fundacionavila.listener.RecyclerMonumentPhotoListener
import com.vectormobile.fundacionavila.listener.RecyclerMonumentVideoListener
import com.vectormobile.fundacionavila.loadByResource
import com.vectormobile.fundacionavila.models.MonumentPhotos
import com.vectormobile.fundacionavila.models.MonumentVideos
import com.vectormobile.fundacionavila.toast
import kotlinx.android.synthetic.main.fragment_monument.view.*
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream

import android.view.Window.FEATURE_NO_TITLE

import android.app.Dialog
import android.view.Window
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dialog_photos.*


private var TextViewMonumentTitle: TextView? = null
private var TextViewMonumentTitle2: TextView? = null


private var ImgMonumentVideo: ImageView? = null
private var imgViewPlay: ImageView? = null
private var btnBegin: ImageView? = null



private var textjsonDate: TextView? = null
private var textjsonBuild: TextView? = null
private var textjsonType: TextView? = null
private var textjsonHistory: TextView? = null
private var textjsonCuriosities: TextView? = null
private var textjsonUses: TextView? = null

private var btnBackMonuments: ImageView? = null
private var imgPhotosdialog: ImageView? = null

var latitude = ""
var longitude = ""
var name = ""




class MonumentFragment : Fragment() {
    var position = 0
    private lateinit var recyclerMonumentVideos: RecyclerView
    private lateinit var recyclerMonumentPhotos: RecyclerView
    private val list: ArrayList<MonumentVideos> by lazy { getMonuments() }
    private val listPhotos: ArrayList<MonumentPhotos> by lazy { getMonumentsPhotos() }
    private lateinit var adapter: MonumentVideoAdapter
    private lateinit var adapterPhotos: MonumentPhotoAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        val v = inflater.inflate(R.layout.fragment_monument, container, false)


        // BackButton


        btnBackMonuments = v.findViewById(R.id.img_back)
        btnBackMonuments!!.setOnClickListener {
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, RutasyMonumentosFragment(), "monumentsandroutes")
                .addToBackStack("routes")
                .commit()

        }

        recyclerMonumentVideos = v.recyclerViewMonumentsVideos as RecyclerView
        recyclerMonumentPhotos = v.RecyclerViewMonumentPhotos as RecyclerView
        TextViewMonumentTitle= v.findViewById(R.id.TextViewMonumentTitle)
        TextViewMonumentTitle2= v.findViewById(R.id.TextViewMonumentTitle2)




        ImgMonumentVideo = v.findViewById<ImageView>(R.id.ImgMonumentVideo)
        imgPhotosdialog = v.findViewById<ImageView>(R.id.imagePhotoDialog)


        // PLAY MAIN VIDEO

        imgViewPlay = v.findViewById<ImageView>(R.id.imageViewPlayVideoMonument)



        imgViewPlay!!.setOnClickListener{
            var link : String
            if (arguments?.getString("mainvideo") == null){
                link ="https://fundacion-avila.s3-eu-west-1.amazonaws.com/ruta2-downavila/la-santa/video1.mp4"

            }else{
                 link = arguments!!.getString("mainvideo")
            }
                val intent = Intent (getActivity(), FullVideoActivity::class.java).putExtra("link",link)
                getActivity()?.startActivity(intent)

        }


        btnBegin = v.findViewById<ImageView>(R.id.buttonBeginRoute)



        btnBegin!!.setOnClickListener{


            val bundle = Bundle()
            bundle.putString("latitude", latitude)
            bundle.putString("longitude", longitude)
            bundle.putString("name", name)
            bundle.putString("pin", "frg")



            val nextFrag = MapFragment()

            nextFrag.arguments = bundle
            activity!!.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, nextFrag, "map")
                .addToBackStack("monument")
                .commit()
        }





        textjsonDate= v.findViewById(R.id.textjsonDate)
        textjsonBuild= v.findViewById(R.id.textjsonBuild)
        textjsonType= v.findViewById(R.id.textjsonType)
        textjsonHistory= v.findViewById(R.id.textjsonHistory)
        textjsonCuriosities= v.findViewById(R.id.textjsonCuriosities)
        textjsonUses= v.findViewById(R.id.textjsonUses)




        val sharedCity = activity?.getSharedPreferences("SELECTION", 0)?.getString("CITY", "Avila")

  /*
     Header
   */

        position = arguments!!.getInt("position")

        if (arguments?.getString("name") != null){
            val routeName = arguments!!.getString("name")
            TextViewMonumentTitle!!.text =  routeName
            TextViewMonumentTitle2!!.text =  routeName

        }
        if (arguments?.getString("image") != null){
            val routeImage = arguments!!.getString("image")
            val uri =  routeImage
            val tempImage:Int = activity!!.getResources().getIdentifier(uri,"drawable",
                getActivity()?.getPackageName())
            ImgMonumentVideo!!.loadByResource(tempImage)


        }

        Log.d("CITY ROUTES", sharedCity.toString())
        var cityfile = "avila.json"
        if (sharedCity!!.equals("Valladolid")) {

            val cityfile = "valladolid.json"
            readJson(cityfile, position)

        } else {
            val cityfile = "avila.json"
            readJson(cityfile, position)
        }



        /*

           VIDEO GALLERIES
         */


        setRecyclerMonumentVideos()
        setRecyclerMonumentPhotos()

        return v
    }



    public fun setRecyclerMonumentVideos() {
        recyclerMonumentVideos.setHasFixedSize(true)
        recyclerMonumentVideos.itemAnimator = DefaultItemAnimator()
        val horizontalLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerMonumentVideos.setLayoutManager(horizontalLayoutManager)
        adapter = MonumentVideoAdapter(list, object : RecyclerMonumentVideoListener {
            override fun onClick(monumentVideo: MonumentVideos, position: Int) {
                // From here we open the full screen video from the video gallery
                //  activity?.toast("Let's go to ${monumentVideo.link}!")

                activity?.let{
                    val intent = Intent (it, FullVideoActivity::class.java).putExtra("link",monumentVideo.link)
                    it.startActivity(intent)
                }



            }})
        recyclerMonumentVideos.adapter = adapter

    }

    public fun setRecyclerMonumentPhotos() {
        recyclerMonumentPhotos.setHasFixedSize(true)
        recyclerMonumentPhotos.itemAnimator = DefaultItemAnimator()
        val horizontalLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerMonumentPhotos.setLayoutManager(horizontalLayoutManager)
        adapterPhotos = MonumentPhotoAdapter(listPhotos, object : RecyclerMonumentPhotoListener {
            override fun onClick(monumentPhotos: MonumentPhotos, position: Int) {
                 // Here you could amplify the image to full screen
                 // activity?.toast("Image id: ${monumentPhotos.image}!")
                val settingsDialog = Dialog(activity)
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE)
                settingsDialog.setContentView(layoutInflater.inflate(R.layout.dialog_photos, null))
               // imgPhotosdialog!!.loadByResource(monumentPhotos.image)

                settingsDialog.show()

            }})
        recyclerMonumentPhotos.adapter = adapterPhotos

    }

    private fun getMonuments(): ArrayList<MonumentVideos> {
        return object : ArrayList<MonumentVideos>() {
            init {
                val sharedCity = activity?.getSharedPreferences("SELECTION", 0)?.getString("CITY", "Avila")
                Log.d("CITY ROUTES", sharedCity.toString())
                var cityfile = "avila.json"
                if (sharedCity!!.equals("Valladolid")) {

                    val cityfile = "valladolid.json"
                    fillList(cityfile)

                } else {
                    val cityfile = "avila.json"
                    fillList(cityfile)
                }




            }

            private fun fillList(cityfile: String) {
                var json: String? = null
                val inputSream: InputStream = activity!!.assets.open(cityfile)
                json = inputSream.bufferedReader().use { it.readText() }
                var jsonarr = JSONArray(json)
                var jsonobjc = jsonarr.getJSONObject(0)
                val monumetJson = jsonobjc.getJSONArray("monuments")
                val id: Int = monumetJson.getJSONObject(position).getInt("id")
                val name = monumetJson.getJSONObject(position).getString("name")

                val list = monumetJson.getJSONObject(position).getJSONArray("videos")

                for (i in 0..list!!.length() - 1) {
                    val uri  = "@drawable/" + list.getJSONObject(i).getString("image")
                    var link = list.getJSONObject(i).getString("link")
                    if (link.equals("")){
                        link = "https://fundacion-avila.s3-eu-west-1.amazonaws.com/Ruta+1+-+DownValladolid/Academia+de+caballeria/videos/ACADEMIA+(A%C3%B1o)+(1).mp4"
                    }

                    val tempImage: Int = activity!!.getResources().getIdentifier(
                        uri, "drawable",
                        getActivity()?.getPackageName()
                    )
                    add(MonumentVideos(tempImage,link))
                }



            }
        }
    }


    /// Read monument photos
    private fun getMonumentsPhotos(): ArrayList<MonumentPhotos> {
        return object : ArrayList<MonumentPhotos>() {
            init {
                val sharedCity = activity?.getSharedPreferences("SELECTION", 0)?.getString("CITY", "Avila")
                Log.d("CITY ROUTES", sharedCity.toString())
                var cityfile = "avila.json"
                if (sharedCity!!.equals("Valladolid")) {

                    val cityfile = "valladolid.json"
                    fillListphotos(cityfile)

                } else {
                    val cityfile = "avila.json"
                    fillListphotos(cityfile)
                }




            }

            private fun fillListphotos(cityfile: String) {
                var json: String? = null
                val inputSream: InputStream = activity!!.assets.open(cityfile)
                json = inputSream.bufferedReader().use { it.readText() }
                var jsonarr = JSONArray(json)
                var jsonobjc = jsonarr.getJSONObject(0)
                val monumetJson = jsonobjc.getJSONArray("monuments")
                val id: Int = monumetJson.getJSONObject(position).getInt("id")
                val name = monumetJson.getJSONObject(position).getString("name")

                val list = monumetJson.getJSONObject(position).getJSONArray("images")

                for (i in 0..list!!.length() - 1) {
                    val uri  = "@drawable/" + list.getJSONObject(i).getString("image")


                    val tempImage: Int = activity!!.getResources().getIdentifier(
                        uri, "drawable",
                        getActivity()?.getPackageName()
                    )
                    add(MonumentPhotos(tempImage))
                }



            }
        }
    }



    /// Read details for earch monument
    fun readJson(cityfile: String, routeId: Int) {
        try {
            Log.d("consultasJson", "cityfile: ${cityfile}   : position: ${position}")
            var json: String? = null
            val inputSream: InputStream = activity!!.assets.open(cityfile)
            json = inputSream.bufferedReader().use { it.readText() }
            var jsonarr = JSONArray(json)
            var jsonobjc = jsonarr.getJSONObject(0)
            val monumetJson = jsonobjc.getJSONArray("monuments")
            val id: Int = monumetJson.getJSONObject(position).getInt("id")
            name = monumetJson.getJSONObject(position).getString("name")
            val uri = "@drawable/" + monumetJson.getJSONObject(position).getString("image")
            val tempImage: Int = activity!!.getResources().getIdentifier(
                uri, "drawable",
                getActivity()?.getPackageName())
            Log.d("resultados: ","${monumetJson} ")


            textjsonDate!!.text = monumetJson.getJSONObject(position).getString("date")
            textjsonBuild!!.text = monumetJson.getJSONObject(position).getString("build")
            textjsonType!!.text = monumetJson.getJSONObject(position).getString("type")
            textjsonHistory!!.text = monumetJson.getJSONObject(position).getString("history")
            textjsonCuriosities!!.text = monumetJson.getJSONObject(position).getString("curiosities")
            textjsonUses!!.text = monumetJson.getJSONObject(position).getString("uses")
            var list = monumetJson.getJSONObject(position).getJSONArray("videos")
            var localization =  monumetJson.getJSONObject(position).getJSONArray("localization")

             latitude = localization.getJSONObject(0).getString("latitude")
             longitude = localization.getJSONObject(0).getString("longitude")

            Log.d("localization", "${latitude}   :   ${longitude}")

        } catch (e: IOException) {
    }
    }


}





