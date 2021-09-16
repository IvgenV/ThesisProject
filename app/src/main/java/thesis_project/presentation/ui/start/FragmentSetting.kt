package thesis_project.presentation.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.thesis_project.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.switchmaterial.SwitchMaterial

class FragmentSetting : Fragment() {

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var switchSetting:SwitchMaterial
    lateinit var textSetting:TextView

    val callbackBackPressed = object : OnBackPressedCallback(false) {
        override fun handleOnBackPressed() {
            if (drawerLayout.isOpen) {
                drawerLayout.closeDrawer(GravityCompat.START)
            }else{
                navigationView.menu.getItem(1).isChecked = false
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        bottomNavigationView = requireActivity().findViewById(R.id.bottomNavigation)
        bottomNavigationView.isVisible = false
        drawerLayout = requireActivity().findViewById(R.id.drawerLayout)
        navigationView = requireActivity().findViewById(R.id.navigationView)
        switchSetting.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            callbackBackPressed
        )
        callbackBackPressed.isEnabled = true
    }

    override fun onPause() {
        callbackBackPressed.isEnabled = false
        bottomNavigationView.isVisible = true
        super.onPause()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textSetting = view.findViewById(R.id.setting_text)
        switchSetting = view.findViewById(R.id.setting_switch)
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            requireActivity().setTheme(R.style.Theme_Thesis_Project_Dark)
            switchSetting.isChecked = true
        } else {
            switchSetting.isChecked = false
            requireActivity().setTheme(R.style.Theme_Thesis_Project)
        }
    }

}