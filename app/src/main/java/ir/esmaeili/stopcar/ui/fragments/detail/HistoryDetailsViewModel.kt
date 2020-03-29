package ir.esmaeili.stopcar.ui.fragments.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.google.android.gms.maps.model.LatLng
import ir.esmaeili.stopcar.models.HistoryJoinCar
import ir.esmaeili.stopcar.repository.RepositoryManager
import ir.esmaeili.stopcar.ui.base.BaseViewModel
import ir.esmaeili.stopcar.utils.Constants

class HistoryDetailsViewModel(
    repositoryManager: RepositoryManager,
    private val state: SavedStateHandle
) :
    BaseViewModel(repositoryManager) {
    fun setHistory(historyJoinCar: HistoryJoinCar) {
        state.set(Constants.KEY_PARK_DATE, historyJoinCar.parkDate)
        state.set(Constants.KEY_PARK_CLOCK, historyJoinCar.parkClock)
        state.set(Constants.KEY_PARK_ADDRESS, historyJoinCar.parkAddress)
        state.set(Constants.KEY_PARK_LATITUDE, historyJoinCar.latitude)
        state.set(Constants.KEY_PARK_LONGITUDE, historyJoinCar.longitude)
        state.set(Constants.KEY_CAR_NAME, historyJoinCar.carName)
        state.set(Constants.KEY_CAR_MODEL, historyJoinCar.carModel)
        state.set(Constants.KEY_CAR_COLOR, historyJoinCar.carColor)
        state.set(Constants.KEY_PLAQUE_IR_CODE, historyJoinCar.carPlaqueIrCode)
        state.set(Constants.KEY_PLAQUE_PART_THREE, historyJoinCar.carPlaquePartThree)
        state.set(Constants.KEY_PLAQUE_KEYWORD, historyJoinCar.carPlaqueKeyWord)
        state.set(Constants.KEY_PLAQUE_PART_TWO, historyJoinCar.carPlaquePartTwo)
    }

    fun isMapFragmentAttached(): Boolean {
        return state.contains(Constants.KEY_MAP_FRAGMENT)
    }

    fun setMapFragmentAttached() {
        state.set(Constants.KEY_MAP_FRAGMENT, true)
    }

    fun getParkDate(): LiveData<String> {
        return this.state.getLiveData(Constants.KEY_PARK_DATE)
    }

    fun getParkClock(): LiveData<String> {
        return this.state.getLiveData(Constants.KEY_PARK_CLOCK)
    }

    fun getParkAddress(): LiveData<String> {
        return this.state.getLiveData(Constants.KEY_PARK_ADDRESS)
    }

    fun getCarName(): LiveData<String> {
        return this.state.getLiveData(Constants.KEY_CAR_NAME)
    }

    fun getCarModel(): LiveData<String> {
        return this.state.getLiveData(Constants.KEY_CAR_MODEL)
    }

    fun getCarColor(): LiveData<String> {
        return this.state.getLiveData(Constants.KEY_CAR_COLOR)
    }

    fun getCarPlaqueIrCode(): LiveData<String> {
        return this.state.getLiveData(Constants.KEY_PLAQUE_IR_CODE)
    }

    fun getCarPlaquePartThree(): LiveData<String> {
        return this.state.getLiveData(Constants.KEY_PLAQUE_PART_THREE)
    }

    fun getCarPlaqueKeyWord(): LiveData<String> {
        return this.state.getLiveData(Constants.KEY_PLAQUE_KEYWORD)
    }

    fun getCarPlaquePartTwo(): LiveData<String> {
        return this.state.getLiveData(Constants.KEY_PLAQUE_PART_TWO)
    }

    fun getParkLocation(): LatLng {
        val latitude = state.get<Double>(Constants.KEY_PARK_LATITUDE)
        val longitude = state.get<Double>(Constants.KEY_PARK_LONGITUDE)
        return LatLng(latitude!!, longitude!!)
    }


}