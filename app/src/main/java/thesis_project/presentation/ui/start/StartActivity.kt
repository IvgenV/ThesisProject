package thesis_project.presentation.ui.start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
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
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.FirebaseDatabase
import thesis_project.presentation.viewmodel.ViewModel

class StartActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var textTitle: TextView
    lateinit var viewModel: ViewModel
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        viewModel = ViewModelProvider(this).get(ViewModel::class.java)

        drawerLayout = findViewById(R.id.drawerLayout)

        findViewById<ImageView>(R.id.imageMenu).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val navigationView = findViewById<NavigationView>(R.id.navigationView)

        navigationView.itemIconTintList = null

        val header = LayoutInflater.from(this).inflate(R.layout.layout_navigation_header, null)
        navigationView.addHeaderView(header)

        val textName = header.findViewById<TextView>(R.id.name_navigation_header)
        val textSurname = header.findViewById<TextView>(R.id.surname_navigation_header)

        val child = intent.extras?.getString("child") ?: "Error"


        val firebase = FirebaseDatabase.getInstance().getReference("FreBaseUsers")
        firebase.child(child).get().addOnSuccessListener {
            val name = it.child("name").getValue(String::class.java) ?: "ErrorName"
            val surname = it.child("surname").getValue(String::class.java) ?: "ErrorSurname"
            val email = it.child("email").getValue(String::class.java) ?: "ErrorEmail"
            textName.text = name
            textSurname.text = surname
            viewModel.name = name
            viewModel.surname = surname
            viewModel.email = email
        }.addOnFailureListener {
        }



        navController = Navigation.findNavController(this, R.id.nav_host_fragment_start)
        NavigationUI.setupWithNavController(navigationView, navController)



        textTitle = findViewById(R.id.textTitleBar)


        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            textTitle.text = destination.label
        }

    }


}