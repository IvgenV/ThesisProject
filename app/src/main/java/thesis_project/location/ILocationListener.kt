package thesis_project.location

import android.location.Location

interface ILocationListener {

    fun onLocationChanged(location:Location)

}