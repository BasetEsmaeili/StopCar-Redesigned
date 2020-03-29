package ir.esmaeili.stopcar.ui.fragments.cars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableMaybeObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber
import ir.esmaeili.stopcar.models.Car
import ir.esmaeili.stopcar.models.Response
import ir.esmaeili.stopcar.repository.RepositoryManager
import ir.esmaeili.stopcar.ui.base.BaseViewModel
import ir.esmaeili.stopcar.utils.SingleEvent

class ManageCarsViewModel(
    repositoryManager: RepositoryManager
) :
    BaseViewModel(repositoryManager) {
    private val getCars: MutableLiveData<SingleEvent<Response<List<Car>>>> = MutableLiveData()
    private val eventHandler: MutableLiveData<SingleEvent<Int>> = MutableLiveData()
    private val deleteCar: MutableLiveData<SingleEvent<Response<Boolean>>> = MutableLiveData()
    private val carCount: MutableLiveData<SingleEvent<Response<Long>>> = MutableLiveData()

    private fun setCarCount(count: Response<Long>) {
        this.carCount.value = SingleEvent(count)
    }

    fun getCarsCountLiveData(): LiveData<SingleEvent<Response<Long>>> {
        return this.carCount
    }

    private fun setDeleteCarState(state: Response<Boolean>) {
        this.deleteCar.value = SingleEvent(state)
    }

    fun getDeleteCar(): LiveData<SingleEvent<Response<Boolean>>> {
        return this.deleteCar
    }

    private fun setCarsList(response: Response<List<Car>>) {
        this.getCars.value = SingleEvent(response)
    }

    fun getCarsList(): LiveData<SingleEvent<Response<List<Car>>>> {
        return this.getCars
    }

    fun setEventHandler(value: Int) {
        this.eventHandler.value = SingleEvent(value)
    }

    fun getEventHandler(): LiveData<SingleEvent<Int>> {
        return this.eventHandler
    }

    fun getCars() {
        compositeDisposable.add(repositoryManager.getCars()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { setCarsList(Response.Loading()) }
            .subscribeWith(object : ResourceSubscriber<List<Car>>() {
                override fun onComplete() {
                    setCarsList(Response.None())
                }

                override fun onNext(cars: List<Car>) {
                    if (cars.isEmpty()) {
                        setCarsList(Response.Empty())
                    } else {
                        setCarsList(Response.Success(cars))
                    }
                }

                override fun onError(t: Throwable?) {
                    setCarsList(Response.Error(t!!))
                }
            })
        )

    }

    fun deleteCar(carID: Long) {
        val observer = object : DisposableSingleObserver<Boolean>() {
            override fun onSuccess(t: Boolean) {
                setDeleteCarState(Response.Success(t))
            }

            override fun onError(e: Throwable) {
                setDeleteCarState(Response.Error(e))
            }
        }
        compositeDisposable.add(
            repositoryManager.deleteCarWithHistory(carID)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { setDeleteCarState(Response.Loading()) }
                .subscribeWith(observer)
        )
    }

    fun getCarsCount() {
        val observer = object : ResourceSubscriber<Long>() {
            override fun onNext(count: Long) {
                if (count <= 0L) {
                    setCarCount(Response.Empty())
                }
            }

            override fun onError(t: Throwable) {
                setCarCount(Response.Error(t))
            }

            override fun onComplete() {
                setCarCount(Response.None())
            }
        }
        compositeDisposable.add(
            repositoryManager.getCarCount().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .distinctUntilChanged()
                .subscribeWith(observer)
        )
    }
}