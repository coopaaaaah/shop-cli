package domain.product;

import java.util.List;

public class ProductPage extends Product {

    Integer quantity;

    public ProductPage(List<Product> products) {
        super();
        if (products.isEmpty()) {
            return;
        }

        Product product = products.get(0);
        name = product.getName();
        this.ean = product.getEan();
        this.location = product.getLocation();
        this.quantity = products.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEan() {
        return ean;
    }

    public void setEan(Long ean) {
        this.ean = ean;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("Name: %s\nEAN: %s\nLocation: %s\nQuantity: %s\n", name, ean, location, quantity);
    }
}