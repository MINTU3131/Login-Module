package com.mintusharma.loginapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.mintusharma.loginapp.databinding.ActivityMainBinding
import com.mintusharma.loginapp.repositories.LoginRepository
import com.mintusharma.loginapp.viewmodels.LoginViewModelFactory
import com.mintusharma.loginapp.viewmodels.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginRepository = LoginRepository(FirebaseAuth.getInstance())
        viewModel = ViewModelProvider(this, LoginViewModelFactory(loginRepository))
            .get(LoginViewModel::class.java)

        initView();
        observeLoginResult()


    }

    private fun observeLoginResult() {
        viewModel.loginResult.observe(this, Observer { success ->
            if (success) {
                binding.progressbar.visibility = View.GONE
                Toast.makeText(this,"Login Successful",Toast.LENGTH_LONG).show()
                startActivity(Intent(this, DashBoardActivity::class.java))
            } else {
                binding.progressbar.visibility = View.GONE
                Toast.makeText(this,"Login failed. Please check your credentials.",Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initView() {

        binding.submit.setOnClickListener {

            val email = binding.email.text.toString()
            val password = binding.password.text.toString()

            if (TextUtils.isEmpty(email)) {
                binding.email.error = "Please enter Email"
            }else if (TextUtils.isEmpty(password)) {
                binding.password.error = "Please enter Password"
            }else{
                binding.progressbar.visibility = View.VISIBLE
                viewModel.login(email, password)
            }
        }

    }
}