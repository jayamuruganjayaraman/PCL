package com.vignesh.pcl.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.vignesh.pcl.R
import com.vignesh.pcl.adapter.HomeTabAdapter
import com.vignesh.pcl.databinding.ActivityMainBinding
import com.vignesh.pcl.databinding.ContentMainBinding
import com.vignesh.pcl.fragments.CreateTournamentDialogFragment
import com.vignesh.pcl.viewModel.MatchViewModel
import com.vignesh.pcl.viewModel.MatchViewModelFactory
import com.vignesh.pcl.viewModel.TeamViewModel
import com.vignesh.pcl.viewModel.TeamViewModelFactory
import com.vignesh.pcl.viewModel.TournamentViewModel
import com.vignesh.pcl.viewModel.TournamentViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var contentMainBinding: ContentMainBinding
    lateinit var matchFactory: MatchViewModelFactory
    lateinit var teamFactory: TeamViewModelFactory
    lateinit var tournamentFactory: TournamentViewModelFactory
    private lateinit var teamViewModel: TeamViewModel
    private lateinit var matchViewModel: MatchViewModel
    private lateinit var tournamentViewModel : TournamentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        contentMainBinding = binding.contentMain
        setContentView(binding.root)
        binding.toolbar.title = getString(R.string.txt_tournment)
        setSupportActionBar(binding.toolbar)

        tournamentFactory = TournamentViewModelFactory(application)
        tournamentViewModel = ViewModelProvider(this, tournamentFactory)[TournamentViewModel::class.java]
        teamFactory = TeamViewModelFactory(application)
        teamViewModel = ViewModelProvider(this, teamFactory)[TeamViewModel::class.java]
        matchFactory = MatchViewModelFactory(application)
        matchViewModel = ViewModelProvider(this, matchFactory)[MatchViewModel::class.java]
        binding.fabAddNew.setOnClickListener {
            val dialog = CreateTournamentDialogFragment()
            dialog.show(supportFragmentManager, "CreateTournamentDialog")
        }


    }
}