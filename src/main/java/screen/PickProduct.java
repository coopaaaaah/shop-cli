package screen;

import domain.product.PickedProduct;
import domain.product.Product;
import domain.product.ProductPage;

import java.io.Console;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static util.ConsoleUtil.clearScreen;
import static util.ConsoleUtil.printErrorMessage;

public class PickProduct {

    // TODO: expand this out to an abstract structure (abstract Page
    private final String COMMAND_QUIT = "q";
    private final String COMMAND_PREVIOUS = "p";
    private final String COMMAND_NEXT = "n";

    private List<ProductPage> productPages = new ArrayList<>();

    public PickProduct(List<Product> products) {

        Map<Object, List<Product>> groupedProducts = products.stream()
                .collect(Collectors.groupingBy(this::groupingKey));

        for (List<Product> groupedProduct : groupedProducts.values()) {
            productPages.add(new ProductPage(groupedProduct));
        }

        productPages.sort(Comparator.comparing(ProductPage::getLocation));

    }

    private String groupingKey(Product product) {
        return String.format("%s-%s",product.getLocation(), product.getEan());
    }


    public PickedProduct pickProduct(String orderId, Integer page) {

        Console console = System.console();

        do {

            String command;
            System.out.println(String.format("Viewing Products for Order ID %s", orderId));
            System.out.println(String.format("Product %s of %s\n", page + 1, productPages.size()));
            System.out.println(productPages.get(page).toString());

            // todo: combine parsing and validation?
            if (page == 0) {
                command = console.readLine("[q]uit, [n]ext, quantity to pick and press <Enter>\nCommand: ");
                if (!isValidCommand("[qn]|\\d+|-\\d+", command)) continue;
            } else if (page < productPages.size() - 1) {
                command = console.readLine("[q]uit, [p]revious, [n]ext, quantity to pick and press <Enter>\nCommand: ");
                if (!isValidCommand("[qpn]|\\d+|-\\d+", command)) continue;
            } else {
                command = console.readLine("[q]uit, [p]revious, quantity to pick and press <Enter>\nCommand: ");
                if (!isValidCommand("[qp]|\\d+|-\\d+", command)) continue;
            }

            try {
                Integer quantityToPick = Integer.parseInt(command);
                ProductPage productPage = productPages.get(page);

                if (quantityToPick <= 0) {
                    clearScreen();
                    printErrorMessage("You cannot pick 0 or less of an item. Pick more.");
                    return pickProduct(orderId, page);
                }

                if (productPage.getQuantity() < quantityToPick) {
                    clearScreen();
                    printErrorMessage("Pick quantity exceeds available quantity. Pick less.");
                    return pickProduct(orderId, page);
                }

                return new PickedProduct(productPage, quantityToPick);
            } catch (NumberFormatException e) {
                // not a number, but a string, continue to process the command
            }

            // check if it's an integer
            switch(command) {
                case COMMAND_PREVIOUS:
                    clearScreen();
                    return pickProduct(orderId, page - 1);
                case COMMAND_NEXT:
                    clearScreen();
                    return pickProduct(orderId,page + 1);
                case COMMAND_QUIT:
                default:
                    clearScreen();
                    return null;
            }

        } while(true);

    }

    private boolean isValidCommand(String pattern, String command) {
        if (!command.matches(pattern)) {
            clearScreen();
            printErrorMessage("Invalid command.");
            return false;
        }
        return true;
    }


}
