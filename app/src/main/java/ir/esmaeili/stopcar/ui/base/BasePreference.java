package ir.esmaeili.stopcar.ui.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import ir.esmaeili.stopcar.R;
import ir.esmaeili.stopcar.ui.activity.MainActivity;
import ir.esmaeili.stopcar.utils.Constants;
import ir.esmaeili.stopcar.utils.SharedViewModel;
import ir.esmaeili.stopcar.utils.SimpleViewModelProviderFactory;
import ir.esmaeili.stopcar.utils.Utils;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public abstract class BasePreference<V extends BaseViewModel> extends DaggerPreferenceFragmentCompat {

    @Inject
    Utils utils;
    @Inject
    SimpleViewModelProviderFactory factory;

    private SharedViewModel mSharedViewModel;

    private V mViewModel;

    private BaseActivity baseActivity;

    public abstract V getViewModel();

    public abstract void onSharedPreferenceChangeListener(SharedPreferences sharedPreferences, String key);

    private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = this::onSharedPreferenceChangeListener;


    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    public BaseActivity getBaseActivity() {
        return baseActivity;
    }


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        super.onCreatePreferences(savedInstanceState, rootKey);
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    private boolean isNightMode() {
        return mViewModel.getPreference(Constants.KEY_DARK_NIGHT, Constants.NIGHT_MODE_DEFAULT_VALUE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel();
        mSharedViewModel = new ViewModelProvider(requireActivity(), factory).get(SharedViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (view != null)
            if (isNightMode()) {
                applyStyle(R.style.DarkTheme);
                view.setBackgroundColor(utils.getColor(R.color.color_night_background));
            } else {
                applyStyle(R.style.AppTheme);
                view.setBackgroundColor(ContextCompat.getColor(baseActivity, R.color.color_white));
            }
        return view;
    }

    private void applyStyle(@StyleRes int style) {
        getBaseActivity().getTheme().applyStyle(style, true);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            this.baseActivity = (BaseActivity) context;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    @Override
    public void setDivider(Drawable divider) {
        if (isNightMode())
            super.setDivider(new ColorDrawable(utils.getColor(R.color.color_preference_divider)));
        else
            super.setDivider(new ColorDrawable(utils.getColor(R.color.colorMain)));
    }

    public SharedViewModel getSharedViewModel() {
        return mSharedViewModel;
    }
}
