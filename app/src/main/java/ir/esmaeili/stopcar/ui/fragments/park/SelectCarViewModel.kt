package ir.esmaeili.stopcar.ui.fragments.park

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber
import ir.esmaeili.stopcar.models.Car
import ir.esmaeili.stopcar.models.Response
import ir.esmaeili.stopcar.repository.RepositoryManager
import ir.esmaeili.stopcar.ui.base.BaseViewModel

class SelectCarViewModel(repositoryManager: RepositoryManager) :
    BaseViewModel(repositoryManager) {
    private val mutableLiveData: MutableLiveData<Response<List<Car>>> = MutableLiveData()
    fun getSavedCars(): MutableLiveData<Response<List<Car>>> {
        return mutableLiveData
    }

    private fun setSavedCars(carList: Response<List<Car>>) {
        this.mutableLiveData.value = carList
    }

    fun getSavedCarsDB() {
        compositeDisposable.add(repositoryManager.getCars()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .distinctUntilChanged()
            .doOnSubscribe { setSavedCars(Response.Loading()) }
            .subscribeWith(object : ResourceSubscriber<List<Car>>() {
                override fun onComplete() {
                    setSavedCars(Response.None())
                }

                override fun onNext(t: List<Car>?) {
                    setSavedCars(Response.Success(t!!))
                }

                override fun onError(t: Throwable?) {
                    setSavedCars(Response.Error(t!!))
                }
            })
        )
    }
}