package ir.esmaeili.stopcar.ui.fragments.history

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.esmaeili.stopcar.BR
import ir.esmaeili.stopcar.R
import ir.esmaeili.stopcar.adapter.ParkHistoryAdapter
import ir.esmaeili.stopcar.databinding.FragmentHistoryBinding
import ir.esmaeili.stopcar.models.AlertView
import ir.esmaeili.stopcar.models.EventType
import ir.esmaeili.stopcar.models.HistoryJoinCar
import ir.esmaeili.stopcar.models.Response
import ir.esmaeili.stopcar.ui.base.BaseFragment
import ir.esmaeili.stopcar.utils.Utils
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import saman.zamani.persiandate.PersianDate
import saman.zamani.persiandate.PersianDateFormat
import javax.inject.Inject

class HistoryFragment : BaseFragment<FragmentHistoryBinding, HistoryViewModel>(),
    HistoryCallback {
    private lateinit var mViewModel: HistoryViewModel
    private lateinit var historyAdapter: ParkHistoryAdapter
    lateinit var persianDate: PersianDate
        @Inject set
    lateinit var persianDateFormat: PersianDateFormat
        @Inject set
    lateinit var factory: HistoryViewModelProviderFactory
        @Inject set

    lateinit var utils: Utils
        @Inject set

    lateinit var mContext: Context
        @Inject set

    override fun getLayoutId(): Int {
        return R.layout.fragment_history
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): HistoryViewModel {
        mViewModel = ViewModelProvider(this, factory).get(HistoryViewModel::class.java)
        return mViewModel
    }

    override fun onViewCreated(p0: View, savedInstanceState: Bundle?) {
        super.onViewCreated(p0, savedInstanceState)
        historyAdapter = ParkHistoryAdapter(this, persianDate, persianDateFormat)
        viewDataBinding.adapter = historyAdapter
        viewDataBinding.animation = SlideInUpAnimator()
        viewDataBinding.layoutManager = LinearLayoutManager(mContext, RecyclerView.VERTICAL, false)
        viewDataBinding.rvParkHistory.setHasFixedSize(true)
        initObservers()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (mViewModel.isStateSaved()) {
            mViewModel.getHistoryState().observe(viewLifecycleOwner, Observer { historyList ->
                historyAdapter.updateHistory(historyList)
            })
            mViewModel.getHistoryScrollPosition().observe(viewLifecycleOwner, Observer {
                viewDataBinding.rvParkHistory.layoutManager?.onRestoreInstanceState(it)
            })
        }
    }

    private fun initObservers() {
        if (!mViewModel.isStateSaved()) {
            mViewModel.getHistoryList()
        }
        mViewModel.getHistory().observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Response.None -> {
                    sharedViewModel.setProgressbar(false)
                    emptyView(false)
                }
                is Response.Success -> {
                    emptyView(false)
                    sharedViewModel.setProgressbar(false)
                    historyAdapter.updateHistory(response.data)
                    mViewModel.saveHistoryState(response.data)
                }
                is Response.Error -> {
                    emptyView(false)
                    sharedViewModel.setProgressbar(false)
                    sharedViewModel.setAlertEvent(
                        AlertView.CustomToast(
                            EventType.ERROR, utils.getString(R.string.label_error_title),
                            utils.getString(R.string.label_error_description_car_history),
                            R.drawable.ic_error_white_24dp
                        )
                    )
                }
                is Response.Loading -> {
                    sharedViewModel.setProgressbar(true)
                    emptyView(false)
                }
                is Response.Empty -> {
                    mViewModel.clearHistoryState()
                    sharedViewModel.setProgressbar(false)
                    emptyView(true)
                }
            }
        })
    }

    private fun emptyView(state: Boolean = false) {
        if (state) {
            viewDataBinding.frameHistoryEmptyView.visibility = View.VISIBLE
        } else {
            viewDataBinding.frameHistoryEmptyView.visibility = View.GONE
        }
    }

    override fun onHistoryItemClick(history: HistoryJoinCar) {
        val action =
            HistoryFragmentDirections.actionFragmentHistoryToFragmentHistoryDetails(history)
        findNavController().navigate(action)
    }

    override fun onPause() {
        super.onPause()
        mViewModel.setHistoryScrollPosition(viewDataBinding.rvParkHistory.layoutManager?.onSaveInstanceState()!!)
    }
}