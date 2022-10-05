package com.example.moviestv.presentation.main

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.moviestv.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Loads [MainFragment].
 */
@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_browse_fragment, MainFragment())
                .commitNow()
        }
    }
}