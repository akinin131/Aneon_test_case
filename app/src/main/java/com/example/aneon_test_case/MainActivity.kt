package com.example.aneon_test_case

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.aneon_test_case.data.datastore.DataStoreManager
import com.example.aneon_test_case.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        lifecycleScope.launch {
            DataStoreManager.readIsUserLoggedIn(this@MainActivity).collect { isLoggedIn ->
                val action =
                    if (isLoggedIn) R.id.action_start_to_mainFragment
                    else R.id.action_start_to_authorizationFragment

                delay(1000)

                navController.navigate(action)
            }
        }

    }
}
