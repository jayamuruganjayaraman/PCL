package com.vignesh.pcl.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.vignesh.pcl.R
import com.vignesh.pcl.adapter.HomeTabAdapter
import com.vignesh.pcl.databinding.ActivityMainBinding
import com.vignesh.pcl.databinding.ContentMainBinding
import com.vignesh.pcl.fragments.CreateTournamentDialogFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var contentMainBinding: ContentMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        contentMainBinding = binding.contentMain
        setContentView(binding.root)
        binding.toolbar.title = getString(R.string.txt_tournment)
        setSupportActionBar(binding.toolbar)


        binding.fabAddNew.setOnClickListener {
            val dialog = CreateTournamentDialogFragment()
            dialog.show(supportFragmentManager, "CreateTournamentDialog")
        }


    }
}