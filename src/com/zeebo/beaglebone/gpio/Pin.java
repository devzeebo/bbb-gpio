package com.zeebo.beaglebone.gpio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Pin {

    public enum Value {
        LOW, HIGH
    }

    private FileInputStream valueInputStream;
    private FileOutputStream valueOutputStream;

    Pin(int gpioBank, int pinNumber)
            throws GpioException
    {
        int gpioNumber = gpioBank * 32 + pinNumber;

        try {
            File gpioDir = new File("/sys/class/gpio/gpio" + gpioNumber);

            valueInputStream = new FileInputStream(new File(gpioDir, "value"));
            valueOutputStream = new FileOutputStream(new File(gpioDir, "value"));
        }
        catch(IOException ioe) {
            throw new GpioException("Error acquiring file handles for gpio " + gpioNumber, ioe);
        }
    }

    public Value getValue()
            throws GpioException {
        try {
            return valueInputStream.read() == 1 ? Value.HIGH : Value.LOW;
        }
        catch (IOException ioe) {
            throw new GpioException("Error reading value from file", ioe);
        }
    }

    public void setValue(Value value)
            throws GpioException {
        try {
            valueOutputStream.write(value.ordinal());
        }
        catch(IOException ioe) {
            throw new GpioException("Error writing value to file", ioe);
        }
    }

    public static void main(String[] args) throws Exception {
        Pin pin = new Pin(0, 30);
        while(true) {
            pin.setValue(Value.HIGH);
            Thread.sleep(1000);
            pin.setValue(Value.LOW);
            Thread.sleep(1000);
        }
    }
}
