package thesis_project.presentation.ui.start

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

open class BaseStartDrawerLayoutFragments : Fragment() {

    var startActivity: StartActivityControlInterface? = null
    val callbackBackPressed = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            if(startActivity?.checkDrawerMenu() == true){
                startActivity?.isBottomNavigationVisible(true)
            }
        }
    }

    override fun onResume() {
        callbackBackPressed.isEnabled = true
        super.onResume()
    }

    override fun onPause() {
        callbackBackPressed.isEnabled = false
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        startActivity = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            callbackBackPressed
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        startActivity = requireActivity() as StartActivityControlInterface
        startActivity?.isBottomNavigationVisible(false)
    }

}