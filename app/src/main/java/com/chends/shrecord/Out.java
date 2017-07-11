package com.chends.shrecord;

/**
 * Created by chends on 2017/6/30.
 */

public class Out {

    private double money;

    private String mode;

    private String type;

    private String date;

    private String explain;

    public Out(double money, String explain, String date) {
        this.money = money;
        this.explain = explain;
        this.date = date;
    }

    public Out(double money, String mode, String type, String date, String explain) {
        this.money = money;
        this.mode = mode;
        this.type = type;
        this.date = date;
        this.explain = explain;
    }

    public double getMoney() {
        return money;
    }

    public String getMode() {
        return mode;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getExplain() {
        return explain;
    }
}
