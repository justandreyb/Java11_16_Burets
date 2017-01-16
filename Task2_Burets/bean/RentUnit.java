package by.training.equipment_store.bean;

public class RentUnit {
    private SportEquipment[] units;
    private int limit;

    public RentUnit() {
        units = new SportEquipment[0];
        limit = 3;
    }

    public RentUnit(SportEquipment[] units) {
        this.units = units;
    }

    public void addUnit(SportEquipment equipment) {
        int amount = units.length + 1;
        SportEquipment[] temp = new SportEquipment[amount];

        if (amount > 1) {
            for (int index = 0; index < amount; index++) {
                temp[index] = units[index];
            }
        }
        temp[amount - 1] = equipment;

        units = temp;
    }

    public void deleteUnit(SportEquipment equipment) {
        int targetIndex = -1;
        for (int index = 0; index < units.length; index++) {
            if (units[index].equals(equipment)) {
                targetIndex = index;
            }
        }

        deleteUnitByID(targetIndex);
    }

    public void deleteUnitByID(int equipmentID) {
        if (equipmentID >= 0) {
            if (units.length > 0) {
                int length = units.length - 1;
                SportEquipment[] temp = new SportEquipment[length];
                for (int index = 0; index < equipmentID; index++) {
                    temp[index] = units[index];
                }
                for (int index = equipmentID + 1; index < length; index++) {
                    temp[index] = units[index];
                }
                units = temp;
            }
        }
    }

    public SportEquipment[] getUnits() {
        return this.units;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
