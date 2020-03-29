package ir.esmaeili.stopcar.ui.fragments.detail

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.GoogleMap
import ir.esmaeili.stopcar.BR
import ir.esmaeili.stopcar.R
import ir.esmaeili.stopcar.adapter.CustomInfoWindowAdapter
import ir.esmaeili.stopcar.databinding.FragmentHistoryDetailsBinding
import ir.esmaeili.stopcar.models.AlertView
import ir.esmaeili.stopcar.models.EventType
import ir.esmaeili.stopcar.ui.base.BaseFragment
import ir.esmaeili.stopcar.ui.fragments.history.HistoryFragmentArgs
import ir.esmaeili.stopcar.utils.Constants
import ir.esmaeili.stopcar.utils.MapHelper
import ir.esmaeili.stopcar.utils.MapUtilsCallback
import ir.esmaeili.stopcar.utils.Utils
import kotlinx.android.synthetic.main.content_history_details.*
import kotlinx.android.synthetic.main.view_plaque.*
import javax.inject.Inject

class HistoryDetailsFragment :
    BaseFragment<FragmentHistoryDetailsBinding, HistoryDetailsViewModel>(), MapUtilsCallback {
    private val args: HistoryFragmentArgs by navArgs()

    lateinit var factory: HistoryDetailsViewModelProvider
        @Inject set
    lateinit var utils: Utils
        @Inject set

    lateinit var mContext: Context
        @Inject set
    lateinit var mapHelper: MapHelper
        @Inject set

    private lateinit var mViewModel: HistoryDetailsViewModel

    override fun getLayoutId(): Int {
        return R.layout.fragment_history_details
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): HistoryDetailsViewModel {
        mViewModel =
            ViewModelProvider(this, factory).get(HistoryDetailsViewModel::class.java)
        return mViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (!mViewModel.isMapFragmentAttached())
            mViewModel.setHistory(args.HistoryArgs)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(p0: View, savedInstanceState: Bundle?) {
        super.onViewCreated(p0, savedInstanceState)
        if (!mViewModel.isMapFragmentAttached()) {
            mapHelper.init(R.id.container_park_details, childFragmentManager, this)
            mViewModel.setMapFragmentAttached()
        }
        viewDataBinding.viewModel = mViewModel
        setupToolbar()
        setupPlaqueViews()
        if (mapHelper.isMapReady())
            card_history_park_detail.visibility = View.VISIBLE
    }

    private fun setupPlaqueViews() {
        txt_history_date.text =
            "${mViewModel.getParkDate().value} ${utils.getString(R.string.label_clock)}: ${mViewModel.getParkClock().value}"
        tv_plaque_part_one.text = mViewModel.getCarPlaqueIrCode().value
        tv_plaque_part_two.text =
            "${mViewModel.getCarPlaquePartThree().value} ${mViewModel.getCarPlaqueKeyWord().value} ${mViewModel.getCarPlaquePartTwo().value}"
    }

    private fun setupToolbar() {
        baseActivity.setSupportActionBar(viewDataBinding.toolbar)
        baseActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        viewDataBinding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        setHasOptionsMenu(true)
    }

    override fun onStartInitializeMap() {
        sharedViewModel.setProgressbar(true)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        sharedViewModel.setProgressbar(false)
        card_history_park_detail.visibility = View.VISIBLE
        mapHelper.apply {
            if (isNightMode) {
                setMapStyle(R.raw.style)
            }
            getUiSettings().isMapToolbarEnabled = false
            moveCamera(mViewModel.getParkLocation(), Constants.LOCATION_ZOOM, false)
            addMarker(
                mViewModel.getParkLocation(),
                utils.getBitmapFromDrawable(
                    R.drawable.ic_park_location,
                    Constants.MAP_MARKER_WIDTH,
                    Constants.MAP_MARKER_HEIGHT
                )
            )
            setInfoWindowAdapter(
                CustomInfoWindowAdapter(
                    baseActivity,
                    mViewModel.getParkAddress().value!!
                )
            )
            setTrafficEnabled(
                viewModel.getPreference(
                    Constants.KEY_TRAFFIC,
                    Constants.TRAFFIC_DEFAULT_VALUE
                )
            )
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_direction -> startDirection()
        }
        return true

    }

    private fun startDirection() {
        try {
            val uri =
                Uri.parse("google.navigation:q=${mViewModel.getParkLocation().latitude},${mViewModel.getParkLocation().longitude}&avoid=tf")
            Intent(Intent.ACTION_VIEW, uri)
                .apply {
                    startActivity(this)
                }
        } catch (exception: ActivityNotFoundException) {
            sharedViewModel.setAlertEvent(
                AlertView.CustomToast(
                    EventType.ERROR,
                    utils.getString(R.string.label_error_title),
                    utils.getString(R.string.label_not_found_direction_app),
                    R.drawable.ic_error_white_24dp
                )
            )
        }

    }
}