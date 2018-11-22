package com.alaskalany.lib;


class Split {

    private static final Split ourInstance = new Split();

    private Split() {
    }

    static Split getInstance() {
        return ourInstance;
    }
}
