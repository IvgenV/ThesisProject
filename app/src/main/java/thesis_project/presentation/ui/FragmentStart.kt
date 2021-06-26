package thesis_project.presentation.ui

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.thesis_project.R
import thesis_project.presentation.viewmodel.ViewModel

class FragmentStart : Fragment() {

    lateinit var text1: TextView
    lateinit var text2: TextView
    lateinit var text3: TextView
    lateinit var button: Button
    lateinit var navigation: NavController
    lateinit var viewmodel: ViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment1, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewmodel = ViewModelProvider(this).get(ViewModel::class.java)

        text1.setOnClickListener {
            navigation.navigate(R.id.fragment2)
        }


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text1 = view.findViewById(R.id.firstitem)
        text2 = view.findViewById(R.id.seconditem)
        text3 = view.findViewById(R.id.thirditem)
        button = view.findViewById(R.id.button)
        navigation = Navigation.findNavController(view)
    }

}