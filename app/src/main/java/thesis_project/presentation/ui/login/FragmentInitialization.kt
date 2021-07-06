package thesis_project.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.thesis_project.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import thesis_project.UserFireBase
import thesis_project.presentation.ui.start.StartActivity

class FragmentInitialization : Fragment() {

    lateinit var buttonInit: Button
    lateinit var buttonCreateUser: Button
    lateinit var tvTextLogin: TextView
    lateinit var tvTextPassword: TextView
    lateinit var inputEmail: EditText
    lateinit var inputPassword: EditText
    lateinit var navigation: NavController
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        buttonInit.setOnClickListener {
            userLogin()
        }

        buttonCreateUser.setOnClickListener {
            createUser()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_initialization, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonInit = view.findViewById(R.id.buttonInit)
        buttonCreateUser = view.findViewById(R.id.buttonCreateUser)
        tvTextLogin = view.findViewById(R.id.tvTextLogin)
        tvTextPassword = view.findViewById(R.id.tvTextPassword)
        inputEmail = view.findViewById(R.id.editTextLogin)
        inputPassword = view.findViewById(R.id.editTextPassword)
        navigation = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
    }

    private fun createUser() {
        val email = inputEmail.text.toString().trim()
        val password = inputPassword.text.toString().trim()

        if (email.isEmpty()) {
            inputEmail.error = "Input email!"
            inputEmail.requestFocus()
            return
        }

        if (password.isEmpty()) {
            inputPassword.error = "Input password!"
            inputPassword.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.error = "Please provide email!"
            inputEmail.requestFocus()
            return
        }

        if (password.length < 6) {
            inputPassword.error = "Password should be at least 6 characters!!"
            inputPassword.requestFocus()
            return
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val user = UserFireBase(email, password)

                FirebaseAuth.getInstance().currentUser?.let { auth ->
                    FirebaseDatabase.getInstance().getReference("FreBaseUsers")
                        .child(auth.uid).setValue(user).addOnCompleteListener { dataBase ->
                            if (dataBase.isComplete) {
                                Toast.makeText(requireContext(), "User add", Toast.LENGTH_SHORT)
                                    .show()
                            } else Toast.makeText(
                                requireContext(),
                                "User dont add",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                }
            } else {
                Toast.makeText(requireContext(), "Failed to register", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun userLogin() {

        val email = inputEmail.text.toString().trim()
        val password = inputPassword.text.toString().trim()

        if (email.isEmpty()) {
            inputEmail.error = "Input email!"
            inputEmail.requestFocus()
            return
        }

        if (password.isEmpty()) {
            inputPassword.error = "Input password!"
            inputPassword.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.error = "Please enter email!"
            inputEmail.requestFocus()
            return
        }

        if (password.length < 6) {
            inputPassword.error = "Password should be at least 6 characters!!"
            inputPassword.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val bundle = Bundle()
                bundle.putString("email",email)
                startActivity(Intent(requireContext(), StartActivity::class.java),bundle)
            } else {
                Toast.makeText(requireContext(), "Failed to login! Check info!", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }
}