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

    @Column(name = "WaterFrequency")
    public Integer waterFrequency;

    @Column(name = "CompostFrequency")
    public Integer compostFrequency;

    @Column(name = "HarvestFrequency")
    public Integer harvestFrequency;

    @Column(name = "LastWatered")
    public Calendar lastWatered;

    @Column(name = "LastComposted")
    public Calendar lastComposted;

    @Column(name = "LastHarvested")
    public Calendar lastHarvested;

    @Column(name = "SoilpH")
    public double soilpH;

    @Column(name = "SowDepth")
    public double sowDepth;

    public Plant(){
        super();
    }

    public Plant(String name, Integer waterFrequency, Integer compostFrequency, Integer harvestFrequency, double soilpH, double sowDepth) {
        super();
        this.name = name;
        this.waterFrequency = waterFrequency;
        this.compostFrequency = compostFrequency;
        this.harvestFrequency = harvestFrequency;
        this.lastWatered = Calendar.getInstance();
        this.lastComposted = Calendar.getInstance();
        this.lastHarvested = Calendar.getInstance();
        this.soilpH = soilpH;
        this.sowDepth = sowDepth;
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
                .orderBy("Name ASC")
                .execute();
    }

    public static List<Plant> search(String searchTerm) {
        return new Select().from(Plant.class)
                .where("Name LIKE ?", new String[]{'%' + searchTerm + '%'})
                .orderBy("Name ASC")
                .execute();
    }
}
