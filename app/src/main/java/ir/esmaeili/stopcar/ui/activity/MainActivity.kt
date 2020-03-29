package ir.esmaeili.stopcar.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.MapsInitializer
import ir.esmaeili.stopcar.BR
import ir.esmaeili.stopcar.R
import ir.esmaeili.stopcar.databinding.ActivityMainBinding
import ir.esmaeili.stopcar.models.ActivityResultModel
import ir.esmaeili.stopcar.models.PermissionModel
import ir.esmaeili.stopcar.ui.base.BaseActivity
import ir.esmaeili.stopcar.utils.Constants
import ir.esmaeili.stopcar.utils.SimpleViewModelProviderFactory
import javax.inject.Inject


class MainActivity : BaseActivity<ActivityMainBinding, MainActivityViewModel>() {
    lateinit var factory: SimpleViewModelProviderFactory
        @Inject set
    lateinit var mContext: Context
        @Inject set

    private lateinit var mActivityViewModel: MainActivityViewModel

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): MainActivityViewModel {
        mActivityViewModel = ViewModelProvider(this, factory).get(MainActivityViewModel::class.java)
        return mActivityViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapsInitializer.initialize(mContext)
        initNightMode()
        initTypeface()
    }

    private fun initTypeface() {
        when (mActivityViewModel.getPreference(
            Constants.KEY_APP_TYPE_FACE,
            Constants.DEFAULT_TYPE_FACE_VALUE
        )) {
            "0" -> theme.applyStyle(R.style.MinisterFont, true)
            "1" -> theme.applyStyle(R.style.AloneFont, true)
            "2" -> theme.applyStyle(R.style.SwallowFont, true)
            "3" -> theme.applyStyle(R.style.ClovesFont, true)
        }
    }

    private fun isNightMode(): Boolean =
        mActivityViewModel.getPreference(
            Constants.KEY_DARK_NIGHT,
            Constants.NIGHT_MODE_DEFAULT_VALUE
        )

    private fun initNightMode() {
        if (isNightMode()) {
            setTheme(R.style.DarkTheme)
        } else {
            setTheme(R.style.AppTheme)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        sharedViewModel.setPermissionEvent(
            PermissionModel(
                requestCode,
                permissions.toList(),
                grantResults.toList()
            )
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        sharedViewModel.setActivityResultEvent(ActivityResultModel(requestCode, resultCode, data))
    }

    override fun showProgressbar() {
        viewDataBinding.progressbar.visibility = View.VISIBLE
    }

    override fun hideProgressbar() {
        viewDataBinding.progressbar.visibility = View.GONE
    }

    override fun onSupportNavigateUp(): Boolean {
        return sharedViewModel.navController?.value?.navigateUp() ?: false
    }
}
