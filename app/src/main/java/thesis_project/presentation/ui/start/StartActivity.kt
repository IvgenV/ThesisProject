package thesis_project.presentation.ui.start

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.thesis_project.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.FirebaseDatabase
import thesis_project.presentation.viewmodel.MyViewModel

class StartActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var myViewModel: MyViewModel
    lateinit var navController: NavController
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var materialToolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        drawerLayout = findViewById(R.id.drawerLayout)
        materialToolbar = findViewById(R.id.materialToolBar)

        materialToolbar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        bottomNavigationView = findViewById(R.id.bottomNavigation)

        val navigationView = findViewById<NavigationView>(R.id.navigationView)

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
                R.id.rateBottomMenu -> {
                    navController.navigate(R.id.fragment_rate)
                    true
                }
                R.id.atmBottomMenu -> {
                    navController.navigate(R.id.fragment_atm)
                    true
                }
                R.id.infoboxBottomMenu -> {
                    navController.navigate(R.id.fragment_infoBox)
                    true
                }
                R.id.newsBottomMenu -> {
                    navController.navigate(R.id.fragment_news)
                    true
                }
                else -> false
            }
        }
        bottomNavigationView.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_LABELED
    }

}