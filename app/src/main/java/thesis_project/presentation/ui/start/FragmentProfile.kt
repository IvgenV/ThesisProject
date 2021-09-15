package thesis_project.presentation.ui.start

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.thesis_project.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import thesis_project.presentation.viewmodel.MyViewModel

class FragmentProfile : Fragment() {

    lateinit var name: TextView
    lateinit var surname: TextView
    lateinit var email: TextView
    lateinit var myViewModel: MyViewModel
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView:NavigationView
    val callbackBackPressed = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            if (drawerLayout.isOpen) {
                ///так не сбрасывает чек
                ////navigationView.checkedItem?.isChecked = false
                navigationView.menu.getItem(0).isChecked = false
                drawerLayout.closeDrawer(GravityCompat.START)
                requireActivity().supportFragmentManager.popBackStack()
            } else {
                navigationView.menu.getItem(0).isChecked = false
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        myViewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        name.text = myViewModel.name
        surname.text = myViewModel.surname
        email.text = myViewModel.email
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            callbackBackPressed
        )
    }

    override fun onResume() {
        super.onResume()
        callbackBackPressed.isEnabled = true
    }

    override fun onPause() {
        bottomNavigationView.visibility = View.VISIBLE
        callbackBackPressed.isEnabled = false
        super.onPause()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        name = view.findViewById(R.id.name_fragment_profile)
        surname = view.findViewById(R.id.surname_fragment_profile)
        email = view.findViewById(R.id.email_fragment_prohile)
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigation)
        bottomNavigationView.visibility = View.GONE
        drawerLayout = requireActivity().findViewById(R.id.drawerLayout)
        navigationView = requireActivity().findViewById(R.id.navigationView)
    }

}