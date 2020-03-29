package ir.esmaeili.stopcar.ui.fragments.newcar

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.esmaeili.stopcar.BR
import ir.esmaeili.stopcar.R
import ir.esmaeili.stopcar.adapter.ColorPickAdapter
import ir.esmaeili.stopcar.databinding.DialogPickColorBinding
import ir.esmaeili.stopcar.models.CarColor
import ir.esmaeili.stopcar.ui.base.BaseBottomSheet
import ir.esmaeili.stopcar.utils.Constants
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import javax.inject.Inject

class ColorPickDialogFragment :
    BaseBottomSheet<DialogPickColorBinding, PickColorViewModel>(),
    CarColorCallback {
    override fun onColorPicked(carColor: CarColor) {
        sharedViewModel.selectCarColor(carColor)
        this.dismiss()
    }

    lateinit var factory: ColorPickDialogViewModelProvider
        @Inject set
    lateinit var mContext: Context
        @Inject set

    private lateinit var mViewModel: PickColorViewModel

    override fun getLayoutId(): Int {
        return R.layout.dialog_pick_color
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): PickColorViewModel {
        mViewModel = ViewModelProvider(this, factory).get(PickColorViewModel::class.java)
        return mViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ColorPickAdapter(mViewModel.getCarColors(), this)
        viewDataBinding.adapter = adapter
        viewDataBinding.layoutManager = GridLayoutManager(
            mContext,
            Constants.COLOR_PICK_SPAN_COUNT,
            RecyclerView.VERTICAL,
            false
        )
        viewDataBinding.animation = SlideInUpAnimator()

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

    override fun getStatusBarColor() = Color.BLUE
}