package thesis_project.presentation.ui.start

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import thesis_project.presentation.viewmodel.MyViewModel

abstract class BaseFragment : Fragment() {

    val startActivity: StartActivityControlInterface by lazy {
        this.requireActivity() as StartActivityControlInterface
    }
    open val bottomNavigationVisible = false
    lateinit var myViewModel: MyViewModel
    lateinit var navigation: NavController
    open val callback = object : OnBackPressedCallback(true ) {
        override fun handleOnBackPressed() {
            onBackPressed()
        }
    }

    open fun onBackPressed() {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        startActivity.setBottomNavigationVisible(bottomNavigationVisible)
        (startActivity as StartActivity).onBackPressedDispatcher.addCallback(this.viewLifecycleOwner,callback)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigation = Navigation.findNavController(view)
        myViewModel = ViewModelProvider(startActivity as StartActivity).get(MyViewModel::class.java)
    }

}