package com.example.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.travel.R
import com.example.database.entity.User
import com.example.travel.databinding.ActivityMainBinding
import com.example.net.ApiRetrofit
import com.example.ui.user.UserListActivity
import com.example.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var apiService: ApiRetrofit
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val users = mutableListOf<User>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initData()
        initListener()
    }

    private fun initData() {
        viewModel.users.observe(this) { users ->
            for (user in users) {
                Log.d("ShLog---", "$user")
                this.users.add(user)
            }

        }
        viewModel.user.observe(this) {
            Log.d("ShLog---", "$it")
            binding.tvContent.text = it?.toString()
        }
    }

    private fun initListener() {
        binding.btnInsert.setOnClickListener {
            lifecycleScope.launch {
//                for (i in 0 until 10) {
//                    val photos = PhotoUtil.genPhoto()
//                    val user = User(null, "${System.currentTimeMillis()}", "123456789", photos[i])
//                    viewModel.insert(user)
//                }
                try {
                    val response = apiService.searchRepos("android")
                    Log.d("ShLog--", "response:$response")
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }

        binding.btnSelect.setOnClickListener {
            startActivity(Intent(this, UserListActivity::class.java))
//            lifecycleScope.launch {
//                viewModel.queryAllUser()
//                viewModel.queryUserById(227)
//            }
        }

        binding.btnDelete.setOnClickListener {
            lifecycleScope.launch {
                users.forEach {
                    viewModel.deleteAllUser(it)
                }
            }
        }
    }

    override fun onBackPressed() {
        goBackToDesktop()
        super.onBackPressed()
    }

    private fun goBackToDesktop() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.addCategory(Intent.CATEGORY_HOME)
        startActivity(intent)
    }
}