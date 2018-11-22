package com.alaskalany.lib;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SplitTest {

    Split split;

    @Before
    public void setUp() throws Exception {
        split = Split.getInstance();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getInstance() {
        Split expected = split;
        Split actual = Split.getInstance();
        assertEquals(expected, actual);
    }
}