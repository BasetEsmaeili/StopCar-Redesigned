package ir.esmaeili.stopcar.ui.base;

import android.content.Context;

import androidx.annotation.NonNull;

import com.andrefrsousa.superbottomsheet.SuperBottomSheetFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;
import dagger.android.support.AndroidSupportInjection;

public abstract class DaggerBottomSheet extends SuperBottomSheetFragment implements HasAndroidInjector {
    @Inject
    DispatchingAndroidInjector<Object> androidInjector;

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
