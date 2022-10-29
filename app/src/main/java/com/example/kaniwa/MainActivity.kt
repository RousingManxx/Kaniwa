package com.example.kaniwa

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.kaniwa.databinding.ActivityMainBinding
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.navigation.NavigationView
import java.util.*

class MainActivity : AppCompatActivity(){

    private val AUTOCOMPLETE_REQUEST_CODE = 1
    private lateinit var binding: ActivityMainBinding
    private lateinit var placesClient:PlacesClient
    private lateinit var drawerOpen:ImageView
    private lateinit var navigationDrawer:NavigationView
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //inicializa Places en caso de que no este inicializado
        if(!Places.isInitialized()){
            Places.initialize(this, getString(R.string.google_maps_key))
        }
        //Crea un cliente de Places
        placesClient = Places.createClient(this)
        setupAutocomplete()

        //setup
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")
        setup(email ?: "", provider ?: "")

        //GUARDADO DE DATOS
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()

        drawerOpen=findViewById(R.id.drawer_open)
        navigationDrawer=findViewById(R.id.navigation_drawer)
        drawerLayout=findViewById(R.id.drawer_layout)
        drawerOpen.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        val navController:NavController= Navigation.findNavController(this,R.id.fragment)
        NavigationUI.setupWithNavController(navigationDrawer,navController)
    }

    private fun setupAutocomplete(){
        //Tomar el fragment del layout para referenciarlo
        val autocompleteFragment = supportFragmentManager.findFragmentById(R.id.autocomplete_fragment) as AutocompleteSupportFragment?
        autocompleteFragment!!.setPlaceFields(listOf(Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG))
        val token = AutocompleteSessionToken.newInstance()
        //instanciar rectangulo del area de Xalapa
        val xalapa = RectangularBounds.newInstance(
            LatLng(19.487500, -96.972906),
            LatLng(19.599870, -96.839109)
        )
        //Se setea el area restringida de busqueda y el pais
        autocompleteFragment.setLocationRestriction(xalapa)
        autocompleteFragment.setCountries("MX")

        //Listener al elegir elemento del fragment
        autocompleteFragment.setOnPlaceSelectedListener(object: PlaceSelectionListener{
            override fun onPlaceSelected(place: Place){
                val latLong = "latitud ${place.latLng.latitude!!} longitud ${place.latLng?.longitude}"
                Toast.makeText(applicationContext,latLong,Toast.LENGTH_LONG).show()
                //val fragment = supportFragmentManager.findFragmentById(R.id.map) as MapFragment?
                //fragment!!.prueba("HOLA CARA DE BOLA")
            }
            override fun onError(status: Status) {
                Toast.makeText(applicationContext,"No se selecciono destino",Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setup(email: String, provider: String) {
        title = "Inicio"
        /*
        binding.closeButton.setOnClickListener {
            //Borrado de datos de sesion
            val prefs =
                getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            FirebaseAuth.getInstance().signOut()
            onBackPressed()
            */
    }
}
