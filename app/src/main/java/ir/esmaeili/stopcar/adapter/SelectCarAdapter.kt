package ir.esmaeili.stopcar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ir.esmaeili.stopcar.R
import ir.esmaeili.stopcar.databinding.ItemviewSelectCarBinding
import ir.esmaeili.stopcar.models.Car
import ir.esmaeili.stopcar.ui.base.BaseViewHolder
import ir.esmaeili.stopcar.ui.fragments.park.SelectCarCallback
import ir.esmaeili.stopcar.ui.fragments.park.SelectCarItemViewViewModel

class SelectCarAdapter(private val callback: SelectCarCallback) :
    RecyclerView.Adapter<BaseViewHolder>() {
    private val carList: ArrayList<Car> = ArrayList()

    fun updateCarList(list: List<Car>) {
        carList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view =
            ItemviewSelectCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectCarViewHolder(view)
    }

    override fun getItemCount(): Int {
        return carList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }

    inner class SelectCarViewHolder(
        private val selectCarBinding: ItemviewSelectCarBinding
    ) : BaseViewHolder(selectCarBinding.root) {
        private var twoDigits: TextView? = null
        private var sixDigits: TextView? = null
        private val viewModel: SelectCarItemViewViewModel = SelectCarItemViewViewModel(callback)

        init {
            val view = selectCarBinding.root
            twoDigits = view.findViewById(R.id.tv_plaque_part_one)
            sixDigits = view.findViewById(R.id.tv_plaque_part_two)
        }

        override fun onBind(position: Int) {
            val car: Car = carList[position]
            viewModel.setCarColor(car.carColor)
            viewModel.setCarName("${car.carName} ${car.carModel}")
            viewModel.setCar(car)
            selectCarBinding.viewModel = viewModel
            selectCarBinding.executePendingBindings()
            twoDigits?.text = car.carPlaqueIrCode
            sixDigits?.text =
                "${car.carPlaquePartThree} ${car.carPlaqueKeyWord} ${car.carPlaquePartTwo}"


        }
    }

}