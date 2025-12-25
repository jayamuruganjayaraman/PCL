package com.vignesh.pcl.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.vignesh.pcl.R
import com.vignesh.pcl.activity.MainActivity
import com.vignesh.pcl.adapter.MatchAdapter
import com.vignesh.pcl.databinding.FragmentMatchesBinding
import com.vignesh.pcl.viewModel.MatchViewModel
import com.vignesh.pcl.viewModel.MatchViewModelFactory
import com.vignesh.pcl.viewModel.TeamViewModel
import com.vignesh.pcl.viewModel.TeamViewModelFactory
import com.vignesh.pcl.viewModel.TournamentViewModel
import com.vignesh.pcl.viewModel.TournamentViewModelFactory
import kotlinx.coroutines.launch

class MatchesFragment : Fragment() {

    lateinit var binding: FragmentMatchesBinding
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
    ): View? {
        binding = FragmentMatchesBinding.inflate(inflater, container, false)

        val tournamentId = arguments?.getLong("tournamentId")
        Log.d("TeamList:: ","List :: "+tournamentId)

        val matchAdapter = MatchAdapter()
        binding.rvMatches.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvMatches.adapter = matchAdapter
        matchViewModel.loadMatches(tournamentId!!)
        handleMatch(matchAdapter)

        return binding.root
    }

    fun handleMatch(matchAdapter: MatchAdapter){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                matchViewModel.matches.collect { matchList ->
                    Log.d("TeamList:: ","Match List :: "+matchList)
                    if (matchList.isEmpty()) {
                        binding.llNewMatch.visibility = View.VISIBLE
                        binding.llMatchList.visibility = View.GONE
                    } else {
                        binding.llNewMatch.visibility = View.GONE
                        binding.llMatchList.visibility = View.VISIBLE
                    }
                    matchAdapter.setData(matchList)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MatchesFragment()
    }
}