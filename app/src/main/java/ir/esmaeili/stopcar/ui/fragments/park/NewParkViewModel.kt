package ir.esmaeili.stopcar.ui.fragments.park

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.google.android.gms.maps.model.LatLng
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import ir.esmaeili.stopcar.models.AddressGeoCoder
import ir.esmaeili.stopcar.models.History
import ir.esmaeili.stopcar.models.Response
import ir.esmaeili.stopcar.repository.RepositoryManager
import ir.esmaeili.stopcar.ui.base.BaseViewModel
import ir.esmaeili.stopcar.utils.Constants
import ir.esmaeili.stopcar.utils.SingleEvent
import java.util.concurrent.TimeUnit

class NewParkViewModel(
    repositoryManager: RepositoryManager,
    private val savedStateHandle: SavedStateHandle
) :
    BaseViewModel(repositoryManager) {

    private val geoCoderDisposable = CompositeDisposable()

    private val locationButtonState: MutableLiveData<Boolean> =
        MutableLiveData(false)

    private val addressGeoCoder: MutableLiveData<Response<AddressGeoCoder>> =
        MutableLiveData()

    private val history: MutableLiveData<SingleEvent<Response<Boolean>>> = MutableLiveData()

    private val eventHandler: MutableLiveData<SingleEvent<Int>> = MutableLiveData()

    fun isMapFragmentAttached(): Boolean {
        return savedStateHandle.contains(Constants.KEY_MAP_FRAGMENT)
    }

    fun setMapFragmentAttached() {
        savedStateHandle.set(Constants.KEY_MAP_FRAGMENT, true)
    }

    fun getEventHandler(): MutableLiveData<SingleEvent<Int>> {
        return this.eventHandler
    }

    fun setEventHandler(event: Int) {
        this.eventHandler.value = SingleEvent(event)
    }

    fun getHistory(): MutableLiveData<SingleEvent<Response<Boolean>>> {
        return this.history
    }

    fun setHistory(response: SingleEvent<Response<Boolean>>) {
        this.history.value = response
    }

    fun setLocationButtonState(state: Boolean) {
        locationButtonState.value = state
    }

    fun getLocationButtonState(): MutableLiveData<Boolean> {
        return locationButtonState
    }

    fun getReverseGeoCoderAddress(): LiveData<Response<AddressGeoCoder>> {
        return addressGeoCoder
    }

    fun setReverseGeoCoderAddress(response: Response<AddressGeoCoder>) {
        this.addressGeoCoder.value = response
    }


    fun insertHistory(history: History) {
        val observer = object : DisposableSingleObserver<Boolean>() {
            override fun onSuccess(t: Boolean) {
                setHistory(SingleEvent(Response.Success(t)))
            }

            override fun onError(e: Throwable) {
                setHistory(SingleEvent(Response.Error(e)))
            }
        }
        compositeDisposable.add(
            repositoryManager
                .insertHistory(history)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { setHistory(SingleEvent(Response.Loading())) }
                .subscribeWith(observer))
    }

    fun getLocationGeoAddress(latLng: LatLng) {
        val observer = object : DisposableSingleObserver<AddressGeoCoder>() {
            override fun onSuccess(t: AddressGeoCoder) {
                setReverseGeoCoderAddress(Response.Success(t))
            }

            override fun onError(e: Throwable) {
                setReverseGeoCoderAddress(Response.Error(e))
            }
        }

        geoCoderDisposable
            .add(repositoryManager.geoCoderAddress(
                latLng.latitude.toString(),
                latLng.longitude.toString(),
                repositoryManager.getNeshanApiKey()
            ).delaySubscription(Constants.DELAY_GEO_ADDRESS_REQUEST, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe { setReverseGeoCoderAddress(Response.Loading()) }
                .subscribeWith(observer))
    }

    fun cancelReverseGeoCoderRequest() {
        if (!geoCoderDisposable.isDisposed)
            geoCoderDisposable.clear()
    }

    override fun onCleared() {
        geoCoderDisposable.dispose()
        super.onCleared()
    }
}