package thesis_project.presentation.ui.start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.thesis_project.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import thesis_project.presentation.viewmodel.MyViewModel

class StartActivity : AppCompatActivity(), StartActivityControlInterface {

    lateinit var drawerLayout: DrawerLayout
    lateinit var myViewModel: MyViewModel
    lateinit var navController: NavController
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var materialToolbar: MaterialToolbar
    lateinit var navigationView: NavigationView
    lateinit var fragment: View
    var snackBar: Snackbar?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        drawerLayout = findViewById(R.id.drawerLayout)
        fragment = findViewById(R.id.nav_host_fragment_start)



        AppCompatDelegate.setDefaultNightMode(myViewModel.getTheme())

        materialToolbar = findViewById(R.id.materialToolBar)

        materialToolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        bottomNavigationView = findViewById(R.id.bottomNavigation)

        navigationView = findViewById(R.id.navigationView)

        navigationView.itemIconTintList = null

        val header = LayoutInflater.from(this).inflate(R.layout.layout_navigation_header, null)
        navigationView.addHeaderView(header)

        val textName = header.findViewById<TextView>(R.id.name_navigation_header)
        val textSurname = header.findViewById<TextView>(R.id.surname_navigation_header)

        val child = intent.extras?.getString("child") ?: "Error"
        myViewModel.userKey = child

        val firebase = FirebaseDatabase.getInstance().getReference("FreBaseUsers")
        firebase.child(child).get().addOnSuccessListener {
            val name = it.child("name").getValue(String::class.java) ?: "ErrorName"
            val surname = it.child("surname").getValue(String::class.java) ?: "ErrorSurname"
            val email = it.child("email").getValue(String::class.java) ?: "ErrorEmail"
            textName.text = name
            textSurname.text = surname
            myViewModel.name = name
            myViewModel.surname = surname
            myViewModel.email = email
        }.addOnFailureListener {

        }


        navController = Navigation.findNavController(this, R.id.nav_host_fragment_start)
        NavigationUI.setupWithNavController(navigationView, navController)


        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            materialToolbar.title = destination.label
        }

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.rateBottomMenu -> R.id.fragment_rate
                R.id.atmBottomMenu -> R.id.fragment_atm
                R.id.infoboxBottomMenu -> R.id.fragment_infoBox
                R.id.newsBottomMenu -> R.id.fragment_news
                else -> null
            }?.let { destinationId ->
                navController.graph.startDestination = destinationId
                navController.navigate(destinationId)
                true
            } ?: false
        }

        bottomNavigationView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
    }

    override fun onBackPressed() {
        super.onBackPressed()

        when {
            drawerLayout.isOpen -> {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            bottomNavigationView.isVisible -> {
                checkDrawerMenuBaseStart()
            }
            else -> {
                checkDrawerMenuBaseStartNext()
            }
        }
    }


    override fun setBottomNavigationVisible(isVisible: Boolean) {
        bottomNavigationView.isVisible = isVisible
    }

    fun checkDrawerMenuBaseStart() {
        if (snackBar?.isShown == true) {
            finish()
        } else {
            snackBar = Snackbar.make(
                window.decorView.rootView,
                "Еще раз для закрытия",
                Snackbar.LENGTH_SHORT
            )
            snackBar?.anchorView = findViewById(R.id.bottomNavigation)
            snackBar?.show()
        }
    }

    fun checkDrawerMenuBaseStartNext() {
        for (i in 0 until navigationView.menu.size) {
            navigationView.menu.getItem(i).isChecked = false
        }
        navController.popBackStack()
    }

}