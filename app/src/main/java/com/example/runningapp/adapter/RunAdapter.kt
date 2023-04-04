package com.example.runningapp.adapter

import android.icu.util.Calendar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.runningapp.R
import com.example.runningapp.db.Run
import com.example.runningapp.utility.TrackingUtility
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {


    inner class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivRunImage = itemView.findViewById<ImageView>(R.id.ivRunImage)
        val tvDate = itemView.findViewById<TextView>(R.id.tvDate)
        val tvAvgSpeed = itemView.findViewById<TextView>(R.id.tvAvgSpeed)
        val tvDistance = itemView.findViewById<TextView>(R.id.tvDistance)
        val tvTime = itemView.findViewById<TextView>(R.id.tvTime)
        val tvCalories = itemView.findViewById<TextView>(R.id.tvCalories)

    }

    val diffCallBack = object : DiffUtil.ItemCallback<Run>() {
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    val differ = AsyncListDiffer(this, diffCallBack)

    fun submitList(list: List<Run>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_run, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(run.img).into(holder.ivRunImage)

            val calendar = Calendar.getInstance()
                .apply {
                    timeInMillis = run.timeInMillis
                }
            val dateFormat = SimpleDateFormat("dd:MM:yy", Locale.getDefault())
            holder.tvDate.text =dateFormat.format(calendar.time)

            val avgSpeed = "${run.avgSpeedInKMH}km/h"
            holder.tvAvgSpeed.text = avgSpeed

            val distanceInKm = "${run.distanceInMeters / 1000f}km"
            holder.tvDistance.text = distanceInKm

            holder.tvTime.text = TrackingUtility.getFormattedStopWatch(run.timeInMillis)

            val caloriesBurned = "${run.caloriesBurned}kCal"

            holder.tvCalories.text = caloriesBurned.toString()
        }
    }
}