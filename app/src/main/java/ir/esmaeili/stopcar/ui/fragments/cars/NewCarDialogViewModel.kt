package ir.esmaeili.stopcar.ui.fragments.cars

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import ir.esmaeili.stopcar.models.Car
import ir.esmaeili.stopcar.models.CarColor
import ir.esmaeili.stopcar.models.Response
import ir.esmaeili.stopcar.repository.RepositoryManager
import ir.esmaeili.stopcar.ui.base.BaseViewModel
import ir.esmaeili.stopcar.utils.Constants

class NewCarDialogViewModel(
    repositoryManager: RepositoryManager,
    private val savedStateHandle: SavedStateHandle
) :
    BaseViewModel(repositoryManager) {
    private val newCarLiveData: MutableLiveData<Response<Boolean>> = MutableLiveData()
    private val eventHandler: MutableLiveData<Int> = MutableLiveData()

    fun setEventHandler(value: Int) {
        this.eventHandler.value = value
    }


    fun getEventHandler(): MutableLiveData<Int> {
        return this.eventHandler
    }

    fun getCarPlaquePartTwo(): LiveData<String> {
        return this.savedStateHandle.getLiveData(Constants.KEY_PLAQUE_PART_TWO, "")
    }

    fun getCarColor(): LiveData<String> {
        return this.savedStateHandle.getLiveData(Constants.KEY_CAR_COLOR, "")
    }

    fun setCarColor(color: String) {
        this.savedStateHandle.set(Constants.KEY_CAR_COLOR, color)
    }

    fun setCarPlaquePartTwo(partTwO: String) {
        this.savedStateHandle.set(Constants.KEY_PLAQUE_PART_TWO, partTwO)
    }

    fun getCarPlaqueKeyWord(): LiveData<String> {
        return this.savedStateHandle.getLiveData(Constants.KEY_PLAQUE_KEYWORD, "")
    }

    fun setCarPlaqueKeyWord(keyWord: String) {
        this.savedStateHandle.set(Constants.KEY_PLAQUE_KEYWORD, keyWord)
    }


    fun getCarPlaquePartThreeCode(): LiveData<String> {
        return this.savedStateHandle.getLiveData(Constants.KEY_PLAQUE_PART_THREE, "")
    }

    fun setCarPlaquePartThreeCode(partThree: String) {
        this.savedStateHandle.set(Constants.KEY_PLAQUE_PART_THREE, partThree)
    }


    fun getCarPlaqueIrCode(): LiveData<String> {
        return this.savedStateHandle.getLiveData(Constants.KEY_PLAQUE_IR_CODE, "")
    }

    fun setCarPlaqueIrCode(irCode: String) {
        this.savedStateHandle.set(Constants.KEY_PLAQUE_IR_CODE, irCode)
    }


    fun getCarName(): LiveData<String> {
        return this.savedStateHandle.getLiveData(Constants.KEY_CAR_NAME, "")
    }

    fun setCarName(carName: String) {
        this.savedStateHandle.set(Constants.KEY_CAR_NAME, carName)
    }


    fun getCarModel(): LiveData<String> {
        return this.savedStateHandle.getLiveData(Constants.KEY_CAR_MODEL, "")
    }

    fun setCarModel(carModel: String) {
        this.savedStateHandle.set(Constants.KEY_CAR_MODEL, carModel)
    }


    fun getCarColors(): ArrayList<CarColor> = repositoryManager.getCarColors()

    fun insertCar() {
        val car = Car()
        car.carID = 0
        car.carName = getCarName().value
        car.carModel = getCarModel().value
        car.carColor = getCarColor().value
        car.carPlaqueIrCode = getCarPlaqueIrCode().value
        car.carPlaquePartThree = getCarPlaquePartThreeCode().value
        car.carPlaqueKeyWord = getCarPlaqueKeyWord().value
        car.carPlaquePartTwo = getCarPlaquePartTwo().value
        val observer = object : DisposableSingleObserver<Boolean>() {
            override fun onSuccess(t: Boolean) {
                setNewCarLiveData(Response.Success(t))
            }

            override fun onError(e: Throwable) {
                setNewCarLiveData(Response.Error(e))
            }
        }
        compositeDisposable.add(
            repositoryManager.insertCar(car)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(observer)
        )
    }

    fun isNewCarInputsNotEmpty(): Boolean {
        return getCarName().value!!.isNotEmpty() && getCarModel().value!!.isNotEmpty() &&
                getCarColor().value!!.isNotEmpty() && getCarPlaqueIrCode().value!!.isNotEmpty() &&
                getCarPlaquePartThreeCode().value!!.isNotEmpty() && getCarPlaqueKeyWord().value!!
            .isNotEmpty() &&
                getCarPlaquePartTwo().value!!.isNotEmpty()
    }

    fun getNewCarLiveData(): MutableLiveData<Response<Boolean>> {
        return this.newCarLiveData
    }


    private fun setNewCarLiveData(response: Response<Boolean>) {
        this.newCarLiveData.value = response
    }

}