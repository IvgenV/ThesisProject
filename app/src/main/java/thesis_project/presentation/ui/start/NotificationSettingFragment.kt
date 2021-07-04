package thesis_project.presentation.ui.start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.thesis_project.R

import thesis_project.presentation.viewmodel.ViewModel

class NotificationSettingFragment : Fragment() {
    lateinit var viewModel: ViewModel
    lateinit var buyRub: EditText
    lateinit var saleRub: EditText
    lateinit var buyUsd: EditText
    lateinit var saleUsd: EditText
    lateinit var buyUah: EditText
    lateinit var saleUah: EditText
    lateinit var buyEur: EditText
    lateinit var saleEur: EditText

    lateinit var switchRub: Switch
    lateinit var switchUsd: Switch
    lateinit var switchUah: Switch
    lateinit var switchEur: Switch

    val key_switchRub = "RUB_SWITCHNEWS"
    val key_switchUsd = "USD_SWITCHNEWS"
    val key_switchUah = "UAH_SWITCHNEWS"
    val key_switchEur = "EUR_SWITCHNEWS"

    val key_BuyRub = "RUB_BUY"
    val key_BuyUsd = "USD_BUY"
    val key_BuyUah = "UAH_BUY"
    val key_BuyEur = "EUR_BUY"
    val key_SaleRub = "RUB_SALE"
    val key_SaleUsd = "USD_SALE"
    val key_SaleUah = "UAH_SALE"
    val key_SaleEur = "EUR_SALE"


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

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
        return inflater.inflate(R.layout.notification_setting, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        switchRub = view.findViewById(R.id.switchRUB)
        switchUsd = view.findViewById(R.id.switchUSD)
        switchUah = view.findViewById(R.id.switchUAH)
        switchEur = view.findViewById(R.id.switchEUR)

        buyRub = view.findViewById(R.id.editTextNumberBuyRUB)
        saleRub = view.findViewById(R.id.editTextNumberSaleRUB)
        buyUsd = view.findViewById(R.id.editTextNumberBuyUSD)
        saleUsd = view.findViewById(R.id.editTextNumberSaleUSD)
        buyUah = view.findViewById(R.id.editTextNumberBuyUAH)
        saleUah = view.findViewById(R.id.editTextNumberSaleUAH)
        buyEur = view.findViewById(R.id.editTextNumberBuyEUR)
        saleEur = view.findViewById(R.id.editTextNumberSaleEUR)

        switchRub.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.addStatusSwitch(key_switchRub, isChecked)
            viewModel.addRateSharedPreferences(key_BuyRub, buyRub.text.toString().toDouble())
            viewModel.addRateSharedPreferences(key_SaleRub, saleRub.text.toString().toDouble())
        }
        switchUsd.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.addStatusSwitch(key_switchUsd, isChecked)
            viewModel.addRateSharedPreferences(key_BuyUsd, buyUsd.text.toString().toDouble())
            viewModel.addRateSharedPreferences(key_SaleUsd, saleUsd.text.toString().toDouble())
        }
        switchUah.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.addStatusSwitch(key_switchUah, isChecked)
            viewModel.addRateSharedPreferences(key_BuyUah, buyUah.text.toString().toDouble())
            viewModel.addRateSharedPreferences(key_SaleUah, saleUah.text.toString().toDouble())
        }
        switchEur.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.addStatusSwitch(key_switchEur, isChecked)
            viewModel.addRateSharedPreferences(key_BuyEur, buyEur.text.toString().toDouble())
            viewModel.addRateSharedPreferences(key_SaleEur, saleUah.text.toString().toDouble())
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}

