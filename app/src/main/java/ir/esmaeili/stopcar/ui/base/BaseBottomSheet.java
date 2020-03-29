package ir.esmaeili.stopcar.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import ir.esmaeili.stopcar.utils.SharedViewModel;
import ir.esmaeili.stopcar.utils.SimpleViewModelProviderFactory;

public abstract class BaseBottomSheet<T extends ViewDataBinding, V extends BaseViewModel> extends DaggerBottomSheet {
    private BaseActivity mActivity;
    private View mRootView;
    private T mViewDataBinding;
    private V mViewModel;
    private SharedViewModel mSharedViewModel;
    @Inject
    SimpleViewModelProviderFactory factory;

    @LayoutRes
    public abstract int getLayoutId();


    public abstract int getBindingVariable();


    public abstract V getViewModel();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = getViewModel();
        mSharedViewModel = new ViewModelProvider(requireActivity(), factory).get(SharedViewModel.class);
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            this.mActivity = (BaseActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mRootView = mViewDataBinding.getRoot();
        return mRootView;
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.setLifecycleOwner(this);
        mViewDataBinding.executePendingBindings();
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }


    public View getRootView() {
        return mRootView;
    }


    public SharedViewModel getSharedViewModel() {
        return mSharedViewModel;
    }
}