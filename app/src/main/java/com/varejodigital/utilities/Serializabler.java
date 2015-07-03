package com.varejodigital.utilities;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * Created by Tox on 12/5/14.
 */
public class Serializabler {

    public static String toString(Object object) {
        byte[] bytes = com.varejodigital.utilities.Serializabler.toBytes(object);
        String base64 = Base64.encodeToString(bytes, Base64.NO_WRAP);
        return base64;
    }

    public static byte[] toBytes(Object object) {
        byte[] result = new byte[0];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(object);
            result = bos.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }

        return result;
    }

    public static Object toObject(String base64) {
        byte[] bytes = Base64.decode(base64, Base64.NO_WRAP);
        return toObject(bytes);
    }

    public static Object toObject(byte[] bytes) {
        Object o = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            o = in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException ex) {
                // ignore close exception
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return o;
    }

}
