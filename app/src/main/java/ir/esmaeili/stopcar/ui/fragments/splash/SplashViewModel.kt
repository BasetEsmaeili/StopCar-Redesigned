package ir.esmaeili.stopcar.ui.fragments.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.ResourceSubscriber
import ir.esmaeili.stopcar.models.Response
import ir.esmaeili.stopcar.models.SplashNavigateType
import ir.esmaeili.stopcar.repository.RepositoryManager
import ir.esmaeili.stopcar.ui.base.BaseViewModel

class SplashViewModel(repositoryManager: RepositoryManager) :
    BaseViewModel(repositoryManager) {

    private val navigation: MutableLiveData<SplashNavigateType> = MutableLiveData()
    private val carCountLiveData: MutableLiveData<Response<Long>> = MutableLiveData()


    private fun setCarCountLiveData(state: Response<Long>) {
        this.carCountLiveData.value = state
    }

    fun getCarCountLiveData(): LiveData<Response<Long>> {
        return this.carCountLiveData
    }

    fun getNavigation(): MutableLiveData<SplashNavigateType> {
        return navigation
    }


    fun isIntroPageFinish() = repositoryManager.isIntroPageFinished()

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