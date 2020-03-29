package ir.esmaeili.stopcar.ui.base;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceFragmentCompat;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import dagger.android.support.AndroidSupportInjection;

public abstract class DaggerPreferenceFragmentCompat extends PreferenceFragmentCompat implements HasAndroidInjector {

    @Inject
    DispatchingAndroidInjector<Object> androidInjector;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }
}
