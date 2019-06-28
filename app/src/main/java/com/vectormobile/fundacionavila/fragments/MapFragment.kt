package com.vectormobile.fundacionavila.fragments


import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*


import com.vectormobile.fundacionavila.R
import com.vectormobile.fundacionavila.adapter.RouteAdapter
import com.vectormobile.fundacionavila.listener.RecyclerRouteListener
import com.vectormobile.fundacionavila.models.Direction
import com.vectormobile.fundacionavila.models.Route

import com.vectormobile.fundacionavila.toast
import kotlinx.android.synthetic.main.fragment_map.view.*
import kotlinx.android.synthetic.main.fragment_monument.*
import org.json.JSONArray


import java.io.IOException
import java.io.InputStream


class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    override fun onMarkerClick(p0: Marker?): Boolean {
        return false
    }

    private lateinit var mMap: GoogleMap
    private lateinit var mapView: MapView
    private lateinit var openArrow: LinearLayoutCompat
    private lateinit var constraintOpened: ConstraintLayout
    private lateinit var closeArrow: ConstraintLayout
    private lateinit var adapterRoutes: RouteAdapter
    private lateinit var recyclerRoutes: RecyclerView
    private val listOfRoutes: ArrayList<Route> by lazy { getRoutes() }
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location
    private var lat: String? = null
    private var lon: String? = null
    private var nam: String? = null
    private var name: String? = null

    private var latitude: String? = null
    private var longitude: String? = null
    private var arrayOfMonumentlocations: String? = null


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_map, container, false)


        recyclerRoutes = rootView.recyclerViewRoutesMap as RecyclerView
        setRecyclerViewRoutes()
        openArrow = rootView.findViewById(R.id.arrow_open)
        constraintOpened = rootView.findViewById(R.id.routesBox)
        closeArrow = rootView.findViewById(R.id.layoutClose)

        openArrow.setOnClickListener { constraintOpened.visibility = View.VISIBLE }
        closeArrow.setOnClickListener { constraintOpened.visibility = View.INVISIBLE }

        if (arguments?.getString("latitude") != null) {
            latitude = arguments!!.getString("latitude")


        }
        if (arguments?.getString("longitude") != null) {
            longitude = arguments!!.getString("longitude")
        }

        if (arguments?.getString("name") != null){
            name = arguments!!.getString("name")
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)






/*  READ MULTIPLE MONUMENTS

                 var getarrayOfMonumentlocationslatitud = arguments!!.getStringArray("arrayOfMonumentlocationslatitude")
                 Log.d("Lat : ->", getarrayOfMonumentlocationslatitud.toString())
                 var getarrayOfMonumentlocationslongitude =
                     arguments!!.getStringArray("arrayOfMonumentlocationslongitude")
                 Log.d("Lon : ->", getarrayOfMonumentlocationslongitude.toString())

*/

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)


        // Add a marker in Sydney and move the camera
        //val sydney = LatLng(-34.0, 151.0)
        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        //val mapFragment = supportFragmentManager
        //mMap.setOnMarkerClickListener(this)
        //mMap.uiSettings.isZoomControlsEnabled = true

        //setUpMap()
        //getMapAsync(this)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.findViewById<View>(R.id.map) as MapView
        mapView.onCreate(savedInstanceState)
        mapView.onResume()
        mapView.getMapAsync(this)//when you already implement OnMapReadyCallback in your fragment
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        if (arguments?.getString("arrayOfMonumentlocations") != null) {
            arrayOfMonumentlocations = arguments!!.getString("arrayOfMonumentlocations")
            Log.d("Location",arrayOfMonumentlocations )

            var json: String? = arrayOfMonumentlocations
            var jsonarr = JSONArray(json)

            for (i in 0..jsonarr!!.length() - 1) {
                 nam = jsonarr.getJSONObject(i).getString("name")
                 lat = jsonarr.getJSONObject(i).getString("lat")
                 lon = jsonarr.getJSONObject(i).getString("lon")
                val monument = LatLng(lat!!.toDouble(), lon!!.toDouble())
                mMap.addMarker(MarkerOptions().position(monument).title(nam))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(monument, 13f))
                Log.d(i.toString(),"Nombre: ${nam.toString()}, Lat: ${lat.toString()}, Lon: ${lon.toString()}" )
            }

        }


        // Add a marker in Sydney and move the camera
        if (latitude != null && longitude != null && name != null){
        val convent = LatLng(latitude!!.toDouble(), longitude!!.toDouble())
        //val church = LatLng(40.655782, -4.7058346)
        //val wall = LatLng(40.6576166, -4.7059678)
        mMap.addMarker(MarkerOptions().position(convent).title(name))}


        if (latitude != null && longitude != null) {
            val convent = LatLng(latitude!!.toDouble(), longitude!!.toDouble())
            //val church = LatLng(40.655782, -4.7058346)
            //val wall = LatLng(40.6576166, -4.7059678)
            mMap.addMarker(MarkerOptions().position(convent).title("Iglesia – Convento de Santa Teresa de Jesús"))

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(convent, 17f))

        }
        //mMap.addMarker(MarkerOptions().position(church).title("Iglesia de San Juan Bautista"))
        //mMap.addMarker(MarkerOptions().position(wall).title("La muralla de Ávila"))

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        mMap.setOnMarkerClickListener(this)
        mMap.uiSettings.isZoomControlsEnabled = false

        /*val latit = arguments?.get("latitude")
        val longit = arguments?.get("longitude")
        val pid = arguments?.get("pid")
        if (latit != null && longit!=null&&pid!=null){
        drawPoint(latit as String, longit as String, pid as String)}
        else{}*/

        setUpMap()
    }

    private fun placeMarker(location: LatLng) {

        val markerOption = MarkerOptions().position(location)

        mMap.addMarker(markerOption)
    }


    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                context!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        mMap.isMyLocationEnabled = false


        fusedLocationClient.lastLocation.addOnSuccessListener(activity!!) { location ->

            if (location != null) {
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                placeMarker(currentLatLong)
                if (latitude == null && longitude == null && lat == null && lon == null && nam == null)
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 13f))
            }
        }
    }

    fun setRecyclerViewRoutes() {
        recyclerRoutes.setHasFixedSize(true)
        recyclerRoutes.itemAnimator = DefaultItemAnimator()
        val horizontalLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerRoutes.setLayoutManager(horizontalLayoutManager)

        adapterRoutes = (RouteAdapter(listOfRoutes, object : RecyclerRouteListener {
            override fun onClick(route: Route, position: Int) {


                //activity?.toast("Let's go to ${route.name}!")

                val bundle = Bundle()
                bundle.putInt("id", route.id)
                bundle.putString("name", "${route.name}")
                bundle.putString("image", "${route.imgResource}")
                bundle.putString("description", "${route.description}")
                bundle.putString("duration", "${route.duration}")


                val nextFrag = RouteFragment()
                nextFrag.arguments = bundle
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, nextFrag, "5")
                    .addToBackStack(null)
                    .commit()

            }

            override fun onDelete(route: Route, position: Int) {
                listOfRoutes.remove(route)
                adapterRoutes.notifyItemRemoved(position)
                activity?.toast("${route.name} has been removed!")
            }
        }))
        recyclerRoutes.adapter = adapterRoutes
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
