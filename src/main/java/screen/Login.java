package screen;

import domain.AuthToken;
import domain.Credentials;
import retrofit2.Call;
import retrofit2.Response;
import service.OrderService;

import java.io.Console;
import java.io.IOException;

import static util.ConsoleUtil.clearScreen;
import static util.ConsoleUtil.printErrorMessage;

public class Login {
    private OrderService orderService;

    public Login(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Loops until a successful login or user closes out of console
     * @return token
     */
    public String handleLogin() {

        clearScreen();
        System.out.println("Welcome to the Shop CLI - crtl+C to exit at any time");
        System.out.println("Please Login.\n");

        while(true) {

            Console console = System.console();
            String username = console.readLine("Username: ");
            String password = new String(console.readPassword("Password: "));

            Call<AuthToken> login = orderService.login(new Credentials(username, password));

            try {
                Response<AuthToken> loginResponse = login.execute();

                if (loginResponse.code() == 401) {
                    clearScreen();
                    printErrorMessage("You are not an authorized user. Try again.");
                    continue;
                }

                if (loginResponse.body() == null) {
                    clearScreen();
                    printErrorMessage("Error making token call to server. Body is null. Try again.");
                    continue;
                }

                return loginResponse.body().getToken();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
