package com.nx.nxapi.provider;

import java.util.concurrent.CompletableFuture;

/**
 * demo service
 *
 * @author nx-xn2002
 */
public interface DemoService {
    /**
     * say hello
     *
     * @param name name
     * @return {@link String }
     */
    String sayHello(String name);

    /**
     * say hello async
     *
     * @param name name
     * @return {@link CompletableFuture }<{@link String }>
     */
    default CompletableFuture<String> sayHelloAsync(String name) {
        return CompletableFuture.completedFuture(sayHello(name));
    }
}
