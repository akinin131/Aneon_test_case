package com.example.aneon_test_case.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.aneon_test_case.R
import com.example.aneon_test_case.databinding.ActivityMainBinding
import com.example.aneon_test_case.utils.OnBackPressedListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
    }

    override fun onBackPressed() {

        val currentFragment = getCurrentFragment()

        if (currentFragment is OnBackPressedListener) {

            (currentFragment as OnBackPressedListener).onBackPressed()
        } else {

            super.onBackPressed()
        }
    }

    private fun getCurrentFragment(): Fragment? {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        return navHostFragment.childFragmentManager.fragments.firstOrNull()
    }
}


