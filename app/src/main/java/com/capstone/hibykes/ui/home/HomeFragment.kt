package com.capstone.hibykes.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.capstone.hibykes.R
import com.capstone.hibykes.data.local.StationEntity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class HomeFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var stations: List<StationEntity>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[HomeViewModel::class.java]
        stations = viewModel.getStations()

        val mapFragment = childFragmentManager.findFragmentById((R.id.map)) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val boundsBuilder = LatLngBounds.Builder()
        for (station in stations) {
            val latLng = LatLng(station.latitude, station.longitude)
            boundsBuilder.include(latLng)
            mMap.addMarker(MarkerOptions().position(latLng).title(station.name).snippet(station.description))
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 1000, 1000, 0 ))
    }

    private fun generateSampleData(): List<StationEntity> {
        val stations = ArrayList<StationEntity>()
        stations.add( StationEntity("Branner Hall", "Best dorm at Stanford", 37.426, -122.163))
        stations.add( StationEntity("Gates CS building", "Many long nights in this basement", 37.430, -122.173))
        stations.add( StationEntity("Pinkberry", "First date with my wife", 37.444, -122.170))
        stations.add( StationEntity("Althea", "Chicago upscale dining with an amazing view", 41.895, -87.625))
        stations.add(StationEntity("Citizen Eatery", "Bright cafe in Austin with a pink rabbit", 30.322, -97.739))
        stations.add(StationEntity("Kati Thai", "Authentic Portland Thai food, served with love", 45.505, -122.635))
        return stations
    }
}