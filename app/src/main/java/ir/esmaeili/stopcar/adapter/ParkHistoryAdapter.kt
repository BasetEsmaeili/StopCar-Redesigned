package ir.esmaeili.stopcar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import ir.esmaeili.stopcar.R
import ir.esmaeili.stopcar.databinding.ItemviewHistoryBinding
import ir.esmaeili.stopcar.databinding.ViewHeaderBinding
import ir.esmaeili.stopcar.models.HistoryJoinCar
import ir.esmaeili.stopcar.ui.base.BaseViewHolder
import ir.esmaeili.stopcar.ui.fragments.history.HistoryCallback
import ir.esmaeili.stopcar.ui.fragments.history.HistoryItemViewViewModel
import ir.esmaeili.stopcar.utils.Constants
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat

class ParkHistoryAdapter(
    private val callback: HistoryCallback,
    private val persianDate: PersianDate,
    private val dateFormat: PersianDateFormat
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val historyItems: ArrayList<HistoryJoinCar> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Constants.VIEW_TYPE_TOOLBAR -> {
                val toolbarView = DataBindingUtil.inflate<ViewHeaderBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.view_header,
                    parent,
                    false
                )
                HistoryToolbarViewHolder(toolbarView)
            }
            Constants.VIEW_TYPE_LIST -> {
                val historyView =
                    DataBindingUtil.inflate<ItemviewHistoryBinding>(
                        LayoutInflater.from(parent.context),
                        R.layout.itemview_history,
                        parent,
                        false
                    )
                ParkHistoryViewHolder(historyView)
            }
            else -> throw IllegalStateException()
        }
    }

    override fun getItemCount(): Int {
        return historyItems.size + 1
    }

    fun updateHistory(list: List<HistoryJoinCar>) {
        this.historyItems.clear()
        this.historyItems.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            Constants.VIEW_TYPE_TOOLBAR
        } else {
            Constants.VIEW_TYPE_LIST
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is ParkHistoryViewHolder)
            viewHolder.onBind(position - 1)
        else if (viewHolder is HistoryToolbarViewHolder)
            viewHolder.onBind(position)
    }

    inner class ParkHistoryViewHolder(
        private val binding: ItemviewHistoryBinding
    ) :
        BaseViewHolder(binding.root) {

        private val viewModel: HistoryItemViewViewModel = HistoryItemViewViewModel(callback)
        private var twoDigits: MaterialTextView? = null
        private var sixDigits: MaterialTextView? = null

        init {
            val view = binding.root
            twoDigits = view.findViewById(R.id.tv_plaque_part_one)
            sixDigits = view.findViewById(R.id.tv_plaque_part_two)
        }

        override fun onBind(position: Int) {
            val history: HistoryJoinCar = historyItems[position]
            val date = dateFormat.format(persianDate)
            twoDigits?.text = history.carPlaqueIrCode
            sixDigits?.text =
                "${history.carPlaquePartThree} ${history.carPlaqueKeyWord} ${history.carPlaquePartTwo}"
            if (date == history.parkDate) {
                viewModel.setIsToday(true)
            } else {
                viewModel.setIsToday(false)
            }
            viewModel.setCarName(history.carName)
            viewModel.setCarModel(history.carModel)
            viewModel.setCarColor(history.carColor)
            viewModel.setCarParkDate(history.parkDate)
            viewModel.setCarParkClock(history.parkClock)
            viewModel.setCarParkAddress(history.parkAddress)
            viewModel.setHistory(history)
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }

    inner class HistoryToolbarViewHolder(private val binding: ViewHeaderBinding) :
        BaseViewHolder(binding.root) {
        override fun onBind(position: Int) {
            binding.animHeaderLottie.setAnimation(R.raw.history_header)
        }
    }
}
