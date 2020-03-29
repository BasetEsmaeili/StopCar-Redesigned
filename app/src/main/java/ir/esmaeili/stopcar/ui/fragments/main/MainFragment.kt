package ir.esmaeili.stopcar.ui.fragments.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ir.esmaeili.stopcar.BR
import ir.esmaeili.stopcar.R
import ir.esmaeili.stopcar.databinding.FragmentMainBinding
import ir.esmaeili.stopcar.ui.base.BaseFragment
import ir.esmaeili.stopcar.utils.setupWithNavController
import javax.inject.Inject

class MainFragment : BaseFragment<FragmentMainBinding, MainFragmentViewModel>() {


    lateinit var factory: MainViewModelProviderFactory
        @Inject set
    private lateinit var mFragmentViewModel: MainFragmentViewModel

    override fun getLayoutId(): Int {
        return R.layout.fragment_main
    }

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): MainFragmentViewModel {
        mFragmentViewModel =
            ViewModelProvider(requireActivity(), factory).get(MainFragmentViewModel::class.java)
        return mFragmentViewModel
    }

    override fun onViewCreated(p0: View, savedInstanceState: Bundle?) {
        super.onViewCreated(p0, savedInstanceState)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    private fun setupBottomNavigationBar() {
        val navGraphIds =
            listOf(
                R.navigation.history,
                R.navigation.new_park,
                R.navigation.manage_cars,
                R.navigation.menu
            )
        val controller = viewDataBinding.navigationMain.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = childFragmentManager,
            containerId = R.id.container_bottom_navigation,
            intent = baseActivity.intent
        )
        controller.observe(viewLifecycleOwner, Observer {
            sharedViewModel.setNavController(it)
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupBottomNavigationBar()
    }
}