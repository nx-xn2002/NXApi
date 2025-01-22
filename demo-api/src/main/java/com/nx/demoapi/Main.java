package com.nx.demoapi;

import com.nx.demoapi.client.DemoApiClient;
import com.nx.demoapi.model.User;

/**
 * main
 *
 * @author nx-xn2002
 * @date 2025-01-20
 */
public class Main {
    public static void main(String[] args) {
        DemoApiClient demoApiClient = new DemoApiClient("123456", "123456");
        demoApiClient.getUserNameByPost(new User("nx-xn2002"));
    }
}
