package com.capstone.hibykes.ui.maps

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.hibykes.R
import com.capstone.hibykes.data.local.StationEntity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var stations: List<StationEntity>
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MapsViewModel::class.java]
        stations = viewModel.getStations()

        val mapFragment = childFragmentManager.findFragmentById((R.id.map)) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(context)
        fetchLocation()
    }

    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && context?.let {
                ActivityCompat.checkSelfPermission(it, Manifest.permission.ACCESS_COARSE_LOCATION)
            } != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), permissionCode)
            return
        }
        val task = fusedLocationProviderClient.lastLocation
        Log.d("maps", task.toString())
        task.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location
                Toast.makeText(context, currentLocation.latitude.toString() + " , " + currentLocation.longitude, Toast.LENGTH_SHORT).show()
                val supportMapFragment = (childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)!!
                supportMapFragment.getMapAsync(this)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        when (requestCode) {
            permissionCode -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
//        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)

        val boundsBuilder = LatLngBounds.Builder()
        for (station in stations) {
            val latLngStation = LatLng(station.latitude, station.longitude)
            boundsBuilder.include(latLngStation)
            mMap.addMarker(
                MarkerOptions().position(latLngStation).title(station.name).snippet(
                    station.description
                )
            )
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 1000, 1000, 0))
//        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f))
    }
}