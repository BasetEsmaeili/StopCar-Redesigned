package ir.esmaeili.stopcar.ui.base;

import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import ir.esmaeili.stopcar.repository.RepositoryManager;

public abstract class BaseViewModel extends ViewModel {
    private RepositoryManager repositoryManager;
    private CompositeDisposable compositeDisposable;

    public BaseViewModel(RepositoryManager repositoryManager) {
        this.repositoryManager = repositoryManager;
        this.compositeDisposable = new CompositeDisposable();
    }

    public void editPreference(String key, Object value) {
        repositoryManager.editSharedPreference(key, value);
    }

    public <T> T getPreference(String key, T defaultValue) {
        return repositoryManager.getSharedPreference(key, defaultValue);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public RepositoryManager getRepositoryManager() {
        return repositoryManager;
    }
}
