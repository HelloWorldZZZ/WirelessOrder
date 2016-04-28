package ecnu.pb.wireless_order.net;

import retrofit.RestAdapter;

public class RestClient {
    private static final String ENDPOINT = "http://www.zhouzezhou.site/WirelessOrder/servlet";

    private static RestAdapter adapter;

    public static UserService createUserService() {
        return getRestAdapter().create(UserService.class);
    }

    public static MenuService createMenuService() {
        return getRestAdapter().create(MenuService.class);
    }

   public static OrderService createOrderService() {
        return getRestAdapter().create(OrderService.class);
    }

    private static RestAdapter getRestAdapter() {
        if (adapter == null) {
            adapter = new RestAdapter.Builder()
                    .setEndpoint(ENDPOINT)
                    .build();
        }
        return adapter;
    }
}

