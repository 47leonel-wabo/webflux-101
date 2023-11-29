package com.wbt.webflux101.util;

public record SleepUtil() {
    public static void sleepThread(final Integer duration   ){
        try {
            Thread.sleep(duration * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
