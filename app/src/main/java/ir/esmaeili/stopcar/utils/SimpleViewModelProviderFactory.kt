package ir.esmaeili.stopcar.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ir.esmaeili.stopcar.repository.RepositoryManager
import ir.esmaeili.stopcar.ui.activity.MainActivityViewModel
import javax.inject.Inject

class SimpleViewModelProviderFactory @Inject constructor(private val repositoryManager: RepositoryManager) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SharedViewModel::class.java) -> SharedViewModel() as T
            modelClass.isAssignableFrom(MainActivityViewModel::class.java) -> MainActivityViewModel(
                repositoryManager
            ) as T
            else -> throw IllegalArgumentException("Unknown class name ${modelClass.name}")
        }
    }
}