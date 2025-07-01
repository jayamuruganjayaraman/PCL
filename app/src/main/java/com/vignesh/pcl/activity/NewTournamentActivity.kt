package com.vignesh.pcl.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vignesh.pcl.R
import com.vignesh.pcl.databinding.ActivityNewTournamentBinding
import com.vignesh.pcl.model.TournamentEntity
import com.vignesh.pcl.utils.DebouncedOnClickListener
import com.vignesh.pcl.viewModel.TournamentViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NewTournamentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewTournamentBinding
    private lateinit var startCalendar: Calendar
    private lateinit var endCalendar: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewTournamentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = " "
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.setTitleTextColor(resources.getColor(R.color.black,null))
        binding.toolbar.navigationIcon!!
            .setColorFilter(resources.getColor(R.color.black, null), PorterDuff.Mode.SRC_ATOP)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        val mViewModel = ViewModelProvider(this)[TournamentViewModel::class.java]

        startCalendar = Calendar.getInstance()
        endCalendar = Calendar.getInstance()

        val startDateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            startCalendar.set(year, month, dayOfMonth)
            updateStartLabel()
        }

        val endDateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            endCalendar.set(year, month, dayOfMonth)
            updateEndLabel()
        }

        binding.tilStartDate.setEndIconOnClickListener(object : DebouncedOnClickListener(DebouncedOnClickListener.CLICK_INT) {
            override fun onDebouncedClick(v: View?) {
                DatePickerDialog(
                    this@NewTournamentActivity,
                    startDateSetListener,
                    startCalendar.get(Calendar.YEAR),
                    startCalendar.get(Calendar.MONTH),
                    startCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        })

        binding.tilEndDate.setEndIconOnClickListener(object : DebouncedOnClickListener(DebouncedOnClickListener.CLICK_INT) {
            override fun onDebouncedClick(v: View?) {
                DatePickerDialog(
                    this@NewTournamentActivity,
                    endDateSetListener,
                    endCalendar.get(Calendar.YEAR),
                    endCalendar.get(Calendar.MONTH),
                    endCalendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        })

        binding.btnCreateTournament.setOnClickListener(object : DebouncedOnClickListener(DebouncedOnClickListener.CLICK_INT) {
            override fun onDebouncedClick(v: View?) {
                val tName = binding.etTName.text.toString().trim()

                if (tName.isEmpty()) {
                    binding.etTName.error = "Tournament name can't be empty."
                    return
                }

                val tournament = TournamentEntity(0,tName, binding.etTLocation.text.toString().trim(), startCalendar.timeInMillis, endCalendar.timeInMillis,false)

                val result = mViewModel.insertTournament(tournament)
                if (result == -1L) {
                    Toast.makeText(this@NewTournamentActivity, "Tournament addition failed.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@NewTournamentActivity, "Tournament added successfully.", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@NewTournamentActivity, MainActivity::class.java))
                   // overridePendingTransition(R.anim.enter, R.anim.exit)
                    finish()
                }
            }
        })


    }


    private fun updateStartLabel() {
        val format = "dd MMM yyyy"
        val sdf = SimpleDateFormat(format, Locale.US)
        binding.etStartDate.setText(sdf.format(startCalendar.time))
    }

    private fun updateEndLabel() {
        val format = "dd MMM yyyy"
        val sdf = SimpleDateFormat(format, Locale.US)
        binding.etEndDate.setText(sdf.format(endCalendar.time))
    }
}