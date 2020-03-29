package ir.esmaeili.stopcar.ui.fragments.intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber
import ir.esmaeili.stopcar.models.Response
import ir.esmaeili.stopcar.models.Slide
import ir.esmaeili.stopcar.repository.RepositoryManager
import ir.esmaeili.stopcar.ui.base.BaseViewModel

class IntroViewModel(repositoryManager: RepositoryManager) : BaseViewModel(repositoryManager) {

    private val items: LiveData<List<Slide>> =
        MutableLiveData<List<Slide>>().apply { value = getRepositoryManager().getIntroItems() }
    private val carCountLiveData: MutableLiveData<Response<Long>> = MutableLiveData()

    private val eventHandler: MutableLiveData<Int> = MutableLiveData()

    fun setEvent(value: Int) {
        eventHandler.value = value
    }

    private fun setCarCountLiveData(state: Response<Long>) {
        this.carCountLiveData.value = state
    }

    fun getCarCountLiveData(): LiveData<Response<Long>> {
        return this.carCountLiveData
    }

    fun getEvent(): MutableLiveData<Int> {
        return eventHandler
    }

    fun getIntroItems(): LiveData<List<Slide>> {
        return items
    }

    fun getCarCount() {
        val subscriber = object : ResourceSubscriber<Long>() {
            override fun onComplete() {
                setCarCountLiveData(Response.None())
            }

            override fun onNext(t: Long) {
                if (t <= 0L) {
                    setCarCountLiveData(Response.Empty())
                } else {
                    setCarCountLiveData(Response.Success(t))
                }
            }

            override fun onError(t: Throwable) {
                setCarCountLiveData(Response.Error(t))
            }
        }
        compositeDisposable.add(
            repositoryManager.getCarCount()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .distinctUntilChanged()
                .subscribeWith(subscriber)
        )
    }

}