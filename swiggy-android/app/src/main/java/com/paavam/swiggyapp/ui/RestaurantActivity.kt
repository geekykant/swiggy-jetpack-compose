package com.paavam.swiggyapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.paavam.swiggyapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            RestaurantMain()
        }
    }

    override fun finish() {
        super.finish()
        addPendingAnimation()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        addPendingAnimation()
    }

    private fun addPendingAnimation() {
        overridePendingTransition(R.anim.exit_none, R.anim.exit_to_left)
    }
}