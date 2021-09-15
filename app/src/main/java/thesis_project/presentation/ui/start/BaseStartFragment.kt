package thesis_project.presentation.ui.start

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.thesis_project.R
import com.google.android.material.snackbar.Snackbar


open class BaseStartFragment: Fragment() {

    lateinit var snackBar:Snackbar

    val callbackBackPressed = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            if(snackBar.isShown){
                requireActivity().finish()
            }else{
                snackBar = Snackbar.make(
                    requireView(),
                    "Еще раз для закрытия",
                    Snackbar.LENGTH_SHORT
                )
                snackBar.anchorView = requireActivity().findViewById(R.id.bottomNavigation)
                snackBar.show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ///без этой инициализации snackbar все равно падает
        snackBar = Snackbar.make(
            requireView(),
            "Еще раз для закрытия",
            Snackbar.LENGTH_SHORT
        )
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