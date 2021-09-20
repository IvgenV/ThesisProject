package thesis_project.presentation.ui.start

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    val startActivity: StartActivityControlInterface by lazy {
        this.requireActivity() as StartActivityControlInterface
    }
    fun onBackPressed() {

    }

}