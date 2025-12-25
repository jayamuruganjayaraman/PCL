package com.vignesh.pcl.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.vignesh.pcl.R
import com.vignesh.pcl.activity.MainActivity
import com.vignesh.pcl.adapter.TeamAdapter
import com.vignesh.pcl.adapter.TeamListAdapter
import com.vignesh.pcl.databinding.FragmentStaticsBinding
import com.vignesh.pcl.databinding.FragmentTeamsBinding
import com.vignesh.pcl.model.TeamEntity
import com.vignesh.pcl.utils.DebouncedOnClickListener
import com.vignesh.pcl.viewModel.MatchViewModel
import com.vignesh.pcl.viewModel.TeamViewModel
import com.vignesh.pcl.viewModel.TeamViewModelFactory
import com.vignesh.pcl.viewModel.TournamentViewModel


class TeamsFragment : Fragment(),CreateTeamDialogFragment.TeamCreateListener {
    lateinit var binding: FragmentTeamsBinding
    private val teamViewModel: TeamViewModel by activityViewModels {
        (requireActivity() as MainActivity).teamFactory
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeamsBinding.inflate(inflater, container, false)
        val tournamentId = arguments?.getLong("tournamentId")
        Log.d("TeamList:: ","List :: "+tournamentId)

        val teamAdapter = TeamListAdapter { team ->
            // handle click
            // Toast.makeText(requireContext(), "Clicked: ${team.name}", Toast.LENGTH_SHORT).show()
        }
        binding.teamGridView.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.teamGridView.setHasFixedSize(true)
        binding.teamGridView.adapter = teamAdapter
        teamViewModel.getTeamsByTournamentId(tournamentId!!).observe(viewLifecycleOwner) { teams ->
            Log.d("TeamList","TeamList:: "+teams)
            when {
                teams.isEmpty() -> {
                    // No teams → show "create team" button
                    binding.llNewTeam.visibility = View.VISIBLE
                    binding.llTeams.visibility = View.GONE
                }
                teams.size in 1..5 -> {
                    // Less than 6 teams
                    binding.btnCreateTeam.visibility = View.VISIBLE
                    binding.teamGridView.visibility = View.VISIBLE
                    binding.llNewTeam.visibility = View.GONE
                    binding.llTeams.visibility = View.VISIBLE

                }
                teams.size == 6 -> {
                    // Exactly 6 teams → allow scheduling matches
                    binding.btnCreateTeam.visibility = View.GONE
                    binding.llNewTeam.visibility = View.GONE
                    binding.teamGridView.visibility = View.VISIBLE
                    binding.llTeams.visibility = View.VISIBLE
                }
            }

            // Update team recycler
            teamAdapter.submitList(teams)
        }
        binding.btnCreateTeam.setOnClickListener(object :
            DebouncedOnClickListener(CLICK_INT) {
            override fun onDebouncedClick(v: View?) {
                val bundle = Bundle().apply {
                    putLong("tournamentId", tournamentId!!)
                }
                val teamDialogFragment = CreateTeamDialogFragment()
                teamDialogFragment.arguments = bundle
                teamDialogFragment.show(childFragmentManager, "CreateTeamDialog")
            }
        })

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = TeamsFragment()
    }

    override fun onTeamCreated(team: TeamEntity) {
        teamViewModel.insertTeam(team)
    }
}