package ir.esmaeili.stopcar.ui.fragments.newcar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import ir.esmaeili.stopcar.models.CarColor
import ir.esmaeili.stopcar.repository.RepositoryManager
import ir.esmaeili.stopcar.ui.base.BaseViewModel

class PickColorViewModel(repositoryManager: RepositoryManager) :
    BaseViewModel(repositoryManager) {
    private val colors: MutableLiveData<List<CarColor>> =
        MutableLiveData<List<CarColor>>().apply { value = getRepositoryManager().getCarColors() }

    fun getCarColors(): List<CarColor> {
        return colors.value!!
    }
}