package thesis_project.presentation.ui.start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.thesis_project.R
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView

class StartActivity : AppCompatActivity() {

    lateinit var drawerLayout:DrawerLayout
    lateinit var textTitle:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        drawerLayout = findViewById(R.id.drawerLayout)

        findViewById<ImageView>(R.id.imageMenu).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        navigationView.itemIconTintList = null

        val navController = Navigation.findNavController(this,R.id.nav_host_fragment_start)
        NavigationUI.setupWithNavController(navigationView,navController)

        textTitle = findViewById(R.id.textTitleBar)


        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            textTitle.text = destination.label

        }
    }

}