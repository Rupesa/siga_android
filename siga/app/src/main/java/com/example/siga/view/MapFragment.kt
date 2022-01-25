package com.example.siga.view

import android.content.Context
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.siga.R
import com.example.siga.viewmodel.AppViewModel
import com.example.siga.viewmodel.Event
import com.example.siga.viewmodel.InjectorUtils
import com.example.siga.viewmodel.ViewModelFactory

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback{

    private lateinit var ctx: Context
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_map, container, false)

        var ab = (activity as AppCompatActivity).supportActionBar

        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(false);
            ab.setHomeButtonEnabled(false);
        }

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }


    override fun onMapReady(googleMap: GoogleMap) {
        val factory = InjectorUtils.provideViewModelFactory(ctx)
        val viewModel: AppViewModel by viewModels { factory }

        viewModel.fetchRemoteEvents()
        Log.w("Call", "Call back")

        viewModel.remoteEvents().observe(this, Observer { events ->
            Log.w("Loc", "Call back")
            events.forEach {
                Log.w("Loc", it.location!!)
                val location = LatLng(it.lat!!, it.lng!!)
                googleMap.addMarker(MarkerOptions().position(location).title(it.description))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13f))
            }
        })
    }
}

