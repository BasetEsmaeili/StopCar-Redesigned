package ir.esmaeili.stopcar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ir.esmaeili.stopcar.R
import ir.esmaeili.stopcar.databinding.ItemviewMangeCarsBinding
import ir.esmaeili.stopcar.databinding.ViewHeaderBinding
import ir.esmaeili.stopcar.models.Car
import ir.esmaeili.stopcar.ui.base.BaseViewHolder
import ir.esmaeili.stopcar.ui.fragments.cars.ManageCarsItemViewViewModel
import ir.esmaeili.stopcar.utils.Constants

class ManageCarsAdapter(private val mangeCarsCallback: MangeCarsCallback) :
    RecyclerView.Adapter<BaseViewHolder>() {
    private val cars: ArrayList<Car> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            Constants.VIEW_TYPE_LIST -> {
                val listVIew = DataBindingUtil.inflate<ItemviewMangeCarsBinding>(
                    LayoutInflater.from(parent.context!!),
                    R.layout.itemview_mange_cars,
                    parent,
                    false
                )
                ManageCarsViewHolder(listVIew)
            }
            Constants.VIEW_TYPE_TOOLBAR -> {
                val headerView = DataBindingUtil.inflate<ViewHeaderBinding>(
                    LayoutInflater.from(parent.context!!),
                    R.layout.view_header,
                    parent,
                    false
                )
                ManageCarsToolbarViewHolder(headerView)
            }
            else -> throw IllegalStateException()
        }
    }


    fun updateCars(list: List<Car>) {
        this.cars.clear()
        this.cars.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return cars.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            Constants.VIEW_TYPE_TOOLBAR
        } else {
            Constants.VIEW_TYPE_LIST
        }
    }

    fun deleteCar(position: Int) {
        mangeCarsCallback.onCarRemoved(cars[position - 1])
        cars.removeAt(position - 1)
        notifyItemRemoved(position)
    }

    override fun onBindViewHolder(viewHolder: BaseViewHolder, position: Int) {
        if (viewHolder is ManageCarsViewHolder)
            viewHolder.onBind(position - 1)
        else if (viewHolder is ManageCarsToolbarViewHolder)
            viewHolder.onBind(position)
    }

    inner class ManageCarsViewHolder(
        private var itemViewMangeCarsBinding: ItemviewMangeCarsBinding
    ) :
        BaseViewHolder(itemViewMangeCarsBinding.root) {
        private var twoDigits: TextView
        private var sixDigits: TextView

        init {
            val view = itemViewMangeCarsBinding.root
            twoDigits = view.findViewById(R.id.tv_plaque_part_one)
            sixDigits = view.findViewById(R.id.tv_plaque_part_two)
        }

        private val viewModel: ManageCarsItemViewViewModel = ManageCarsItemViewViewModel()
        override fun onBind(position: Int) {
            viewModel.setCarName("${cars[position].carName} ${cars[position].carModel}")
            viewModel.setCarColor(cars[position].carColor)
            itemViewMangeCarsBinding.viewModel = viewModel
            itemViewMangeCarsBinding.executePendingBindings()
            twoDigits.text = cars[position].carPlaqueIrCode
            sixDigits.text =
                "${cars[position].carPlaquePartThree} ${cars[position].carPlaqueKeyWord} ${cars[position].carPlaquePartTwo}"
        }

    }

    inner class ManageCarsToolbarViewHolder(private val binding: ViewHeaderBinding) :
        BaseViewHolder(binding.root) {
        override fun onBind(position: Int) {
            binding.animHeaderLottie.setAnimation(R.raw.manage_header)
        }

    }
}