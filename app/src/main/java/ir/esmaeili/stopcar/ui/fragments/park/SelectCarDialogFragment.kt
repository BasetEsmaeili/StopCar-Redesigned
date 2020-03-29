package ir.esmaeili.stopcar.ui.fragments.park

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.esmaeili.stopcar.BR
import ir.esmaeili.stopcar.R
import ir.esmaeili.stopcar.adapter.SelectCarAdapter
import ir.esmaeili.stopcar.databinding.DialogSelectCarBinding
import ir.esmaeili.stopcar.models.AlertView
import ir.esmaeili.stopcar.models.Car
import ir.esmaeili.stopcar.models.EventType
import ir.esmaeili.stopcar.models.Response
import ir.esmaeili.stopcar.ui.base.BaseBottomSheet
import ir.esmaeili.stopcar.utils.Constants
import ir.esmaeili.stopcar.utils.Utils
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import javax.inject.Inject

class SelectCarDialogFragment : BaseBottomSheet<DialogSelectCarBinding, SelectCarViewModel>(),
    SelectCarCallback {

    lateinit var utils: Utils
        @Inject set
    lateinit var factory: SelectCarDialogViewModelProvider
        @Inject set

    lateinit var mContext: Context
        @Inject set

    private lateinit var mViewModel: SelectCarViewModel


    private lateinit var selectCarAdapter: SelectCarAdapter

    override fun onCarSelected(car: Car) {
        sharedViewModel.selectCar(car)
        this.dismiss()
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_select_car
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): SelectCarViewModel {
        mViewModel = ViewModelProvider(this, factory).get(SelectCarViewModel::class.java)
        return mViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectCarAdapter = SelectCarAdapter(this)
        viewDataBinding.adapter = selectCarAdapter
        viewDataBinding.animation = SlideInUpAnimator()
        viewDataBinding.layoutManager = GridLayoutManager(
            mContext,
            Constants.SPAN_COUNT_SELECT_CAR,
            RecyclerView.VERTICAL,
            false
        )
        initObservers()
    }

    private fun initObservers() {
        mViewModel.getSavedCarsDB()
        mViewModel.getSavedCars().observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Response.Error -> {
                    sharedViewModel.setProgressbar(false)
                    sharedViewModel.setAlertEvent(
                        AlertView.CustomToast(
                            EventType.ERROR, utils.getString(R.string.label_error_title),
                            utils.getString(R.string.label_error_description_car_history),
                            R.drawable.ic_error_white_24dp
                        )
                    )
                }
                is Response.Success -> {
                    sharedViewModel.setProgressbar(false)
                    selectCarAdapter.updateCarList(response.data)
                }
                is Response.None -> {
                    sharedViewModel.setProgressbar(false)
                }
                is Response.Loading -> {
                    sharedViewModel.setProgressbar(true)
                }
            }
        })
    }

    override fun getBackgroundColor(): Int {
        val isNightMode = mViewModel.getPreference(
            Constants.KEY_DARK_NIGHT,
            Constants.NIGHT_MODE_DEFAULT_VALUE
        )
        return if (isNightMode)
            ContextCompat.getColor(mContext, R.color.color_night_background)
        else
            ContextCompat.getColor(mContext, R.color.color_white)
    }


}