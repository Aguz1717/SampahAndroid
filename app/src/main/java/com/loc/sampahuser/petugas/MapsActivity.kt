package com.loc.sampahuser.petugas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.loc.sampahuser.R

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var googleMap: GoogleMap?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }


    override fun onMapReady(p0: GoogleMap) {
        googleMap=p0

        //Adding markers to map

        val lat = intent.getDoubleExtra("latitude",0.0)
        val lang = intent.getDoubleExtra("langitude",0.0)
        val marker = intent.getStringExtra("lokasi")

        val latLng= LatLng(lat,lang)
        val markerOptions: MarkerOptions = MarkerOptions().position(latLng).title(marker)

        // moving camera and zoom map

        val zoomLevel = 15.0f //This goes up to 21


        googleMap.let {
            it!!.addMarker(markerOptions)
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel))
        }
    }
}