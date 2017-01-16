package by.training.Task2_Burets.bean;

public class Category {
    private String type;
    private Gender gender;

    public Category(String type, Gender gender) {
        this.type = type;
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public Gender getGender() {
        return gender;
    }
}
