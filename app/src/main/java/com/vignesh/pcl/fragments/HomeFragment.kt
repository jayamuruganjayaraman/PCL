package com.vignesh.pcl.fragments

import com.vignesh.pcl.databinding.FragmentHomeBinding
import com.vignesh.pcl.viewModel.TournamentViewModel

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.Visibility
import com.vignesh.pcl.R
import com.vignesh.pcl.activity.MainActivity
import com.vignesh.pcl.adapter.TeamAdapter
import com.vignesh.pcl.databinding.ContentLoadTournamentBinding
import com.vignesh.pcl.model.MatchEntity
import com.vignesh.pcl.model.TeamEntity
import com.vignesh.pcl.utils.DebouncedOnClickListener
import com.vignesh.pcl.viewModel.MatchViewModel
import com.vignesh.pcl.viewModel.MatchViewModelFactory
import com.vignesh.pcl.viewModel.TeamViewModel
import com.vignesh.pcl.viewModel.TeamViewModelFactory
import com.vignesh.pcl.viewModel.TournamentViewModelFactory
import kotlinx.coroutines.launch
import java.util.*

class HomeFragment : Fragment(),CreateTeamDialogFragment.TeamCreateListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var contentLoadTournamentBinding: ContentLoadTournamentBinding
    private val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    private val matchViewModel: MatchViewModel by activityViewModels {
        (requireActivity() as MainActivity).matchFactory
    }

    private val teamViewModel: TeamViewModel by activityViewModels {
        (requireActivity() as MainActivity).teamFactory
    }

    private val tournamentViewModel: TournamentViewModel by activityViewModels {
        (requireActivity() as MainActivity).tournamentFactory
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        contentLoadTournamentBinding = binding.contentLoadTournament
        val tournamentId = arguments?.getLong("tournamentId")


        Log.d("TournamentList:: ","List :: "+tournamentId)
        var tLocation = ""
        var tStartDate = 0L

        val teamAdapter = TeamAdapter { team ->
            // handle click
            // Toast.makeText(requireContext(), "Clicked: ${team.name}", Toast.LENGTH_SHORT).show()
        }
        contentLoadTournamentBinding.teamGridView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        contentLoadTournamentBinding.teamGridView.adapter = teamAdapter
        tournamentViewModel.getTournamentById(tournamentId!!).observe(viewLifecycleOwner) { tournament ->
            if (tournament != null) {
                // Tournament exists → update UI
                binding.llNewMatch.visibility = View.GONE
                binding.llTournament.visibility = View.VISIBLE
                contentLoadTournamentBinding.tvTournamentName.text = tournament.name
                contentLoadTournamentBinding.tvLocation.text = tournament.location
                contentLoadTournamentBinding.tvTournamentDate.text = "${getDateTime(tournament.startDate)} - ${getDateTime(tournament.endDate)}"
                tStartDate =tournament.startDate
                tLocation = tournament.location
                //handleMatches(tournament.tournamentId)
            } else {
                // No tournament
                binding.llNewMatch.visibility = View.VISIBLE
                binding.llTournament.visibility = View.GONE
                contentLoadTournamentBinding.tvTournamentName.text = getString(R.string.txt_no_tournament)
                contentLoadTournamentBinding.btnCreateTeam.visibility = View.GONE
                contentLoadTournamentBinding.btnStartMatch.visibility = View.GONE
            }
        }
        binding.btnCreateTournament.setOnClickListener {

        }

        // Create Team Button
        contentLoadTournamentBinding.btnCreateTeam.setOnClickListener(object :
            DebouncedOnClickListener(CLICK_INT) {
            override fun onDebouncedClick(v: View?) {
                val bundle = Bundle().apply {
                    putLong("tournamentId", tournamentId)
                }
                val teamDialogFragment = CreateTeamDialogFragment()
                teamDialogFragment.arguments = bundle
                teamDialogFragment.show(childFragmentManager, "CreateTeamDialog")
            }
        })

        // Start Match Button
        contentLoadTournamentBinding.btnStartMatch.setOnClickListener {
            val teamList =  teamViewModel.getTeamsList(tournamentId)
            Log.d("TournamentList","TeamList:: "+teamList)
            val nameList = teamList.map { it.name }
            matchViewModel.scheduleMatches(tournamentId,nameList,tLocation,tStartDate)
        }

        handleMatches(tournamentId)
        teamViewModel.getTeamsByTournamentId(tournamentId).observe(viewLifecycleOwner) { teams ->
            Log.d("TournamentList","TeamList:: "+teams)
            when {
                teams.isEmpty() -> {
                    // No teams → show "create team" button
                    contentLoadTournamentBinding.btnCreateTeam.visibility = View.VISIBLE
                    contentLoadTournamentBinding.llNoTeams.visibility = View.VISIBLE
                    contentLoadTournamentBinding.btnStartMatch.visibility = View.INVISIBLE
                }
                teams.size in 1..5 -> {
                    // Less than 6 teams
                    contentLoadTournamentBinding.btnCreateTeam.visibility = View.VISIBLE
                    contentLoadTournamentBinding.llNoTeams.visibility = View.GONE
                    contentLoadTournamentBinding.teamGridView.visibility = View.VISIBLE
                    contentLoadTournamentBinding.btnStartMatch.visibility = View.INVISIBLE

                }
                teams.size == 6 -> {
                    // Exactly 6 teams → allow scheduling matches
                    contentLoadTournamentBinding.btnCreateTeam.visibility = View.GONE
                    contentLoadTournamentBinding.llNoTeams.visibility = View.GONE
                    contentLoadTournamentBinding.teamGridView.visibility = View.VISIBLE
                    contentLoadTournamentBinding.btnStartMatch.visibility = View.VISIBLE
                }
            }

            // Update team recycler
            teamAdapter.submitList(teams)
        }

        return binding.root
    }

    private fun getDateTime(time: Long): String {
        val date = Date(time)
        return formatter.format(date)
    }


    private fun handleMatches(tournamentId: Long) {
        matchViewModel.loadMatches(tournamentId)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                matchViewModel.matches.collect { matchList ->
                    if (matchList.isEmpty()) {
                        // No match scheduled
                        contentLoadTournamentBinding.btnStartMatch.visibility = View.VISIBLE
                    } else {
                        // Matches exist
                        contentLoadTournamentBinding.btnStartMatch.visibility = View.GONE
                    }
                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()

    }

    override fun onTeamCreated(team: TeamEntity) {
        teamViewModel.insertTeam(team)
        Log.d("TournamentList","TeamName:: "+team.name+"TID:: "+team.tournamentId)
    }
}