package thesis_project.presentation.ui.start

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.thesis_project.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar


open class BaseStartFragment: Fragment() {

    lateinit var snackBar:Snackbar
    lateinit var drawerLayout: DrawerLayout

    val callbackBackPressed = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            if (drawerLayout.isOpen) {
                drawerLayout.closeDrawer(GravityCompat.START)
            }else{
                snackBar = Snackbar.make(
                    requireView(),
                    "Еще раз для закрытия",
                    Snackbar.LENGTH_SHORT
                )
                if(snackBar.isShown){
                    requireActivity().finish()
                }else{
                    snackBar.anchorView = requireActivity().findViewById(R.id.bottomNavigation)
                    snackBar.show()
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        drawerLayout = requireActivity().findViewById(R.id.drawerLayout)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callbackBackPressed)
    }

    override fun onResume() {
        super.onResume()
        callbackBackPressed.isEnabled = true
    }

    override fun onPause() {
        super.onPause()
        callbackBackPressed.isEnabled = false
    }

}