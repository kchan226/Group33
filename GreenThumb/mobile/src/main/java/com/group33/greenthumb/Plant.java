package com.group33.greenthumb;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Calendar;
import java.util.List;

@Table(name = "Plants")
public class Plant extends Model {
    // NOTE: If querying the database causes your app to crash because of a SQLiteException, try uninstalling and reinstalling/rerunning the app

    @Column(name = "Name")
    public String name;

    // water every _____ days
    @Column(name = "WaterFrequency")
    public Integer waterFrequency;

    @Column(name = "LastWatered")
    public Calendar lastWatered;

    public Plant(){
        super();
    }

    public Plant(String name, Integer waterFrequency) {
        super();
        this.name = name;
        this.waterFrequency = waterFrequency;
        this.lastWatered = Calendar.getInstance();
    }

    public static Plant getPlant(String name) {
        return new Select()
                .from(Plant.class)
                .where("Name = ?", name)
                .executeSingle();
    }

    public static List<Plant> getAllPlants() {
        return new Select(new String[]{"Id, Name"})
                .from(Plant.class)
                .orderBy("Name DESC")
                .execute();
    }
}
