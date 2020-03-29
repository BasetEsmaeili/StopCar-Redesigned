package ir.esmaeili.stopcar.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;

public class HistoryJoinCar implements Parcelable {
    @ColumnInfo(name = "history_id")
    private long historyID;
    @ColumnInfo(name = "car_id")
    private long carID;
    @ColumnInfo(name = "park_date")
    private String parkDate;
    @ColumnInfo(name = "park_clock")
    private String parkClock;
    @ColumnInfo(name = "park_address")
    private String parkAddress;
    @ColumnInfo(name = "latitude")
    private double latitude;
    @ColumnInfo(name = "longitude")
    private double longitude;
    @ColumnInfo(name = "car_name")
    private String carName;
    @ColumnInfo(name = "car_model")
    private String carModel;
    @ColumnInfo(name = "car_color")
    private String carColor;
    @ColumnInfo(name = "car_plaque_ir_code")
    private String carPlaqueIrCode;
    @ColumnInfo(name = "car_plaque_part_three")
    private String carPlaquePartThree;
    @ColumnInfo(name = "car_plaque_keyword")
    private String carPlaqueKeyWord;
    @ColumnInfo(name = "car_plaque_part_two")
    private String carPlaquePartTwo;

    public HistoryJoinCar(long historyID, long carID, String parkDate, String parkClock, String parkAddress, double latitude, double longitude, String carName, String carModel, String carColor, String carPlaqueIrCode, String carPlaquePartThree, String carPlaqueKeyWord, String carPlaquePartTwo) {
        this.historyID = historyID;
        this.carID = carID;
        this.parkDate = parkDate;
        this.parkClock = parkClock;
        this.parkAddress = parkAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.carName = carName;
        this.carModel = carModel;
        this.carColor = carColor;
        this.carPlaqueIrCode = carPlaqueIrCode;
        this.carPlaquePartThree = carPlaquePartThree;
        this.carPlaqueKeyWord = carPlaqueKeyWord;
        this.carPlaquePartTwo = carPlaquePartTwo;
    }

    public long getHistoryID() {
        return historyID;
    }

    public void setHistoryID(long historyID) {
        this.historyID = historyID;
    }

    public long getCarID() {
        return carID;
    }

    public void setCarID(long carID) {
        this.carID = carID;
    }

    public String getParkDate() {
        return parkDate;
    }

    public void setParkDate(String parkDate) {
        this.parkDate = parkDate;
    }

    public String getParkClock() {
        return parkClock;
    }

    public void setParkClock(String parkClock) {
        this.parkClock = parkClock;
    }

    public String getParkAddress() {
        return parkAddress;
    }

    public void setParkAddress(String parkAddress) {
        this.parkAddress = parkAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarPlaqueIrCode() {
        return carPlaqueIrCode;
    }

    public void setCarPlaqueIrCode(String carPlaqueIrCode) {
        this.carPlaqueIrCode = carPlaqueIrCode;
    }

    public String getCarPlaquePartThree() {
        return carPlaquePartThree;
    }

    public void setCarPlaquePartThree(String carPlaquePartThree) {
        this.carPlaquePartThree = carPlaquePartThree;
    }

    public String getCarPlaqueKeyWord() {
        return carPlaqueKeyWord;
    }

    public void setCarPlaqueKeyWord(String carPlaqueKeyWord) {
        this.carPlaqueKeyWord = carPlaqueKeyWord;
    }

    public String getCarPlaquePartTwo() {
        return carPlaquePartTwo;
    }

    public void setCarPlaquePartTwo(String carPlaquePartTwo) {
        this.carPlaquePartTwo = carPlaquePartTwo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.historyID);
        dest.writeLong(this.carID);
        dest.writeString(this.parkDate);
        dest.writeString(this.parkClock);
        dest.writeString(this.parkAddress);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.carName);
        dest.writeString(this.carModel);
        dest.writeString(this.carColor);
        dest.writeString(this.carPlaqueIrCode);
        dest.writeString(this.carPlaquePartThree);
        dest.writeString(this.carPlaqueKeyWord);
        dest.writeString(this.carPlaquePartTwo);
    }

    protected HistoryJoinCar(Parcel in) {
        this.historyID = in.readLong();
        this.carID = in.readLong();
        this.parkDate = in.readString();
        this.parkClock = in.readString();
        this.parkAddress = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.carName = in.readString();
        this.carModel = in.readString();
        this.carColor = in.readString();
        this.carPlaqueIrCode = in.readString();
        this.carPlaquePartThree = in.readString();
        this.carPlaqueKeyWord = in.readString();
        this.carPlaquePartTwo = in.readString();
    }

    public static final Parcelable.Creator<HistoryJoinCar> CREATOR = new Parcelable.Creator<HistoryJoinCar>() {
        @Override
        public HistoryJoinCar createFromParcel(Parcel source) {
            return new HistoryJoinCar(source);
        }

        @Override
        public HistoryJoinCar[] newArray(int size) {
            return new HistoryJoinCar[size];
        }
    };
}
