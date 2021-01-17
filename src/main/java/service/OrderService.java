package service;

import domain.AuthToken;
import domain.Credentials;
import domain.Order;
import retrofit2.Call;
import retrofit2.http.*;

public interface OrderService {

    /**
     * 200: { token : "token_response" } <-- good credentials
     * 401: null body <- bad credentials
     * @param credentials Object representing username and password
     * @return Call<AuthToken>
     */
    @POST("/tokens")
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    Call<AuthToken> login(@Body Credentials credentials);

    /**
     * 200 <- ok
     *  response: Order obj
     * 401 <- no token; unauthorized
     *  response: null
     * 400 <- bad identifier; bad request
     *  response: null
     * @param token Auth token from login method
     * @param orderId Order identifier (ei. O01, O02, O03, ...)
     * @return Call<Order>
     */
    @POST("/order")
    @Headers({"Accept: application/json", "Content-Type: application/json"})
    Call<Order> fetchOrders(@Header("Authorization") String token, @Query("identifier") String orderId);
}
