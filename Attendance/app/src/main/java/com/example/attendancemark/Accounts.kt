package com.example.attendancemark

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.attendancemark.databinding.AccountsFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Accounts: Fragment(R.layout.accounts_fragment) {
    private var binding: AccountsFragmentBinding?= null
    private val bind get() = binding!!
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var empCode: String
    private lateinit var empName: String
    private lateinit var empDesignation: String


    override fun onCreateView(inflater: LayoutInflater , container: ViewGroup? , savedInstanceState: Bundle? ): View {
        // Inflate the layout for this fragment
        binding = AccountsFragmentBinding.inflate(inflater, container, false)

        handlingBackButton()

        auth = FirebaseAuth.getInstance()

        readEmpData()
        clickToSave()

        return binding!!.root
    }

    private fun readEmpData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        val dbRef: DatabaseReference = databaseReference.child(auth.currentUser?.phoneNumber.toString())
        dbRef.get().addOnSuccessListener {
            if(it.exists()){
                var code = it.child("employeeCode").value.toString()
                var name = it.child("employeeName").value.toString()
                var designation = it.child("employeeDesignation").value.toString()
                if(code == "null") code = ""
                if(name == "null") name = ""
                if(designation == "null") designation = ""
                binding!!.empCode.setText(code)
                binding!!.empName.setText(name)
                binding!!.empDesignation.setText(designation)
            }
            else if(it.value != null){
                Toast.makeText(activity,"User does not exist",Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener{
            Toast.makeText(activity,"Can't upload Emp details",Toast.LENGTH_SHORT).show()
        }
    }

    private fun clickToSave() {
        binding!!.saveInfoBtn.setOnClickListener{
            empCode = binding!!.empCode.text.toString()
            empName = binding!!.empName.text.toString()
            empDesignation = binding!!.empDesignation.text.toString()
            empName = renamingEmpStrings(empName)
            empDesignation = renamingEmpStrings(empDesignation)
            if(!checkingEmpValues(empCode)){
                Toast.makeText(activity,"Code can not be empty",Toast.LENGTH_SHORT).show()
            }
            else if(!checkingEmpValues(empName)){
                Toast.makeText(activity,"Name can not be empty",Toast.LENGTH_SHORT).show()
            }
            else if(!checkingEmpValues(empDesignation)){
                Toast.makeText(activity,"Designation can not be empty",Toast.LENGTH_SHORT).show()
            }
            else{
                databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                val dbRef: DatabaseReference = databaseReference.child(auth.currentUser?.phoneNumber.toString())
                dbRef.updateChildren(
                    mapOf(
                        "employeeCode" to empCode,
                        "employeeName" to empName,
                        "employeeDesignation" to empDesignation
                    )
                ).addOnCompleteListener{
                    if(it.isSuccessful){
                        Toast.makeText(activity, "Saved", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(activity, "Can't update", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
    private fun checkingEmpValues(empVal: String): Boolean {
        for(i in empVal.indices){
            if(empVal[i]!=' ') return true
        }
        return false
    }
    private fun renamingEmpStrings(str: String): String {
        var s = ""
        val n = str.length
        var lastIndex = 0
        for (i in n-1 downTo 0) {
            val c = str[i]
            if (c in 'a'..'z' || c in 'A'..'Z'){
                lastIndex = i
                break
            }
        }
        for(i in 0 until lastIndex+1){
            if(i<str.length) s += str[i]
        }
        return s
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