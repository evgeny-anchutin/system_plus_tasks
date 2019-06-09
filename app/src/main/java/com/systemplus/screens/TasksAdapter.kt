package com.systemplus.screens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.systemplus.R
import com.systemplus.data.TaskData
import kotlinx.android.synthetic.main.item_task.view.*

//todo: сделать из этого StubAdapter для ускорения разработки
class TasksAdapter : RecyclerView.Adapter<TasksAdapter.TaskVh>() {

    var items = mutableListOf<TaskData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskVh {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskVh(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: TaskVh, position: Int) {
        val task = items[position]

        task.isDone?.let {
            if (it) {
                holder.showDoneImage()

            } else {
                holder.showDoneButton()
            }
        }

        holder.showName(task.name)
        holder.showWeight(task.weight)
        holder.showVolume(task.volume)

        holder.showStartAddress(task.startAddress)
        holder.showFinishAddress(task.finishAddress)
    }

    class TaskVh(var vItem: View) : RecyclerView.ViewHolder(vItem) {
        fun showDoneImage() {
            vItem.vDoneImage.visibility = View.VISIBLE
            vItem.vDoneButton.visibility = View.GONE
        }

        fun showDoneButton() {
            vItem.vDoneButton.visibility = View.VISIBLE
            vItem.vDoneImage.visibility = View.GONE
        }

        fun showName(name: String?) {
            vItem.vNameLabel.text = "$name"
        }

        fun showWeight(weight: Double?) {
            vItem.vWeightLabel.text = "$weight"
        }

        fun showVolume(volume: Double?) {
            vItem.vVolumeLabel.text = "$volume"
        }

        fun showStartAddress(startAddress: String?) {
            vItem.vStartAddressLabel.text = "$startAddress"
        }

        fun showFinishAddress(finishAddress: String?) {
            vItem.vFinishAddressLabel.text = "$finishAddress"
        }
    }
}