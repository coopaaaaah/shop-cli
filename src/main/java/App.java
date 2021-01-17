import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import screen.Login;
import screen.OrderInquiry;
import service.OrderService;

import static util.ConsoleUtil.clearScreen;
import static util.ConsoleUtil.initShutdownHook;

/**
 * TODO:
 * (temp) Welcome Screen
 * (need) Login Page
 *          - Error Handling
 * (need) Order Inquiry Page
 *          - Product Navigation (grouped based on location and item id, ascending (sort))
 *          - Error Handling
 */
public class App {

    public static void main(String[] args) {

        initShutdownHook();
        clearScreen();

        OrderService orderService = initOrderService();

        // login stage
        String token = new Login(orderService).handleLogin();

        // order inquiry
        new OrderInquiry(token, orderService).searchOrders();

    }

    private static OrderService initOrderService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(OrderService.class);
    }

}
