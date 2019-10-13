package com.revolut.androidexam

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.revolut.rates.ui.RatesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.container_main, RatesFragment.newInstance(),
                RatesFragment.FRAGMENT_TAG
            )
            .disallowAddToBackStack()
            .commit()

    }
}