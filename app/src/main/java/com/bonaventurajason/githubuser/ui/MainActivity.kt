package com.bonaventurajason.githubuser.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.bonaventurajason.githubuser.R
import com.bonaventurajason.githubuser.data.repository.UserRepository
import com.bonaventurajason.githubuser.ui.viewmodel.UserViewModel
import com.bonaventurajason.githubuser.ui.viewmodel.UserViewModelProviderFactory
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Githubuserandroid)
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