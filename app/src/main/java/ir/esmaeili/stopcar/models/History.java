package ir.esmaeili.stopcar.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "history", foreignKeys = @ForeignKey(entity = Car.class, parentColumns = "car_id", childColumns = "car_id", onDelete = ForeignKey.CASCADE))
public class History implements Parcelable {
    @PrimaryKey(autoGenerate = true)
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
    }

    public History() {
    }

    protected History(Parcel in) {
        this.historyID = in.readLong();
        this.carID = in.readLong();
        this.parkDate = in.readString();
        this.parkClock = in.readString();
        this.parkAddress = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    public static final Parcelable.Creator<History> CREATOR = new Parcelable.Creator<History>() {
        @Override
        public History createFromParcel(Parcel source) {
            return new History(source);
        }

        @Override
        public History[] newArray(int size) {
            return new History[size];
        }
    };
}
