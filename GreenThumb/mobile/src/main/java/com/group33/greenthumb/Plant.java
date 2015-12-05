package com.group33.greenthumb;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Table(name = "Plants")
public class Plant extends Model {
    // NOTE: If querying the database causes your app to crash because of a SQLiteException, try uninstalling and reinstalling/rerunning the app

    @Column(name = "Name")
    public String name;

    @Column(name = "WaterFrequency")
    public Integer waterFrequency;

    @Column(name = "CompostFrequency")
    public Integer compostFrequency;

    @Column(name = "TaskLastDone")
    public Map taskLastDone;

    public Plant(){
        super();
    }

    public Plant(String name, Integer waterFrequency, Integer compostFrequency) {
        super();
        this.name = name;
        this.waterFrequency = waterFrequency;
        this.compostFrequency = compostFrequency;
        this.taskLastDone = new HashMap<>();
        this.taskLastDone.put("water", Calendar.getInstance());
        this.taskLastDone.put("compost", Calendar.getInstance());
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
