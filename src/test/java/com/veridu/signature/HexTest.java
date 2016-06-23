package com.veridu.signature;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.veridu.signature.Hex;

public class HexTest {

    Hex hex = new Hex();

    @Test
    public void testEncode() {
        byte[] bytes = new byte[10];
        Object string = Hex.Encode(bytes);
        assertTrue(string instanceof String);
    }
}
