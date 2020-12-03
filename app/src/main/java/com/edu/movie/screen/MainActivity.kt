package com.edu.movie.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.edu.movie.R
import com.edu.movie.screen.base.HomePageFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .add(R.id.container, HomePageFragment.newInstance()).commit()
    }
}
