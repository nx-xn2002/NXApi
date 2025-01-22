package com.nx.nxapiapi;

import com.nx.nxapiapi.client.NxApiClient;
import com.nx.nxapiapi.model.User;

/**
 * main
 *
 * @author nx-xn2002
 * @date 2025-01-20
 */
public class Main {
    public static void main(String[] args) {
        NxApiClient nxApiClient = new NxApiClient();
        nxApiClient.getNameByGet("nixiang");
        nxApiClient.getNameByPost("nixiang2");
        nxApiClient.getUserNameByPost(User.builder().name("nixiang3").build());
    }
}
