package com.khoirullatif.backgroundthreadapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khoirullatif.backgroundthreadapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            try {
                for (i in 1..10) {
                    Thread.sleep(500)
                    val percentage = i*10
                    if (percentage == 100) {
                        binding.tvStatus.text = getString(R.string.task_completed)
                    } else {
                        binding.tvStatus.text = String.format(getString(R.string.compressing), percentage)
                    }
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}