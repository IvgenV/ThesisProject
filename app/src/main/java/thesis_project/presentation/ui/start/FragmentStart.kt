package thesis_project.presentation.ui.start

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.thesis_project.R
import thesis_project.presentation.viewmodel.ViewModel

class FragmentStart : Fragment() {

    lateinit var toRate: TextView
    lateinit var toAtm: TextView
    lateinit var toInfoBox: TextView
    lateinit var toNews: TextView
    lateinit var navigation: NavController
    lateinit var viewmodel: ViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewmodel = ViewModelProvider(this).get(ViewModel::class.java)


        toRate.setOnClickListener {
            navigation.navigate(R.id.fragment_rate)
        }

        toAtm.setOnClickListener {
            navigation.navigate(R.id.fragment_atm)
        }

        toInfoBox.setOnClickListener {
            navigation.navigate(R.id.fragment_infoBox)
        }
        toNews.setOnClickListener {
            navigation.navigate(R.id.news_fragment)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toRate = view.findViewById(R.id.filials_start_fragment)
        toAtm = view.findViewById(R.id.atm_start_fragment)
        toInfoBox = view.findViewById(R.id.infoBoxes_start_fragment)
        toNews = view.findViewById(R.id.news_start_fragment)
        navigation = Navigation.findNavController(view)
    }

}