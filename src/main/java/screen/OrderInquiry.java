package screen;

import domain.Order;
import domain.product.PickedProduct;
import retrofit2.Call;
import retrofit2.Response;
import service.OrderService;

import java.io.Console;
import java.io.IOException;

import static util.ConsoleUtil.*;

public class OrderInquiry {

    private final String authToken;
    private final OrderService orderService;

    public OrderInquiry(String authToken, OrderService orderService) {
        this.authToken = authToken;
        this.orderService = orderService;
    }

    public void searchOrders(){

        clearScreen();

        while(true) {

            try {
                // request orders
                System.out.println("Welcome to the Order Inquiry Screen.");
                System.out.println("Enter an Order ID to lookup an Order and its products");
                Console console = System.console();
                String orderId = console.readLine("Order ID: ");

                Call<Order> fetchOrder = orderService.fetchOrders(authToken, orderId);
                Response<Order> orderResponse = fetchOrder.execute();

                if (orderResponse.body() == null || orderResponse.code() == 400) {
                    clearScreen();
                    printErrorMessage("We could not fetch an Order with that Order ID. Try again.");
                    continue;
                }

                if (orderResponse.body().getProducts().isEmpty()) {
                    clearScreen();
                    printErrorMessage("The returned order has no products attached to it. Try again.");
                    continue;
                }

                // group orders for presentation - Product page view
                clearScreen();
                PickedProduct pickedProduct = new PickProduct(orderResponse.body().getProducts()).pickProduct(orderId, 0);

                // a user quit from the product selection screen
                if (pickedProduct == null) {
                    searchOrders();
                    return;
                }

                // summary screen - <Enter> to continue back order inquiry
                clearScreen();
                System.out.println("You picked this product:");
                System.out.println(pickedProduct.toString() + "\n");
                console.readLine("Press <Enter> to lookup a new order.");
                clearScreen();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
