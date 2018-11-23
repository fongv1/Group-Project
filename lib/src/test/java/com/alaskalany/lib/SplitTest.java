package com.alaskalany.lib;

import com.alaskalany.lib.entity.UserEntity;
import com.alaskalany.lib.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SplitTest {

    Split split;

    @Before
    public void setUp() throws Exception {
        User user = new UserEntity();
        split = Split.getInstance(user);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getInstance() {
        Split expected = split;
        User user = new UserEntity();
        Split actual = Split.getInstance(user);
        assertEquals(expected, actual);
    }
}