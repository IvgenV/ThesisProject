package thesis_project.presentation.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.thesis_project.R
import com.google.android.material.switchmaterial.SwitchMaterial
import thesis_project.presentation.viewmodel.MyViewModel

class FragmentSetting : BaseFragment() {

    lateinit var switchSetting: SwitchMaterial

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switchSetting = view.findViewById(R.id.setting_switch)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        switchSetting.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                myViewModel.setTheme(
                    AppCompatDelegate.MODE_NIGHT_YES
                )
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )
            } else {
                myViewModel.setTheme(
                    AppCompatDelegate.MODE_NIGHT_NO
                )
                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )
            }
        }
        checkedSwitch()
    }

    fun checkedSwitch(){
        switchSetting.isChecked = myViewModel.getTheme()==AppCompatDelegate.MODE_NIGHT_YES
    }

}