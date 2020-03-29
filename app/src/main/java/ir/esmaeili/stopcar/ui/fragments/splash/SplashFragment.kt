package ir.esmaeili.stopcar.ui.fragments.splash

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.room.EmptyResultSetException
import ir.esmaeili.stopcar.BR
import ir.esmaeili.stopcar.R
import ir.esmaeili.stopcar.databinding.FragmentSplashBinding
import ir.esmaeili.stopcar.models.Response
import ir.esmaeili.stopcar.models.SplashNavigateType
import ir.esmaeili.stopcar.ui.base.BaseFragment
import ir.esmaeili.stopcar.utils.Constants
import javax.inject.Inject


class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>() {

    lateinit var viewModelProviderFactory: SplashViewModelProviderFactory
        @Inject set

    lateinit var mViewModel: SplashViewModel

    override fun getLayoutId(): Int {
        return R.layout.fragment_splash
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): SplashViewModel {
        mViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(SplashViewModel::class.java)
        return mViewModel
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animateSplashIcon()
        mViewModel.getNavigation().observe(viewLifecycleOwner, Observer {
            when (it) {
                SplashNavigateType.NEW_CAR -> {
                    findNavController().navigate(R.id.action_fragmentSplash_to_fragmentNewCar)
                }
                SplashNavigateType.INTRO -> {
                    findNavController().navigate(R.id.navigate_splash_intro)
                }

                SplashNavigateType.MAIN -> {
                    findNavController().navigate(R.id.action_fragmentSplash_to_fragmentMain)
                }
            }
        })
    }


    private fun animateSplashIcon() {
        viewDataBinding.imgSplashIcon.animate().translationY(-20f).withEndAction {
            splashHandlerDone()
        }
    }

    private fun splashHandlerDone() {
        viewDataBinding.tvSplashAppName.visibility = View.VISIBLE
        viewDataBinding.tvSplashAppName.animate().translationY(-30f).withEndAction {
            viewDataBinding.progressbarSplash.visibility = View.VISIBLE
            viewDataBinding.progressbarSplash.animate().translationY(-20f).withEndAction {
                animationEnd()
            }
        }
    }


    private fun animationEnd() {
        mViewModel.getCarCount()
        mViewModel.getCarCountLiveData().observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Response.Success -> {
                    if (mViewModel.isIntroPageFinish())
                        mViewModel.getNavigation().value = SplashNavigateType.MAIN
                    else
                        mViewModel.getNavigation().value = SplashNavigateType.INTRO
                }
                is Response.Error -> {
                    if (response.throwable is EmptyResultSetException) {
                        mViewModel.getNavigation().value = SplashNavigateType.NEW_CAR
                    }
                }
                is Response.Empty -> {
                    if (mViewModel.isIntroPageFinish())
                        mViewModel.getNavigation().value = SplashNavigateType.NEW_CAR
                    else
                        mViewModel.getNavigation().value = SplashNavigateType.INTRO
                }
            }

        })
    }


}