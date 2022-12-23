package com.example.attendancemark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.attendancemark.databinding.ActivityScanBinding

class ActivityScanOld : AppCompatActivity() {
    private lateinit var binding: ActivityScanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}