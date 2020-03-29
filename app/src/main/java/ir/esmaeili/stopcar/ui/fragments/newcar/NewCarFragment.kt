package ir.esmaeili.stopcar.ui.fragments.newcar

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ir.esmaeili.stopcar.BR
import ir.esmaeili.stopcar.R
import ir.esmaeili.stopcar.databinding.FragmentNewCarBinding
import ir.esmaeili.stopcar.models.AlertView
import ir.esmaeili.stopcar.models.CarColor
import ir.esmaeili.stopcar.models.EventType
import ir.esmaeili.stopcar.models.Response
import ir.esmaeili.stopcar.ui.base.BaseFragment
import ir.esmaeili.stopcar.utils.*
import javax.inject.Inject


class NewCarFragment : BaseFragment<FragmentNewCarBinding, NewCarViewModel>() {

    lateinit var factory: NewCarViewModelProviderFactory
        @Inject set

    lateinit var utils: Utils
        @Inject set

    private lateinit var mViewModel: NewCarViewModel


    override fun getLayoutId(): Int {
        return R.layout.fragment_new_car
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): NewCarViewModel {
        mViewModel = ViewModelProvider(this, factory).get(NewCarViewModel::class.java)
        return mViewModel
    }

    override fun onViewCreated(p0: View, savedInstanceState: Bundle?) {
        super.onViewCreated(p0, savedInstanceState)
        viewDataBinding.sharedViewModel = sharedViewModel
        viewDataBinding.viewModel = mViewModel
        setupToolbar()
        editTextEvents()
        selectColorButton()
        initObservers()
        initSavedStateObservers()
    }

    private fun initObservers() {
        mViewModel.getNewCarNavigator().observe(viewLifecycleOwner, Observer { navigator ->
            when (navigator) {
                NewCarNavigator.MAIN -> {
                    findNavController().navigate(R.id.action_fragmentNewCar_to_MainFragment)
                }
            }
        })
        mViewModel.getNewCarLiveData().observe(viewLifecycleOwner, Observer {
            when (it) {
                is Response.Loading -> {
                    sharedViewModel.setProgressbar(true)
                }
                is Response.Success -> {
                    sharedViewModel.setProgressbar(false)
                    sharedViewModel.setAlertEvent(
                        AlertView.CustomToast(
                            EventType.SUCCESS, utils
                                .getString(R.string.label_success_title),
                            utils.getString(R.string.label_success_description_data_base),
                            R.drawable.ic_done_gray_24dp
                        )
                    )
                    navigate()
                }
                is Response.Error -> {
                    sharedViewModel.setProgressbar(false)
                    sharedViewModel.setAlertEvent(
                        AlertView.CustomToast(
                            EventType.ERROR,
                            utils.getString(R.string.label_error_title),
                            utils.getString(R.string.label_error_save_car)
                            , R.drawable.ic_error_white_24dp
                        )
                    )
                }
            }
        })
        sharedViewModel.selectedCarColor.observe(viewLifecycleOwner, Observer { color ->
            mViewModel.setCarColor(color.color)
            changeCarDrawableColor(color)
        })
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

    private fun navigate() {
        val handler = Handler()
        val post = Runnable {
            mViewModel.setNewCarNavigator(NewCarNavigator.MAIN)
        }
        handler.postDelayed(post, Constants.SPLASH_DURATION)
    }

    private fun selectColorButton() {
        viewDataBinding.tvNewCarColor.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentNewCar_to_dialogSelectCarColor)
        }
    }

    private fun changeCarDrawableColor(carColor: CarColor) {
        if (carColor.color.isNotEmpty()) {
            viewDataBinding.tvNewCarColor.setTextColor(Color.parseColor(carColor.color))
            viewDataBinding.tvNewCarColor.compoundDrawablesRelative.forEach { drawable ->
                try {
                    drawable.colorFilter = PorterDuffColorFilter(
                        Color.parseColor(carColor.color),
                        PorterDuff.Mode.SRC_IN
                    )
                } catch (e: Exception) {

                }
            }
        }
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
}