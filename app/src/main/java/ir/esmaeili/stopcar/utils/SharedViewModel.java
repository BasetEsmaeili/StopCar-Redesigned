package ir.esmaeili.stopcar.utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

import ir.esmaeili.stopcar.models.ActivityResultModel;
import ir.esmaeili.stopcar.models.AlertView;
import ir.esmaeili.stopcar.models.Car;
import ir.esmaeili.stopcar.models.CarColor;
import ir.esmaeili.stopcar.models.PermissionModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<AlertView> alertView = new MutableLiveData<>();
    private MutableLiveData<PermissionModel> permissions = new MutableLiveData<>();
    private MutableLiveData<ActivityResultModel> activityResult = new MutableLiveData<>();
    private MutableLiveData<CarColor> selectCarColor = new MutableLiveData<>();
    private MutableLiveData<SingleEvent<Car>> selectCar = new MutableLiveData<>();
    private MutableLiveData<Boolean> progressbar = new MutableLiveData<>();
    private MutableLiveData<NavController> navController = new MutableLiveData<>();


    public void setNavController(NavController navController) {
        this.navController.setValue(navController);
    }

    public MutableLiveData<NavController> getNavController() {
        return navController;
    }

    public LiveData<Boolean> getProgressbar() {
        return progressbar;
    }

    public void setProgressbar(boolean value) {
        this.progressbar.setValue(value);
    }

    public void selectCar(Car car) {
        this.selectCar.setValue(new SingleEvent<>(car));
    }

    public LiveData<SingleEvent<Car>> getSelectedCar() {
        return this.selectCar;
    }


    public void selectCarColor(CarColor color) {
        this.selectCarColor.setValue(color);
    }

    public LiveData<CarColor> getSelectedCarColor() {
        return this.selectCarColor;
    }

    public void setActivityResultEvent(ActivityResultModel activityResult) {
        this.activityResult.setValue(activityResult);
    }

    public LiveData<ActivityResultModel> getActivityResultEvent() {
        return this.activityResult;
    }

    public void setPermissionEvent(PermissionModel permission) {
        this.permissions.setValue(permission);
    }

    public LiveData<PermissionModel> getPermissionEvent() {
        return permissions;
    }

    public void setAlertEvent(AlertView eventListener) {
        this.alertView.setValue(eventListener);
    }

    public LiveData<AlertView> getAlertEvent() {
        return alertView;
    }
}
