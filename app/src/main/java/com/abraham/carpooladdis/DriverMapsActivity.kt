package com.abraham.carpooladdis

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DriverMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        checkPermission()
    }

var ACCESSLOCATION = 123
    fun checkPermission(){
        if (Build.VERSION.SDK_INT>=23){

            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
                    !=PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),ACCESSLOCATION)
                return
            }

        }

        getUserLocation()
    }

     @SuppressLint("MissingPermission")
     fun getUserLocation() {

         Toast.makeText(this,"we can access your current location",Toast.LENGTH_SHORT).show()

         var myLocation = MyLocationListener()

         var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

         locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3,3f,myLocation)
         var myThread = myThread()
         myThread.start()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when (requestCode){
            ACCESSLOCATION->{
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    getUserLocation()

                }else
                    Toast.makeText(this,"We can not access your current location",Toast.LENGTH_SHORT).show()

            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


    }

    var location: Location? = null

    inner class MyLocationListener: LocationListener {

        constructor() {
            location = Location("Start")
            location!!.longitude=0.0
            location!!.latitude=0.0
        }
        override fun onLocationChanged(p0: Location?) {
            location=p0

        }
        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
           // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderEnabled(p0: String?) {
           // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onProviderDisabled(p0: String?) {
           // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }





    }

    inner class myThread: Thread {

        constructor() : super() {

        }

        override fun run() {
            while (true) {
                try {
                    runOnUiThread {

                        mMap!!.clear()
                        val currentLocation = LatLng(location!!.latitude, location!!.longitude)
                        mMap!!.addMarker(MarkerOptions().position(currentLocation)
                                .title("Me")
                                .snippet("This is my location")
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.car_map_marker)))

                        mMap!!.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
                        mMap!!.moveCamera(CameraUpdateFactory.zoomTo(12f))

                    }

                    Thread.sleep(1000)


                } catch (ex: Exception) {
                }
            }
        }

    }
}
