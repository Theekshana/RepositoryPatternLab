package com.example.mvvmrepositorylab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmrepositorylab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //val viewModel: UserViewModel by viewModels()

    /** ----  After creating ViewModelFactory  ----- */
    val viewModel: UserViewModel by lazy {
        val viewModelProviderFactory = UserViewModelProviderFactory()
        ViewModelProvider(this, viewModelProviderFactory)[UserViewModel::class.java]
    }
    /** ----  After creating ViewModelFactory  ----- */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.userVM = viewModel
        binding.lifecycleOwner = this

        binding.button.setOnClickListener {
            viewModel.getUsers()
        }
    }
}