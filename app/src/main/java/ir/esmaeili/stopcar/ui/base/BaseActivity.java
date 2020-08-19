package ir.esmaeili.stopcar.ui.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import ir.esmaeili.stopcar.R;
import ir.esmaeili.stopcar.databinding.ViewCustomMessageBinding;
import ir.esmaeili.stopcar.models.AlertView;
import ir.esmaeili.stopcar.ui.activity.EventMessageViewModel;
import ir.esmaeili.stopcar.ui.activity.MainActivity;
import ir.esmaeili.stopcar.utils.Constants;
import ir.esmaeili.stopcar.utils.LocaleWrapper;
import ir.esmaeili.stopcar.utils.SharedViewModel;
import ir.esmaeili.stopcar.utils.SimpleViewModelProviderFactory;
import ir.esmaeili.stopcar.utils.Utils;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends DaggerAppCompatActivity {
    @Inject
    Utils utils;
    @Inject
    Context context;
    @Inject
    SimpleViewModelProviderFactory factory;

    private SharedViewModel mSharedViewModel;
    private V mViewModel;
    private T mViewDataBinding;
    private ViewCustomMessageBinding customMessageBinding;
    private EventMessageViewModel customMessageViewModel;

    public abstract int getBindingVariable();

    public abstract @LayoutRes
    int getLayoutId();

    public abstract V getViewModel();


    public abstract void showProgressbar();

    public abstract void hideProgressbar();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleWrapper.onAttach(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(context, R.xml.preferences, false);
        this.mSharedViewModel = new ViewModelProvider(this, factory).get(SharedViewModel.class);
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        customMessageBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.view_custom_message, null, false);
        customMessageViewModel = new EventMessageViewModel();
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.setLifecycleOwner(this);
        mViewDataBinding.executePendingBindings();
        observeSharedViewModel();
    }

    private void observeSharedViewModel() {
        mSharedViewModel.getAlertEvent().observe(this, alertView -> {
            if (alertView instanceof AlertView.CustomToast) {
                initCustomToast((AlertView.CustomToast) alertView);
            } else if (alertView instanceof AlertView.AlertDialog) {
                initAlertDialog((AlertView.AlertDialog) alertView);
            }
        });
        mSharedViewModel.getProgressbar().observe(this, progressbar -> {
            if (progressbar)
                showProgressbar();
            else
                hideProgressbar();
        });
    }

    public SharedViewModel getSharedViewModel() {
        return mSharedViewModel;
    }

    private void initCustomToast(AlertView.CustomToast alertView) {
        Drawable drawable = utils.getDrawable(alertView.getIcon());
        drawable.setColorFilter(new PorterDuffColorFilter(utils.getColor(R.color.color_white), PorterDuff.Mode.SRC_ATOP));
        switch (alertView.getEventType()) {
            case ERROR:
                customMessageViewModel.setBackground(utils.getColor(R.color.color_error_message));
                break;
            case SUCCESS:
                customMessageViewModel.setBackground(utils.getColor(R.color.color_success_message));
                break;
            case MESSAGE:
                customMessageViewModel.setBackground(utils.getColor(R.color.color_message_message));
                break;
            case ALERT:
                customMessageViewModel.setBackground(utils.getColor(R.color.color_alert_message));
                break;
        }
        customMessageViewModel.setIcon(drawable);
        customMessageViewModel.setTitle(alertView.getTitle());
        customMessageViewModel.setDescription(alertView.getDescription());
        customMessageBinding.setViewModel(customMessageViewModel);
        switch (getCurrentTypeface()) {
            case Constants.VAZIR_FONT_NAME:
                customMessageBinding.tvEventTitle.setTypeface(utils.setTypeface(Constants.VAZIR_FONT_NAME));
                customMessageBinding.tvEventDescription.setTypeface(utils.setTypeface(Constants.VAZIR_FONT_NAME));
                break;
            case Constants.TANHA_FONT_NAME:
                customMessageBinding.tvEventTitle.setTypeface(utils.setTypeface(Constants.TANHA_FONT_NAME));
                customMessageBinding.tvEventDescription.setTypeface(utils.setTypeface(Constants.TANHA_FONT_NAME));
                break;
            case Constants.PARASTOO_FONT_NAME:
                customMessageBinding.tvEventTitle.setTypeface(utils.setTypeface(Constants.PARASTOO_FONT_NAME));
                customMessageBinding.tvEventDescription.setTypeface(utils.setTypeface(Constants.PARASTOO_FONT_NAME));
                break;
            case Constants.MIKHAK_FONT_NAME:
                customMessageBinding.tvEventTitle.setTypeface(utils.setTypeface(Constants.MIKHAK_FONT_NAME));
                customMessageBinding.tvEventDescription.setTypeface(utils.setTypeface(Constants.MIKHAK_FONT_NAME));
                break;
        }
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(customMessageBinding.getRoot());
        toast.show();
    }

    private void initAlertDialog(AlertView.AlertDialog alertDialogExtra) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(alertDialogExtra.getTitle())
                .setMessage(alertDialogExtra.getMessage())
                .setCancelable(alertDialogExtra.isCancelable())
                .setPositiveButton(alertDialogExtra.getPositiveText(), alertDialogExtra.getClickListener());
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(dialog -> alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(utils.getColor(R.color.colorAccent)));
        alertDialog.show();
        TextView alertMessage = alertDialog.getWindow().findViewById(android.R.id.message);
        if (alertMessage != null) {
            alertMessage.setTypeface(utils.setTypeface(getCurrentTypeface()));
        }
    }

    private String getCurrentTypeface() {
        switch (mViewModel.getPreference(Constants.KEY_APP_TYPE_FACE, Constants.DEFAULT_TYPE_FACE_VALUE)) {
            case "0":
                return Constants.VAZIR_FONT_NAME;
            case "1":
                return Constants.TANHA_FONT_NAME;
            case "2":
                return Constants.PARASTOO_FONT_NAME;
            case "3":
                return Constants.MIKHAK_FONT_NAME;
        }
        return Constants.VAZIR_FONT_NAME;
    }

    public void restartActivity(boolean isNewTask) {
        if (isNewTask) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
            this.startActivity(intent);
            this.finish();
            Runtime.getRuntime().exit(0);
        } else {
            Intent intent = this.getIntent();
            this.finish();
            startActivity(intent);
        }
    }


    public T getViewDataBinding() {
        return mViewDataBinding;
    }
}
