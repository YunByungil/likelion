package com.likelion.jacoco;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JavFooTest {
    private JavaFoo javaFoo = new JavaFoo();

    @Test
    public void partiallyCoveredHelloMethodTest() {
        String actual = javaFoo.hello("펭");
        javaFoo.callMe();
        assertEquals(actual, "하");
    }
}