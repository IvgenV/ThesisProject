package thesis_project.presentation.ui.start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.thesis_project.R

class StartActivity : AppCompatActivity() {

    lateinit var drawerLayout:DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        drawerLayout = findViewById(R.id.drawerLayout)

        findViewById<ImageView>(R.id.imageMenu).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

    }

}