package by.training.equipment_store.bean;

import java.math.BigDecimal;

public class SportEquipment {
    private Category category;
    private String title;
    private BigDecimal price;

    public SportEquipment(String title, double price, Category category) {
        this.category = category;
        this.title = title;
        this.price = new BigDecimal(price);
    }

    public SportEquipment(String title, BigDecimal price, Category category) {
        this.category = category;
        this.title = title;
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price.doubleValue();
    }

    public BigDecimal getPriceDecimal() {
        return price;
    }
}
