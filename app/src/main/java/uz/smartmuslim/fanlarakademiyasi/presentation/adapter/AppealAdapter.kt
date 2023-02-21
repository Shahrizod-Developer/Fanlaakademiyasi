package uz.smartmuslim.fanlarakademiyasi.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.smartmuslim.fanlarakademiyasi.R
import uz.smartmuslim.fanlarakademiyasi.app.App
import uz.smartmuslim.fanlarakademiyasi.data.local.room.entity.AppealEntity
import uz.smartmuslim.fanlarakademiyasi.data.model.AppealData
import uz.smartmuslim.fanlarakademiyasi.databinding.ItemAppealBinding
import java.text.SimpleDateFormat
import java.util.*

class AppealAdapter : ListAdapter<AppealData, AppealAdapter.ViewHolder>(AppealDiffUtilCallback) {

    private var onItemClickListener: ((AppealData) -> Unit)? = null

    fun submitOnItemClickListener(block: (AppealData) -> Unit) {
        onItemClickListener = block
    }

    inner class ViewHolder(private val binding: ItemAppealBinding) :
        RecyclerView.ViewHolder(binding.root) {


        init {
            itemView.setOnClickListener {
                onItemClickListener?.invoke(getItem(absoluteAdapterPosition))
            }
        }

        fun onBind() {

            val data = getItem(absoluteAdapterPosition)
            binding.fullName.text = data.fullName
            binding.appeal.text = data.content
            binding.time.text = convertLongToTime(data.createDate)

            when (data.status) {
                0 -> binding.image.setImageResource(R.drawable.unread)
                1 -> binding.image.setImageResource(R.drawable.yellow)
                2 -> binding.image.setImageResource(R.drawable.answered)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemAppealBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.onBind()
}

private val AppealDiffUtilCallback = object : DiffUtil.ItemCallback<AppealData>() {
    override fun areItemsTheSame(oldItem: AppealData, newItem: AppealData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: AppealData, newItem: AppealData): Boolean {
        return oldItem.content == newItem.content
                && oldItem.address == newItem.address
                && oldItem.passportData == newItem.passportData
                && oldItem.phoneNumber == newItem.phoneNumber
                && oldItem.fullName == newItem.fullName
                && oldItem.type == newItem.type
                && oldItem.recipient == newItem.recipient
                && oldItem.id == newItem.id
                && oldItem.useId == newItem.useId
                && oldItem.birthDate == newItem.birthDate
                && oldItem.status == newItem.status
                && oldItem.createDate == newItem.createDate
    }

}