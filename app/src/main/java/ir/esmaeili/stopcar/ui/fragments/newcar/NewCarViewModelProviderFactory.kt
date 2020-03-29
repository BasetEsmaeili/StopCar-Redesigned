package ir.esmaeili.stopcar.ui.fragments.newcar

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.savedstate.SavedStateRegistryOwner
import ir.esmaeili.stopcar.repository.RepositoryManager
import javax.inject.Inject

class NewCarViewModelProviderFactory @Inject constructor(
    private val
    repositoryManager: RepositoryManager
    , owner: SavedStateRegistryOwner
) : AbstractSavedStateViewModelFactory(owner, null) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return NewCarViewModel(repositoryManager, handle) as T
    }
}