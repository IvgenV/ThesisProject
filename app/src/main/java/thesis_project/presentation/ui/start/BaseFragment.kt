package thesis_project.presentation.ui.start

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import thesis_project.presentation.viewmodel.MyViewModel

abstract class BaseFragment : Fragment(), LifecycleObserver {

    val startActivity: StartActivityControlInterface by lazy {
        activity as StartActivityControlInterface
    }
    open val bottomNavigationVisible = false
    lateinit var myViewModel: MyViewModel
    lateinit var navigation: NavController
    open val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            onBackPressed()
        }
    }

    open fun onBackPressed() {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        lifecycle.addObserver(this)
    }

    override fun onDetach() {
        super.onDetach()
        lifecycle.addObserver(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myViewModel = ViewModelProvider(startActivity as StartActivity)[MyViewModel::class.java]
    }

    override fun onStart() {
        startActivity.setBottomNavigationVisible(bottomNavigationVisible)
        (startActivity as StartActivity).onBackPressedDispatcher.addCallback(
            this.viewLifecycleOwner,
            callback
        )
        view?.let { navigation = Navigation.findNavController(it) }
        super.onStart()
    }

}