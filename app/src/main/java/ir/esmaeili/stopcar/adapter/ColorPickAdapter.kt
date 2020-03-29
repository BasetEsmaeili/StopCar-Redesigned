package ir.esmaeili.stopcar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.esmaeili.stopcar.databinding.ItemviewColorBinding
import ir.esmaeili.stopcar.models.CarColor
import ir.esmaeili.stopcar.ui.base.BaseViewHolder
import ir.esmaeili.stopcar.ui.fragments.newcar.CarColorPickerViewModel
import ir.esmaeili.stopcar.ui.fragments.newcar.CarColorCallback

class ColorPickAdapter(
    private val carColorList: List<CarColor>,
    private val callback: CarColorCallback
) : RecyclerView.Adapter<BaseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = ItemviewColorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ColorPickerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return carColorList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }


    inner class ColorPickerViewHolder(
        private val binding: ItemviewColorBinding
    ) :
        BaseViewHolder(binding.root) {
        private val viewModel: CarColorPickerViewModel = CarColorPickerViewModel(callback)
        override fun onBind(position: Int) {
            viewModel.setColor(carColorList[position].color)
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }

}