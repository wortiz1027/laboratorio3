package co.edu.javeriana.abcmiddleware.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class ObjectAndByteCovertUtil {

    public static Object ByteToObject(byte[] bytes) {
        Object obj = null;
        ByteArrayInputStream bi = null;
        ObjectInputStream oi = null;
        try {
            // bytearray to object
            bi = new ByteArrayInputStream(bytes);
            oi = new ObjectInputStream(bi);
            obj = oi.readObject();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (null != bi)
                try {
                    bi.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            if (null != oi)
                try {
                    oi.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return obj;
    }

    public static byte[] ObjectToByte(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bo = null;
        ObjectOutputStream oo = null;
        try {
            // object to bytearray
            bo = new ByteArrayOutputStream();
            oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            bytes = bo.toByteArray();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (null != bo) {
                try {
                    bo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != oo) {
                try {
                    oo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }
}