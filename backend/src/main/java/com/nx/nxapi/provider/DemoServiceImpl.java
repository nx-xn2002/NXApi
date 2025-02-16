package com.nx.nxapi.provider;

import org.apache.dubbo.config.annotation.DubboService;

/**
 * demo service impl
 *
 * @author nx-xn2002
 */
@DubboService
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "hello" + name;
    }
}
