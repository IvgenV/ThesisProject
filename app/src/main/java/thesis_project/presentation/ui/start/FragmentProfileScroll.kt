package thesis_project.presentation.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.thesis_project.R
import thesis_project.presentation.viewmodel.ViewModel

class FragmentProfileScroll : Fragment() {
    private lateinit var viewModel: ViewModel

    private lateinit var buttonSaveSetting: Button
    private lateinit var buyRub: EditText
    private lateinit var saleRub: EditText
    private lateinit var buyUsd: EditText
    private lateinit var saleUsd: EditText
    private lateinit var buyUah: EditText
    private lateinit var saleUah: EditText
    private lateinit var buyEur: EditText
    private lateinit var saleEur: EditText


    private lateinit var switchNotification: Switch
    private lateinit var switchRub: Switch
    private lateinit var switchUsd: Switch
    private lateinit var switchUah: Switch
    private lateinit var switchEur: Switch

    private lateinit var key_switch: String
    private lateinit var key_switchRub: String
    private lateinit var key_switchUsd: String
    private lateinit var key_switchUah: String
    private lateinit var key_switchEur: String

    private lateinit var key_BuyRub: String
    private lateinit var key_BuyUsd: String
    private lateinit var key_BuyUah: String
    private lateinit var key_BuyEur: String
    private lateinit var key_SaleRub: String
    private lateinit var key_SaleUsd: String
    private lateinit var key_SaleUah: String
    private lateinit var key_SaleEur: String


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        key_switch = getString(R.string.key_switchNews)
        key_switchRub = getString(R.string.key_switchRub)
        key_switchUsd = getString(R.string.key_switchUsd)
        key_switchUah = getString(R.string.key_switchUah)
        key_switchEur = getString(R.string.key_switchEur)

        key_BuyRub = getString(R.string.key_buyRub)
        key_BuyUsd = getString(R.string.key_buyUsd)
        key_BuyUah = getString(R.string.key_buyUah)
        key_BuyEur = getString(R.string.key_buyEur)
        key_SaleRub = getString(R.string.key_saleRub)
        key_SaleUsd = getString(R.string.key_saleUsd)
        key_SaleUah = getString(R.string.key_saleUah)
        key_SaleEur = getString(R.string.key_saleEur)


        buyRub.setText(
            viewModel.takeRateSharedPreferences(key_BuyRub).toString(),
            TextView.BufferType.EDITABLE
        )
        buyUsd.setText(
            viewModel.takeRateSharedPreferences(key_BuyUsd).toString(),
            TextView.BufferType.EDITABLE
        )
        buyUah.setText(
            viewModel.takeRateSharedPreferences(key_BuyUah).toString(),
            TextView.BufferType.EDITABLE
        )
        buyEur.setText(
            viewModel.takeRateSharedPreferences(key_BuyEur).toString(),
            TextView.BufferType.EDITABLE
        )
        saleRub.setText(
            viewModel.takeRateSharedPreferences(key_SaleRub).toString(),
            TextView.BufferType.EDITABLE
        )
        saleUsd.setText(
            viewModel.takeRateSharedPreferences(key_SaleUsd).toString(),
            TextView.BufferType.EDITABLE
        )
        saleUah.setText(
            viewModel.takeRateSharedPreferences(key_SaleUah).toString(),
            TextView.BufferType.EDITABLE
        )
        saleEur.setText(
            viewModel.takeRateSharedPreferences(key_SaleEur).toString(),
            TextView.BufferType.EDITABLE
        )

        switchNotification.isChecked = viewModel.takeStatusSwitch(key_switch)
        switchRub.isChecked = viewModel.takeStatusSwitch(key_switchRub)
        switchUsd.isChecked = viewModel.takeStatusSwitch(key_switchUsd)
        switchUah.isChecked = viewModel.takeStatusSwitch(key_switchUah)
        switchEur.isChecked = viewModel.takeStatusSwitch(key_switchEur)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_scroll, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        switchRub = view.findViewById(R.id.switchRUB)
        switchUsd = view.findViewById(R.id.switchUSD)
        switchUah = view.findViewById(R.id.switchUAH)
        switchEur = view.findViewById(R.id.switchEUR)
        switchNotification = view.findViewById(R.id.switchNews)

        buyRub = view.findViewById(R.id.editTextNumberBuyRUB)
        saleRub = view.findViewById(R.id.editTextNumberSaleRUB)
        buyUsd = view.findViewById(R.id.editTextNumberBuyUSD)
        saleUsd = view.findViewById(R.id.editTextNumberSaleUSD)
        buyUah = view.findViewById(R.id.editTextNumberBuyUAH)
        saleUah = view.findViewById(R.id.editTextNumberSaleUAH)
        buyEur = view.findViewById(R.id.editTextNumberBuyEUR)
        saleEur = view.findViewById(R.id.editTextNumberSaleEUR)
        buttonSaveSetting = view.findViewById(R.id.btnSaveSettingRate)

        switchRub.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.addStatusSwitch(key_switchRub, isChecked)
        }
        switchUsd.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.addStatusSwitch(key_switchUsd, isChecked)
        }
        switchUah.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.addStatusSwitch(key_switchUah, isChecked)
        }
        switchEur.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.addStatusSwitch(key_switchEur, isChecked)
        }

        switchNotification.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked == true && !viewModel.takeStatusSwitch(key_switch)) {
                viewModel.startNotificationNews()
            } else if (isChecked == false && viewModel.takeStatusSwitch(key_switch)) {
                viewModel.stopNotificationNews()
            }
            viewModel.addStatusSwitch(key_switch, isChecked)
        }
        buttonSaveSetting.setOnClickListener {
            viewModel.addRateSharedPreferences(key_BuyRub, buyRub.text.toString().toDouble())
            viewModel.addRateSharedPreferences(key_SaleRub, saleRub.text.toString().toDouble())
            viewModel.addRateSharedPreferences(key_BuyUsd, buyUsd.text.toString().toDouble())
            viewModel.addRateSharedPreferences(key_SaleUsd, saleUsd.text.toString().toDouble())
            viewModel.addRateSharedPreferences(key_BuyUah, buyUah.text.toString().toDouble())
            viewModel.addRateSharedPreferences(key_SaleUah, saleUah.text.toString().toDouble())
            viewModel.addRateSharedPreferences(key_BuyEur, buyEur.text.toString().toDouble())
            viewModel.addRateSharedPreferences(key_SaleEur, saleEur.text.toString().toDouble())

            if ((switchRub.isChecked == true) || (switchUsd.isChecked == true) ||
                (switchUah.isChecked == true) || (switchEur.isChecked == true)
            ) {
                viewModel.startNotificationRate()
            } else {
                viewModel.stopNotificationRate()
            }
        }
    }

}