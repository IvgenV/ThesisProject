package thesis_project.location

import android.Manifest
import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class GpsLocation :  LocationListener {

    private lateinit var localListenerInterface:ILocationListener

    override fun onLocationChanged(location: Location) {
        localListenerInterface.onLocationChanged(location)
    }

    override fun onProviderEnabled(provider: String) {

    }

    override fun onProviderDisabled(provider: String) {

    }

    fun setLocalListenerInterface(locationListener: ILocationListener){
        this.localListenerInterface = locationListener
    }

}