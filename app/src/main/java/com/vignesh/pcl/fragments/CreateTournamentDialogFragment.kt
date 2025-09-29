package com.vignesh.pcl.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.vignesh.pcl.databinding.FragmentCreateTournamnetBinding
import com.vignesh.pcl.model.TournamentEntity
import com.vignesh.pcl.viewModel.TournamentViewModel
import com.vignesh.pcl.viewModel.TournamentViewModelFactory
import java.util.Calendar

class CreateTournamentDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentCreateTournamnetBinding
    private var startDateMillis: Long = 0L
    private var endDateMillis: Long = 0L

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        binding = FragmentCreateTournamnetBinding.inflate(layoutInflater)

        val tournamentViewModelFactory = TournamentViewModelFactory(requireActivity().application)
        val tournamentViewModel = ViewModelProvider(requireActivity(), tournamentViewModelFactory)[TournamentViewModel::class.java]

        val etTName = binding.etTName
        val etTLocation = binding.etTLocation
        val etStartDate = binding.etStartDate
        val etEndDate = binding.etEndDate

        // Handle close button click
        binding.ivClose.setOnClickListener {
            dismiss()
        }

        // Date pickers
        etStartDate.setOnClickListener {
            showDatePicker(etStartDate, isStartDate = true)
        }

        etEndDate.setOnClickListener {
            showDatePicker(etEndDate, isStartDate = false)
        }

        // Create Tournament button
        binding.btnCreateTournament.setOnClickListener {
            val name = etTName.text?.toString()?.trim().orEmpty()
            val location = etTLocation.text?.toString()?.trim().orEmpty()
            val startDate = etStartDate.text?.toString().orEmpty()
            val endDate = etEndDate.text?.toString().orEmpty()

            if (name.isEmpty() || location.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(requireContext(), "All fields are required", Toast.LENGTH_SHORT).show()
            } else {
                val tournament = TournamentEntity(
                      0,
                    name = name,
                    location = location,
                    startDate = startDateMillis,
                    endDate = endDateMillis,
                     false
                )

                val result = tournamentViewModel.insertTournament(tournament)
                Log.d("CreateTournament", "Insert result: $result")
                dismiss()
            }
        }

        builder.setView(binding.root)
        return builder.create()
    }

    private fun showDatePicker(target: com.google.android.material.textfield.TextInputEditText, isStartDate: Boolean) {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth, 0, 0, 0)
                val millis = calendar.timeInMillis

                val dateStr = "$dayOfMonth/${month + 1}/$year"
                target.setText(dateStr)

                if (isStartDate) {
                    startDateMillis = millis
                } else {
                    endDateMillis = millis
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }
}
