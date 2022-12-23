package com.example.attendancemark

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.attendancemark.databinding.HomeFragmentBinding
import com.google.firebase.auth.FirebaseAuth


class Home: Fragment(R.layout.home_fragment) {
    private var binding: HomeFragmentBinding?= null
    private val bind get() = binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        handlingBackButton()

        val intent = Intent(context,ActivityScanOld::class.java)
        binding!!.entryBtn.setOnClickListener{
            intent.putExtra("in_or_out","entry")
            startActivity(intent)
        }
        binding!!.exitBtn.setOnClickListener{
            intent.putExtra("in_or_out","exit")
            startActivity(intent)
        }
        return binding!!.root
    }



    override fun onViewCreated(view: View , savedInstanceState: Bundle?) {
        super.onViewCreated(view , savedInstanceState)
    }

    // to avoid memory leaks
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun handlingBackButton() {
            requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_HOME)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
        })
    }
}