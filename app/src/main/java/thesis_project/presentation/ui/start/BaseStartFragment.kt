package thesis_project.presentation.ui.start

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

open class BaseStartFragment: Fragment() {

    lateinit var snackBar:Snackbar

    val callbackBackPressed = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            if(snackBar.isShown){
                requireActivity().finish()
            }else{
                ///вот так работает
                snackBar = Snackbar.make(
                    requireView(),
                    "Еще раз для закрытия",
                    Snackbar.LENGTH_SHORT
                )
                snackBar.show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ///сдесь вылетает с ошибкой
        ///Views added to a FragmentContainerView must be associated with a Fragment View is not associated with a Fragment
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