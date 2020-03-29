package ir.esmaeili.stopcar.ui.fragments.intro

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import ir.esmaeili.stopcar.BR
import ir.esmaeili.stopcar.R
import ir.esmaeili.stopcar.databinding.FragmentIntroBinding
import ir.esmaeili.stopcar.models.Response
import ir.esmaeili.stopcar.ui.base.BaseFragment
import ir.esmaeili.stopcar.utils.Constants
import ir.esmaeili.stopcar.utils.Utils
import javax.inject.Inject

class IntroFragment : BaseFragment<FragmentIntroBinding, IntroViewModel>() {
    private lateinit var callBack: ViewPager2.OnPageChangeCallback
    private lateinit var mViewModel: IntroViewModel
    lateinit var factory: IntroViewModelProviderFactory
        @Inject set

    lateinit var utils: Utils
        @Inject set

    override fun getLayoutId(): Int {
        return R.layout.fragment_intro
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): IntroViewModel {
        mViewModel = ViewModelProvider(this, factory).get(IntroViewModel::class.java)
        return mViewModel

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding.viewModel = mViewModel
        initObservers()
        addPagerChangeListener()
    }

    private fun initObservers() {
        mViewModel.getCarCount()
        mViewModel.getEvent().observe(viewLifecycleOwner, Observer {
            when (it) {
                Constants.EVENT_NEXT_ITEM -> {
                    pagerNextItem()
                }
            }
        })
    }

    private fun addPagerChangeListener() {
        viewDataBinding.indicatorIntro.count = mViewModel.getIntroItems().value?.size!!
        viewDataBinding.indicatorIntro.selection = 0
        callBack = object : ViewPager2
        .OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewDataBinding.indicatorIntro.selection = position
                if (position == mViewModel.getIntroItems().value?.size?.minus(1)) {
                    viewDataBinding.btnIntroNext.setOnClickListener(null)
                    viewDataBinding.btnIntroNext.text = utils.getString(R.string.label_lets_go)
                    viewDataBinding.btnIntroNext.setOnClickListener {
                        finishIntro()
                    }
                } else {
                    viewDataBinding.btnIntroNext.text = utils.getString(R.string.label_next)
                    viewDataBinding.btnIntroNext.setOnClickListener(null)
                    viewDataBinding.btnIntroNext.setOnClickListener {
                        pagerNextItem()
                    }
                }
            }
        }
        viewDataBinding.pagerIntro.registerOnPageChangeCallback(callBack)
    }

    fun finishIntro() {
        mViewModel.editPreference(Constants.INTRO_PREF_KEY, "IntroDone")
        mViewModel.getCarCountLiveData().observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Response.Empty -> {
                    findNavController().navigate(R.id.action_fragmentIntro_to_fragmentNewCar)
                }
                is Response.Success -> {
                    findNavController().navigate(R.id.action_fragmentIntro_to_fragmentMain)
                }
                is Response.Error -> {
                    findNavController().navigate(R.id.action_fragmentSplash_to_fragmentNewCar)
                }
            }
        })
    }

    fun pagerNextItem() {
        if (viewDataBinding.pagerIntro.currentItem != mViewModel.getIntroItems().value?.size?.minus(
                1
            )
        ) {
            viewDataBinding.pagerIntro.setCurrentItem(
                viewDataBinding.pagerIntro.currentItem.plus(
                    1
                ), true
            )
        }

    }

    override fun onDestroy() {
        viewDataBinding.pagerIntro.unregisterOnPageChangeCallback(callBack)
        super.onDestroy()
    }

}