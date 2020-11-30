package com.bonaventurajason.consumerapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.bonaventurajason.consumerapp.R
import com.bonaventurajason.consumerapp.data.repository.UserRepository
import com.bonaventurajason.consumerapp.ui.viewmodel.UserViewModel
import com.bonaventurajason.consumerapp.ui.viewmodel.UserViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        val userRepository = UserRepository()
        val viewModelProviderFactory = UserViewModelProviderFactory(application, userRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(UserViewModel::class.java)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        setupActionBarWithNavController(navHostFragment.navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp()
                || super.onSupportNavigateUp()
    }
}