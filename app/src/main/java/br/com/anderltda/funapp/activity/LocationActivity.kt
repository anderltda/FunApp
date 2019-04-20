package br.com.anderltda.funapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.anderltda.funapp.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_location.*

class LocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private lateinit var root: ViewGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        val title = toolbar.findViewById(R.id.tv_title) as TextView

        title.text = resources.getString(R.string.title_location)

        val back = toolbar.findViewById(R.id.tv_back) as TextView
        back.visibility = View.VISIBLE
        back.setOnClickListener{
            finish()
        }

        mapFragment.getMapAsync(this)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            mapFragment.getMapAsync { googleMap ->
                googleMap.mapType = GoogleMap.MAP_TYPE_SATELLITE

                googleMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(37.4233438, -122.0728817))
                        .title("LinkedIn")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                )

                googleMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(37.4629101, -122.2449094))
                        .title("Facebook")
                        .snippet("Facebook HQ: Menlo Park")
                )

                googleMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(37.3092293, -122.1136845))
                        .title("Apple")
                )

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.4233438, -122.0728817), 10f))
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(37.4233438, -122.0728817))
                .title("LinkedIn")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        )

        googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(37.4629101, -122.2449094))
                .title("Facebook")
                .snippet("Facebook HQ: Menlo Park")
        )

        googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(37.3092293, -122.1136845))
                .title("Apple")
        )

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(37.4233438, -122.0728817), 10f))

    }
}
