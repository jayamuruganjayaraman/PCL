package com.vignesh.pcl.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.vignesh.pcl.R
import com.vignesh.pcl.databinding.FragmentCreateTeamDialogBinding
import com.vignesh.pcl.model.TeamEntity

class CreateTeamDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentCreateTeamDialogBinding

    interface TeamCreateListener {
        fun onTeamCreated(team: TeamEntity)
    }

    private var listener: TeamCreateListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = when {
            parentFragment is TeamCreateListener -> parentFragment as TeamCreateListener
            context is TeamCreateListener -> context
            else -> null
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentCreateTeamDialogBinding.inflate(LayoutInflater.from(requireContext()))

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(binding.root)
        val tournamentId = arguments?.getLong("tournamentId")
        // Handle close button
        binding.ivClose.setOnClickListener {
            dismiss()
        }

        // Handle create button
        binding.btnCreateTeam.setOnClickListener {
            val teamName = binding.etTeamName.text.toString().trim()
            if (teamName.isNotEmpty()) {
                val team = TeamEntity(
                    name = teamName,
                    tournamentId = tournamentId!! // you can pass actual tournamentId later
                )
                Log.d("TournamentList","TeamName:: "+team.name)
                listener?.onTeamCreated(team)
                dismiss()
            } else {
                binding.tilTeamName.error = "Team name required"
            }
        }

        return builder.create()
    }

}
