package thesis_project.presentation.ui.start

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.thesis_project.R
import com.google.android.material.snackbar.Snackbar


open class BaseStartFragments: Fragment() {

    lateinit var snackBar:Snackbar
    lateinit var drawerLayout: DrawerLayout

    val callbackBackPressed = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            if (drawerLayout.isOpen) {
                drawerLayout.closeDrawer(GravityCompat.START)
            }else{
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
        snackBar = Snackbar.make(
            requireView(),
            "Еще раз для закрытия",
            Snackbar.LENGTH_SHORT
        )
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

    override fun onDestroy() {
        callbackBackPressed.isEnabled = false
        super.onDestroy()
    }

}