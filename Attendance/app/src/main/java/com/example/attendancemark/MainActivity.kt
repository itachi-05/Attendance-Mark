package com.example.attendancemark

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.attendancemark.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var tabLayout: TabLayout
    private lateinit var myViewPager: ViewPager2



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        init()
        val config: Configuration = resources.configuration
        if (config.smallestScreenWidthDp < 200) {
            binding.appName.textSize = 20F
        }
    }

    private fun init() {
        auth = FirebaseAuth.getInstance()
        tabLayout = findViewById(R.id.tabLayout)
        myViewPager = findViewById(R.id.MyViewPager)
        myViewPager.adapter = MyAdapterFG(this)
        TabLayoutMediator(tabLayout,myViewPager){ tab, index ->
            tab.text = when(index){
                0 -> { "Home" }
                1 -> { "Accounts" }
                else -> { throw Resources.NotFoundException("Position not found") }
            }
        }.attach()
//        tabLayout.setTabTextColors(Color.parseColor("#595757"),Color.parseColor("#000000"))
    }
}