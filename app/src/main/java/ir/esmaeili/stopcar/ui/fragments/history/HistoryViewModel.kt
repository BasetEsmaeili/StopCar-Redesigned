package ir.esmaeili.stopcar.ui.fragments.history

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber
import ir.esmaeili.stopcar.models.HistoryJoinCar
import ir.esmaeili.stopcar.models.Response
import ir.esmaeili.stopcar.repository.RepositoryManager
import ir.esmaeili.stopcar.ui.base.BaseViewModel
import ir.esmaeili.stopcar.utils.Constants

class HistoryViewModel(
    repositoryManager: RepositoryManager,
    private val stateHandle: SavedStateHandle
) :
    BaseViewModel(repositoryManager) {
    private val carHistory: MutableLiveData<Response<List<HistoryJoinCar>>> = MutableLiveData()
    fun getHistory(): MutableLiveData<Response<List<HistoryJoinCar>>> {
        return this.carHistory
    }

    private fun setHistory(response: Response<List<HistoryJoinCar>>) {
        this.carHistory.value = response
    }

    fun saveHistoryState(list: List<HistoryJoinCar>) {
        this.stateHandle.set(Constants.KEY_HISTORY_LIST_SATE, list)
    }

    fun clearHistoryState() {
        if (stateHandle.contains(Constants.KEY_HISTORY_LIST_SATE)) {
            stateHandle.remove<List<HistoryJoinCar>>(Constants.KEY_HISTORY_LIST_SATE)
        } else {
            return
        }
    }

    fun getHistoryState(): LiveData<List<HistoryJoinCar>> {
        return stateHandle.getLiveData(Constants.KEY_HISTORY_LIST_SATE)
    }

    fun isStateSaved(): Boolean {
        return stateHandle.contains(Constants.KEY_HISTORY_LIST)
    }

    fun getHistoryScrollPosition(): LiveData<Parcelable> {
        return stateHandle.getLiveData(Constants.KEY_HISTORY_LIST_SATE)
    }

    fun setHistoryScrollPosition(state: Parcelable) {
        stateHandle.set(Constants.KEY_HISTORY_LIST_SATE, state)
    }

    fun getHistoryList() {
        compositeDisposable.add(
            repositoryManager.getHistoryList().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .distinctUntilChanged()
                .doOnSubscribe {
                    setHistory(Response.Loading())
                }
                .subscribeWith(object : ResourceSubscriber<List<HistoryJoinCar>>() {
                    override fun onComplete() {
                        setHistory(Response.None())
                    }

                    override fun onNext(t: List<HistoryJoinCar>) {
                        if (t.isNotEmpty()) {
                            setHistory(Response.Success(t))
                        } else {
                            setHistory(Response.Empty())
                        }
                    }

                    override fun onError(t: Throwable?) {
                        setHistory(Response.Error(t!!))
                    }
                })
        )
    }

}