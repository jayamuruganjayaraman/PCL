package com.vignesh.pcl.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.vignesh.pcl.R
import com.vignesh.pcl.adapter.HomeTabAdapter
import com.vignesh.pcl.databinding.ActivityMainBinding
import com.vignesh.pcl.databinding.ContentMainBinding

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

        var position = 0
        val extras = intent.extras
        if (extras != null) {
            position = extras.getInt("viewpager_position")
        }
        val tabTitles = listOf(
            getString(R.string.title_home),
            getString(R.string.title_teams),
            getString(R.string.title_matches),
            getString(R.string.title_points),
            getString(R.string.title_statistics)
        )

        val adapter = HomeTabAdapter(this@MainActivity, position)
        contentMainBinding.viewPager.adapter = adapter
        TabLayoutMediator(contentMainBinding.tabHome, contentMainBinding.viewPager) { tab, pos ->
            tab.text = tabTitles[pos]
        }.attach()

        binding.fabAddNew.setOnClickListener {
            val intent = Intent(this, NewTournamentActivity::class.java)
            startActivity(intent)
        }


    }
}