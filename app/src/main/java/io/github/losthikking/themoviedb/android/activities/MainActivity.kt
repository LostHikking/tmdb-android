package io.github.losthikking.themoviedb.android.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import dagger.hilt.android.AndroidEntryPoint
import io.github.losthikking.themoviedb.R
import io.github.losthikking.themoviedb.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
}
