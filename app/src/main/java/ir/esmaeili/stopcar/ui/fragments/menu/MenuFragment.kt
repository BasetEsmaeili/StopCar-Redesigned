package ir.esmaeili.stopcar.ui.fragments.menu

import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.Preference
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import ir.esmaeili.stopcar.R
import ir.esmaeili.stopcar.models.AlertView
import ir.esmaeili.stopcar.models.EventType
import ir.esmaeili.stopcar.models.Response
import ir.esmaeili.stopcar.ui.base.BasePreference
import ir.esmaeili.stopcar.utils.Constants
import ir.esmaeili.stopcar.utils.Utils
import javax.inject.Inject


class MenuFragment : BasePreference<MenuViewModel>(),
    DialogInterface.OnCancelListener {
    lateinit var factory: MenuViewModelProviderFactory
        @Inject set
    lateinit var utils: Utils
        @Inject set
    private lateinit var mViewModel: MenuViewModel

    override fun getViewModel(): MenuViewModel {
        mViewModel = ViewModelProvider(this, factory).get(MenuViewModel::class.java)
        return mViewModel
    }

    override fun onCancel(dialog: DialogInterface?) {
        dialog?.dismiss()
    }

    override fun onSharedPreferenceChangeListener(
        sharedPreferences: SharedPreferences?,
        key: String?
    ) {

        if (key.equals(Constants.KEY_DARK_NIGHT) || key == Constants.KEY_APP_TYPE_FACE) {
            baseActivity.restartActivity(false)
        } else if (key.equals(Constants.KEY_LOCALE)) {
            baseActivity.restartActivity(false)
        }
    }

    override fun onPreferenceTreeClick(preference: Preference): Boolean {
        return when (preference.key) {
            Constants.KEY_CREDITS -> {
                OssLicensesMenuActivity.setActivityTitle(utils.getString(R.string.label_credits))
                startActivity(Intent(baseActivity, OssLicensesMenuActivity::class.java))
                true
            }
            Constants.KEY_BACKUP_CLEAR_DATABASE -> {
                mViewModel.clearDatabase()
                true
            }
            Constants.KEY_CONTACT_ME -> {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.URL_MY_BLOG)))
                true
            }
            else -> false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

    private fun initObservers() {
        mViewModel.getClearDatabaseLiveData().observe(viewLifecycleOwner, Observer { response ->
            if (!response.hasBeenHandled)
                when (response.getContentIfNotHandled()) {
                    is Response.Loading -> {
                        sharedViewModel.setProgressbar(true)
                    }
                    is Response.Success -> {
                        sharedViewModel.setProgressbar(false)
                        baseActivity.restartActivity(false)
                    }
                    is Response.Error -> {
                        sharedViewModel.setProgressbar(false)
                        sharedViewModel.setAlertEvent(
                            AlertView.CustomToast(
                                EventType.ERROR,
                                utils.getString(R.string.label_error_title),
                                utils.getString(R.string.label_error_clear_data_base),
                                R.drawable.ic_error_white_24dp
                            )
                        )
                    }
                }
        })
    }
}