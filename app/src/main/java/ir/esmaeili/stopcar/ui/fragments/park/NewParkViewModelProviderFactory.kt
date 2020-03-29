package ir.esmaeili.stopcar.ui.fragments.park

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import ir.esmaeili.stopcar.repository.RepositoryManager
import javax.inject.Inject

class NewParkViewModelProviderFactory @Inject constructor(
    private val repositoryManager: RepositoryManager
    , owner: SavedStateRegistryOwner
) : AbstractSavedStateViewModelFactory
    (owner, null) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return NewParkViewModel(repositoryManager, handle) as T
    }
}