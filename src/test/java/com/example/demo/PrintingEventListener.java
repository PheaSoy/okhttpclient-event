package com.example.demo;

import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.EventListener;

import java.net.InetAddress;
import java.util.List;

class PrintingEventListener extends EventListener {
    private long callStartNanos;

    private void printEvent(String name) {

        long nowNanos = System.nanoTime();
        if (name.equals("callStart")) {
            callStartNanos = nowNanos;
        }
        long elapsedNanos = nowNanos - callStartNanos;
        System.out.printf("%s execute in %.3f nanoseconds %n",name, elapsedNanos / 1000000000d);
    }

    @Override
    public void callStart(Call call) {
        printEvent("callStart");
    }

    @Override
    public void callEnd(Call call) {
        printEvent("callEnd");
    }

    @Override
    public void connectionAcquired(Call call, Connection connection) {
        printEvent("connectionAcquired");
    }

    @Override
    public void connectionReleased(Call call, Connection connection) {
        printEvent("connectionReleased");
    }

    @Override
    public void dnsStart(Call call, String domainName) {
        printEvent("dnsStart");
    }

    @Override
    public void dnsEnd(Call call, String domainName, List<InetAddress> inetAddressList) {
        printEvent("dnsEnd");
    }

}