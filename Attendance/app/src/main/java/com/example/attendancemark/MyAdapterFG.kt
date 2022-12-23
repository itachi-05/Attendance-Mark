package com.example.attendancemark


import android.content.res.Resources
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

@RequiresApi(Build.VERSION_CODES.O)
class MyAdapterFG(fragmentActivity: MainActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> { Home() }
            1 -> { Accounts() }
            else -> {
                throw Resources.NotFoundException("Position not found")
            }
        }
    }
}
