package thesis_project.presentation.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.thesis_project.R
import thesis_project.presentation.viewmodel.MyViewModel

class FragmentProfile : BaseFragment() {

    lateinit var name: TextView
    lateinit var surname: TextView
    lateinit var email: TextView

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
        name.text = myViewModel.name
        surname.text = myViewModel.surname
        email.text = myViewModel.email
    }

}