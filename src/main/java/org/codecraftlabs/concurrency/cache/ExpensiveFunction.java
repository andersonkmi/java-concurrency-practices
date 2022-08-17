package org.codecraftlabs.concurrency.cache;

import java.math.BigInteger;

public class ExpensiveFunction implements Computable<String, BigInteger> {
    @Override
    public BigInteger compute(String value) throws InterruptedException {
        // simulate an expensive computation here
        return new BigInteger(value);
    }
}
