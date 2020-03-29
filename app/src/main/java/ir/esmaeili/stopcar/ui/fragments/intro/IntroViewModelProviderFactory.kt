package ir.esmaeili.stopcar.ui.fragments.intro

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import ir.esmaeili.stopcar.repository.RepositoryManager
import javax.inject.Inject

class IntroViewModelProviderFactory @Inject constructor
    (
    private val repositoryManager: RepositoryManager,
    savedStateRegistryOwner: SavedStateRegistryOwner
) : AbstractSavedStateViewModelFactory(savedStateRegistryOwner, null) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return IntroViewModel(repositoryManager) as T
    }
}