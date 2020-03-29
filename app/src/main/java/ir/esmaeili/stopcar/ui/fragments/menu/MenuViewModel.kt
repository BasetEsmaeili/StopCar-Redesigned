package ir.esmaeili.stopcar.ui.fragments.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import ir.esmaeili.stopcar.models.Response
import ir.esmaeili.stopcar.repository.RepositoryManager
import ir.esmaeili.stopcar.ui.base.BaseViewModel
import ir.esmaeili.stopcar.utils.SingleEvent

class MenuViewModel(repositoryManager: RepositoryManager) :
    BaseViewModel(repositoryManager) {

    private val clearDatabase: MutableLiveData<SingleEvent<Response<Boolean>>> = MutableLiveData()

    private fun setClearDatabase(response: Response<Boolean>) {
        this.clearDatabase.value = SingleEvent(response)
    }

    fun getClearDatabaseLiveData(): LiveData<SingleEvent<Response<Boolean>>> {
        return this.clearDatabase
    }

    fun clearDatabase() {
        val observer = object : DisposableCompletableObserver() {
            override fun onComplete() {
                setClearDatabase(Response.Success(true))
            }

            override fun onError(e: Throwable) {
                setClearDatabase(Response.Error(e))
            }
        }
        compositeDisposable.add(repositoryManager.deleteAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { setClearDatabase(Response.Loading()) }
            .subscribeWith(observer))
    }
}