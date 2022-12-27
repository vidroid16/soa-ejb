package andrey.shalya.utils;

public enum Sortfields {

    ID("id"),
    NAME("name"),
    CREATION_DATE("creation-date"),
    WHEELS_NUMBER("wheels-number"),
    MILEAGE("mileage"),
    ENGINE_POWER("engine-power"),
    FUEL_CONSUMPTION("fuel-consumption"),
    X("x"),
    Y("Y"),
    VEHICLE_TYPE("vehicle-type"),
    FUEL_TYPE("fuel-type");
    public final String fieldname;

    Sortfields(String fieldname) {
        this.fieldname = fieldname;
    }

    public Sortfields getByFieldname(String fieldname){
        Sortfields[] array = Sortfields.values();
        for (int i = 0; i < array.length; i++) {
            if(fieldname.equals(array[i].fieldname))
                return array[i];
        }
        return Sortfields.ID;
    }

}
