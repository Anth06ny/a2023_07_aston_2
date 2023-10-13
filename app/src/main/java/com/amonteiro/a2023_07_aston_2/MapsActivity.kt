package com.amonteiro.a2023_07_aston_2

import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amonteiro.a2023_07_aston_2.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Locale
import java.util.Timer
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.thread


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var mMap: GoogleMap? = null
    private lateinit var binding: ActivityMapsBinding
    val model by lazy { ViewModelProvider(this)[MapsViewModel::class.java] }

    var timer : Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        observe()

    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }


    fun observe() {
        model.liveData.observe(this) {
            mMap?.clear()
            if (it != null) {
                var markerISS = MarkerOptions().apply {
                    position(it)
                    title("ISS")
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                }
                //On ajoute le marker
                mMap?.addMarker(markerISS)
                mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 6f));
            }
        }

        model.cityNameLiveData.observe(this) {
            binding.textView.text = it
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

        timer = fixedRateTimer( period=1000){
            model.loadData(this@MapsActivity)
        }
    }


}

class MapsViewModel : ViewModel() {
    val liveData = MutableLiveData<LatLng?>(null)
    val cityNameLiveData = MutableLiveData("-")
    val errorLiveData = MutableLiveData("")

    fun loadData(context: Context) {

        thread {
            try {
                val position = RequestUtils.getISSPosition()
                liveData.postValue(position)

                val geocoder = Geocoder(context, Locale.getDefault())
                val list = geocoder.getFromLocation(position.latitude, position.longitude, 1)
                val adress = list?.getOrNull(0)
                cityNameLiveData.postValue("${adress?.countryName ?: "-"} ${adress?.locality ?: "-"}")

            }
            catch (e: Exception) {
                e.printStackTrace()
                errorLiveData.postValue("Une erreur est survenue : " + e.message)
                cityNameLiveData.postValue("- -")
            }
        }
    }
}