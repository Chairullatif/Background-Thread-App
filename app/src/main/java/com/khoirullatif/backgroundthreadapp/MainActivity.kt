package com.khoirullatif.backgroundthreadapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import com.khoirullatif.backgroundthreadapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // untuk proses background yang menggunakan executor + handler (2)
        // executor => membuat thread baru. handler => update hasil ke ui
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        // PROSES YANG BERJALAN DI BACKGROUND THREAD (1)
//        binding.btnStart.setOnClickListener {
//            try {
//                for (i in 1..10) {
//                    Thread.sleep(500)
//                    val percentage = i*10
//                    if (percentage == 100) {
//                        binding.tvStatus.text = getString(R.string.task_completed)
//                    } else {
//                        binding.tvStatus.text = String.format(getString(R.string.compressing), percentage)
//                    }
//                }
//            } catch (e: InterruptedException) {
//                e.printStackTrace()
//            }
//        }


        // PROSES YANG BERJALAN DI THREAD LAIN (2)
//        binding.btnStart.setOnClickListener {
//            executor.execute {
//                try {
//                    for (i in 1..10) {
//                        Thread.sleep(1000)
//                        val percentage = i * 10
//                        //update view di ui
//                        handler.post {
//                            if (percentage == 100) {
//                                binding.tvStatus.text = getString(R.string.task_completed)
//                            } else {
//                                binding.tvStatus.text = String.format(getString(R.string.compressing), percentage)
//                            }
//                        }
//                    }
//                } catch (e: InterruptedException) {
//                    e.printStackTrace()
//                }
//            }
//        }

        //MENGGUNAKAN COROUTINE (tanpa executor dan handler)
        binding.btnStart.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Default) {
                try {
                    for (i in 1..10) {
                        Thread.sleep(1000)
                        var presentage = i * 10
                        //update view di ui
                        withContext(Dispatchers.Main){
                            if (presentage == 100) {
                                binding.tvStatus.text = getString(R.string.task_completed)
                                presentage = 0
                            } else {
                                binding.tvStatus.text = String.format(getString(R.string.compressing), presentage)
                            }
                        }
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }
}