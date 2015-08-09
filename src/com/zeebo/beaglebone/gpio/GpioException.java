package com.zeebo.beaglebone.gpio;

public class GpioException extends Exception {

    public GpioException() {}

    public GpioException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
