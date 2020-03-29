package ir.esmaeili.stopcar.ui.fragments.cars

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.animation.Interpolator
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import ir.esmaeili.stopcar.BR
import ir.esmaeili.stopcar.R
import ir.esmaeili.stopcar.adapter.ColorPickAdapter
import ir.esmaeili.stopcar.databinding.DialogNewCarBinding
import ir.esmaeili.stopcar.models.AlertView
import ir.esmaeili.stopcar.models.CarColor
import ir.esmaeili.stopcar.models.EventType
import ir.esmaeili.stopcar.models.Response
import ir.esmaeili.stopcar.ui.base.BaseBottomSheet
import ir.esmaeili.stopcar.ui.fragments.newcar.CarColorCallback
import ir.esmaeili.stopcar.ui.fragments.newcar.NewCarFragment
import ir.esmaeili.stopcar.ui.fragments.park.SelectCarDialogViewModelProvider
import ir.esmaeili.stopcar.utils.Constants
import ir.esmaeili.stopcar.utils.NewCarEditTextChangeListener
import ir.esmaeili.stopcar.utils.NumberExcludeFilter
import ir.esmaeili.stopcar.utils.Utils
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import javax.inject.Inject


class NewCarDialogFragment : BaseBottomSheet<DialogNewCarBinding, NewCarDialogViewModel>(),
    CarColorCallback {

    lateinit var factory: NewCarDialogViewModelProvider
        @Inject set
    lateinit var mContext: Context
        @Inject set
    private lateinit var mViewModel: NewCarDialogViewModel
    private var isColorExpanded = false
    lateinit var utils: Utils
        @Inject set

    override fun onColorPicked(carColor: CarColor) {
        mViewModel.setCarColor(carColor.color)
        expandColorPicker()
        changeCarDrawableColor(carColor)
    }

    private fun changeCarDrawableColor(carColor: CarColor) {
        if (carColor.color.isNotEmpty()) {
            viewDataBinding.tvNewCarColor.setTextColor(Color.parseColor(carColor.color))
            viewDataBinding.tvNewCarColor.compoundDrawables.forEach { drawable ->
                if (drawable != null)
                    drawable.colorFilter =
                        PorterDuffColorFilter(
                            Color.parseColor(carColor.color),
                            PorterDuff.Mode.SRC_IN
                        )

            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_new_car
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): NewCarDialogViewModel {
        mViewModel = ViewModelProvider(this, factory).get(NewCarDialogViewModel::class.java)
        return mViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewModel = mViewModel
        viewDataBinding.sharedViewModel = sharedViewModel
        viewDataBinding.layoutManager = GridLayoutManager(
            mContext,
            Constants.COLOR_PICK_SPAN_COUNT,
            RecyclerView.VERTICAL,
            false
        )
        viewDataBinding.adapter = ColorPickAdapter(mViewModel.getCarColors(), this)
        viewDataBinding.slideInUpAnimator =
            SlideInUpAnimator(Interpolator { Constants.ITEM_ANIMATOR_INTERPOLATOR })
        viewDataBinding.rvNewCarColor.isNestedScrollingEnabled = false
        editTextEvents()
        initObservers()
        setupToolbar()
        initSavedStateObservers()
    }

    private fun initSavedStateObservers() {
        if (utils.inputIsNotEmpty(viewDataBinding.edtNewCarName))
            mViewModel.getCarName().observe(
                viewLifecycleOwner,
                Observer { setTextInputLayout(viewDataBinding.edtNewCarName, it) })
        if (utils.inputIsNotEmpty(viewDataBinding.edtNewCarModel))
            mViewModel.getCarModel().observe(
                viewLifecycleOwner,
                Observer { setTextInputLayout(viewDataBinding.edtNewCarModel, it) })
        mViewModel.getCarColor().observe(viewLifecycleOwner, Observer {
            changeCarDrawableColor(
                CarColor(it)
            )
        })
        if (utils.inputIsNotEmpty(viewDataBinding.edtIrCode))
            mViewModel.getCarPlaqueIrCode().observe(
                viewLifecycleOwner,
                Observer { setTextInputLayout(viewDataBinding.edtIrCode, it) })
        if (utils.inputIsNotEmpty(viewDataBinding.edtPartThreeCode))
            mViewModel.getCarPlaquePartThreeCode().observe(
                viewLifecycleOwner,
                Observer { setTextInputLayout(viewDataBinding.edtPartThreeCode, it) })
        if (utils.inputIsNotEmpty(viewDataBinding.edtNewCarKeyword))
            mViewModel.getCarPlaqueKeyWord().observe(
                viewLifecycleOwner,
                Observer { setTextInputLayout(viewDataBinding.edtNewCarKeyword, it) })
        if (utils.inputIsNotEmpty(viewDataBinding.edtNewCarTwoPart))
            mViewModel.getCarPlaquePartTwo().observe(
                viewLifecycleOwner,
                Observer { setTextInputLayout(viewDataBinding.edtNewCarTwoPart, it) })
    }

    private fun setTextInputLayout(input: TextInputEditText, text: String) {
        input.setText(text)
    }


    private fun initObservers() {
        mViewModel.getNewCarLiveData().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Response.Success -> {
                    sharedViewModel.setAlertEvent(
                        AlertView.CustomToast(
                            EventType.SUCCESS,
                            resources.getString(R.string.label_success_title),
                            resources.getString(R.string.label_success_save_car),
                            R.drawable.ic_done_gray_24dp
                        )
                    )
                    this@NewCarDialogFragment.dismiss()
                }
                is Response.Error -> {
                    sharedViewModel.setAlertEvent(
                        AlertView.CustomToast(
                            EventType.ERROR,
                            resources.getString(R.string.label_error_title),
                            resources.getString(R.string.label_error_save_car),
                            R.drawable.ic_error_white_24dp
                        )
                    )
                    this@NewCarDialogFragment.dismiss()
                }
            }
        })
        mViewModel.getEventHandler().observe(viewLifecycleOwner, Observer {
            when (it) {
                Constants.EVENT_EXPAND_COLOR_PICKER -> {
                    expandColorPicker()
                }
            }
        })
    }

    private fun expandColorPicker() {
        if (isColorExpanded) {
            viewDataBinding.rvNewCarColor.visibility = View.GONE
            viewDataBinding.tvNewCarColor.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_arrow_drop_down_gray_24dp,
                0,
                R.drawable.ic_car_color,
                0
            )
            isColorExpanded = false
        } else {
            viewDataBinding.rvNewCarColor.visibility = View.VISIBLE
            viewDataBinding.tvNewCarColor.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_arrow_drop_up_gray_24dp,
                0,
                R.drawable.ic_car_color,
                0
            )
            isColorExpanded = true

        }
    }

    override fun isSheetAlwaysExpanded(): Boolean {
        return true
    }

    private fun setupToolbar() {
        baseActivity.setSupportActionBar(viewDataBinding.toolbar)
        baseActivity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun editTextEvents() {
        viewDataBinding.inputNewCarName.editText?.setOnEditorActionListener(object :
            TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == EditorInfo.IME_ACTION_NEXT) {
                    viewDataBinding.inputNewCarModel.editText?.requestFocus()
                    return true
                }
                return false
            }
        })
        viewDataBinding.inputNewCarModel.editText?.setOnEditorActionListener(object :
            TextView.OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
                if (p1 == EditorInfo.IME_ACTION_NEXT || p1 == EditorInfo.IME_ACTION_DONE) {
                    utils.hideKeyboard(baseActivity)
                    viewDataBinding.tvNewCarColor.requestFocus()
                    return true
                }
                return false
            }
        })
        viewDataBinding.inputNewCarIrCde.editText?.addTextChangedListener(
            NewCarEditTextChangeListener(
                2,
                viewDataBinding.inputNewCarThreePart
            )
        )
        viewDataBinding.inputNewCarThreePart.editText?.addTextChangedListener(
            NewCarEditTextChangeListener(
                3,
                viewDataBinding.inputNewCarKeyword
            )
        )
        viewDataBinding.inputNewCarKeyword.editText?.addTextChangedListener(
            NewCarEditTextChangeListener(
                1,
                viewDataBinding.inputNewCarTwoPart
            )
        )
        viewDataBinding.inputNewCarKeyword.editText?.filters = arrayOf(NumberExcludeFilter())
        viewDataBinding.inputNewCarTwoPart.editText?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 2)
                    utils.hideKeyboard(baseActivity)
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