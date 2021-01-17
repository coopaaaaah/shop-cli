package domain.product;

public class PickedProduct extends Product {

    private Integer quantity;

    public PickedProduct(ProductPage productPage, int quantityPicked) {
        this.name = productPage.getName();
        this.ean = productPage.getEan();
        this.location = productPage.getLocation();
        this.quantity = quantityPicked;
    }


    @Override
    public String toString() {
        return String.format("Name: %s\nEAN: %s\nLocation: %s\nQuantity: %s\n", name, ean, location, quantity);
    }
}
