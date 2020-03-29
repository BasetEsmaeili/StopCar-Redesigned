package ir.esmaeili.stopcar.ui.fragments.cars

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.esmaeili.stopcar.BR
import ir.esmaeili.stopcar.R
import ir.esmaeili.stopcar.adapter.ManageCarsAdapter
import ir.esmaeili.stopcar.adapter.MangeCarsCallback
import ir.esmaeili.stopcar.databinding.FragmentManageCarsBinding
import ir.esmaeili.stopcar.models.AlertView
import ir.esmaeili.stopcar.models.Car
import ir.esmaeili.stopcar.models.EventType
import ir.esmaeili.stopcar.models.Response
import ir.esmaeili.stopcar.ui.base.BaseFragment
import ir.esmaeili.stopcar.utils.Constants
import ir.esmaeili.stopcar.utils.RecyclerViewSwipeHelper
import ir.esmaeili.stopcar.utils.Utils
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import javax.inject.Inject

class ManageCarsFragment : BaseFragment<FragmentManageCarsBinding, ManageCarsViewModel>(),
    NewCarCallback, MangeCarsCallback {
    lateinit var factory: ManageCarsViewModelProviderFactory
        @Inject set

    lateinit var utils: Utils
        @Inject set

    lateinit var mContext: Context
        @Inject set

    private lateinit var mViewModel: ManageCarsViewModel
    override fun onCarSaved() {
        mViewModel.getCars()
    }


    override fun getLayoutId(): Int {
        return R.layout.fragment_manage_cars
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): ManageCarsViewModel {
        mViewModel = ViewModelProvider(this, factory).get(ManageCarsViewModel::class.java)
        return mViewModel
    }

    override fun onViewCreated(p0: View, savedInstanceState: Bundle?) {
        super.onViewCreated(p0, savedInstanceState)
        val adapter = ManageCarsAdapter(this)
        viewDataBinding.adapter = adapter
        viewDataBinding.animation = SlideInUpAnimator()
        viewDataBinding.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        viewDataBinding.rvManageCars.setHasFixedSize(true)
        val itemTouchHelper = ItemTouchHelper(RecyclerViewSwipeHelper(adapter))
        itemTouchHelper.attachToRecyclerView(viewDataBinding.rvManageCars)
        initObservers()
    }

    private fun initObservers() {
        mViewModel.getCars()
        mViewModel.getCarsList().observe(viewLifecycleOwner, Observer { response ->
            if (!response.hasBeenHandled) {
                when (val content = response.getContentIfNotHandled()) {
                    is Response.None -> {
                        sharedViewModel.setProgressbar(false)
                    }
                    is Response.Success -> {
                        sharedViewModel.setProgressbar(false)
                        viewDataBinding.adapter?.updateCars((content.data))
                    }
                    is Response.Error -> {
                        sharedViewModel.setProgressbar(false)
                        sharedViewModel.setAlertEvent(
                            AlertView.CustomToast(
                                EventType.ERROR,
                                utils.getString(R.string.label_error_title),
                                utils.getString(R.string.label_error_get_cars),
                                R.drawable.ic_error_white_24dp
                            )
                        )
                    }
                }
            }
        })
        mViewModel.getEventHandler().observe(viewLifecycleOwner, Observer { response ->
            if (!response.hasBeenHandled)
                when (response.getContentIfNotHandled()) {
                    Constants.EVENT_SAVE_NEW_CAR -> {
                        showNewCarDialog()
                    }
                }
        })
        mViewModel.getDeleteCar().observe(viewLifecycleOwner, Observer { response ->
            if (!response.hasBeenHandled) {
                when (response.getContentIfNotHandled()) {
                    is Response.Loading -> {
                        sharedViewModel.setProgressbar(true)
                    }
                    is Response.Success -> {
                        sharedViewModel.setProgressbar(false)
                        sharedViewModel.setAlertEvent(
                            AlertView.CustomToast(
                                EventType.SUCCESS,
                                utils.getString(R.string.label_success_title),
                                utils.getString(R.string.label_car_deleted),
                                R.drawable.ic_done_gray_24dp
                            )
                        )
                        mViewModel.getCarsCount()
                    }
                    is Response.Error -> {
                        sharedViewModel.setProgressbar(false)
                        sharedViewModel.setAlertEvent(
                            AlertView.CustomToast(
                                EventType.ERROR,
                                utils.getString(R.string.label_error_title),
                                utils.getString(R.string.label_car_not_deleted),
                                R.drawable.ic_error_white_24dp
                            )
                        )
                    }
                }
            }
        })
        mViewModel.getCarsCountLiveData().observe(viewLifecycleOwner, Observer { response ->
            if (!response.hasBeenHandled)
                when (response.getContentIfNotHandled()) {
                    is Response.Empty -> {
                        baseActivity.restartActivity(false)
                    }
                }
        })
    }


    private fun showNewCarDialog() {
        findNavController().navigate(R.id.action_fragmentManageCars_to_addNewCarDialogFragment)
    }

    override fun onCarRemoved(car: Car) {
        mViewModel.deleteCar(car.carID)
    }

}