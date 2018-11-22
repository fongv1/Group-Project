package com.alaskalany.lib;

import static org.junit.jupiter.api.Assertions.assertSame;

class SplitTest {

    private Split split;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        split = Split.getInstance();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void getInstance() {
        Split expected = split;
        Split actual = Split.getInstance();
        assertSame(expected, actual);
    }
}