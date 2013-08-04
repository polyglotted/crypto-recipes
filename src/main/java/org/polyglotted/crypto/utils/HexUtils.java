package org.polyglotted.crypto.utils;

public abstract class HexUtils {

    private static final byte[] decodingTable = new byte[128];
    private static final byte[] encodingTable = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
    static {
        for (int i = 0; i < encodingTable.length; i++)
            decodingTable[encodingTable[i]] = (byte) i;

        for (int i = 65, j = 97; i <= 70; i++, j++)
            decodingTable[i] = decodingTable[j];
    }

    public static String encodeString(byte[] bytes) {
        return new String(encode(bytes));
    }

    public static byte[] encode(byte[] input) {
        byte[] result = new byte[input.length * 2];
        for (int i = 0; i < input.length; i++)
            encode(input[i], result, i * 2);

        return result;
    }

    public static void encode(byte originalByte, byte encodedBytes[], int offset) {
        int l = originalByte & 0xff;
        encodedBytes[offset] = (encodingTable[l >>> 4]);
        encodedBytes[offset + 1] = (encodingTable[l & 0xf]);
    }

    public static byte[] decode(String hexValue) {
        return decode(hexValue.getBytes());
    }

    public static byte[] decode(byte input[]) {
        byte[] result = new byte[input.length / 2];
        for (int i = 0, j = 0; i < input.length; i = i + 2, j++) {
            result[j] = decode(input[i], input[i + 1]);
        }
        return result;
    }

    public static byte decode(byte b1, byte b2) {
        return (byte) (decodingTable[b1] << 4 | decodingTable[b2]);
    }
}
