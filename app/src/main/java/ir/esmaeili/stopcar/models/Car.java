package ir.esmaeili.stopcar.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "cars")
public class Car {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "car_id")
    private long carID;

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

    public long getCarID() {
        return carID;
    }

    public void setCarID(long carID) {
        this.carID = carID;
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
}
