package com.paavam.swiggyapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import com.paavam.swiggyapp.R
import com.paavam.swiggyapp.ui.screens.SwiggyMain
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalFoundationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwiggyMain()
        }
    }

    /**
     * Activity Entry transition - Right --> Left
     */
    override fun startActivity(intent: Intent?) {
        super.startActivity(intent)
        addPendingAnimation()
    }

    override fun startActivity(intent: Intent?, options: Bundle?) {
        super.startActivity(intent, options)
        addPendingAnimation()
    }

    private fun addPendingAnimation() {
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_none)
    }
}