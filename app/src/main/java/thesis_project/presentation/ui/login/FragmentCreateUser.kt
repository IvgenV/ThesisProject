package thesis_project.presentation.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.thesis_project.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import thesis_project.UserFireBase
import thesis_project.presentation.ui.start.StartActivity

class FragmentCreateUser : Fragment() {

    lateinit var emailInput: TextInputEditText
    lateinit var passwordInput: TextInputEditText
    lateinit var nameInput: TextInputEditText
    lateinit var surnameInput: TextInputEditText
    lateinit var buttonCreateUser: Button
    private lateinit var auth: FirebaseAuth

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        buttonCreateUser.setOnClickListener {
            createUser()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emailInput = view.findViewById(R.id.textInputEmail)
        passwordInput = view.findViewById(R.id.textInputPassword)
        nameInput = view.findViewById(R.id.textInputName)
        surnameInput = view.findViewById(R.id.textInputSurname)
        buttonCreateUser = view.findViewById(R.id.buttonCreateUser)
        auth = FirebaseAuth.getInstance()
    }

    private fun createUser() {
        val email = emailInput.text.toString().trim()
        val password = passwordInput.text.toString().trim()
        val name = nameInput.text.toString().trim()
        val surname = surnameInput.text.toString().trim()

        if (email.isEmpty()) {
            emailInput.error = "Input email!"
            emailInput.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.error = "Please enter email!"
            emailInput.requestFocus()
            return
        }

        if (password.isEmpty()) {
            passwordInput.error = "Input password!"
            passwordInput.requestFocus()
            return
        }

        if (password.length < 6) {
            passwordInput.error = "Password should be at least 6 characters!!"
            passwordInput.requestFocus()
            return
        }

        if (name.isEmpty()) {
            nameInput.error = "Input name!"
            nameInput.requestFocus()
            return
        }

        if (surname.isEmpty()) {
            surnameInput.error = "Input surname!"
            surnameInput.requestFocus()
            return
        }


        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val user = UserFireBase(email, password,name,surname)
                val child = email.substring(0,email.indexOf('@'))

                FirebaseAuth.getInstance().currentUser?.let { auth ->
                    FirebaseDatabase.getInstance().getReference("FreBaseUsers")
                        .child(child).setValue(user).addOnCompleteListener { dataBase ->
                            if (dataBase.isComplete) {
                                Toast.makeText(requireContext(), "User add", Toast.LENGTH_SHORT)
                                    .show()
                                val intent = Intent(requireContext(), StartActivity::class.java)
                                intent.putExtra("child",child)
                                startActivity(intent)
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

}
