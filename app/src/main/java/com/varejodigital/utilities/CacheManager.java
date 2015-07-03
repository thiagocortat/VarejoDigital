package com.varejodigital.utilities;

import android.content.Context;

import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain;
import com.facebook.crypto.Crypto;
import com.facebook.crypto.Entity;
import com.facebook.crypto.exception.CryptoInitializationException;
import com.facebook.crypto.exception.KeyChainException;
import com.facebook.crypto.util.SystemNativeCryptoLibrary;

import java.io.IOException;

/**
 * Created by Tox on 5/19/15.
 */
public class CacheManager {

    private final static String key = "@#JD$(:><#ODLW)#@L94ls712;'[]e430";

    public static boolean write(Context context, String fileName, Object object) {
        if (object != null)
        {
            String objString = Serializabler.toString(object);
            if (objString != null)
            {
                Crypto crypto = new Crypto(
                        new SharedPrefsBackedKeyChain(context),
                        new SystemNativeCryptoLibrary());

                String objCrypted = null;
                try {
                    byte bytesCrypted[] = crypto.encrypt(objString.getBytes(), new Entity(key));
                    if (bytesCrypted != null)
                    {
                        objCrypted = new String(bytesCrypted);
                    }
                } catch (KeyChainException e) {
                    e.printStackTrace();
                } catch (CryptoInitializationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (objCrypted != null)
                    return (FileManager.write(context, fileName, objCrypted));
            }
        }

        return false;
    }

    public static Object readObject(Context context, String fileName)
    {
        String objCrypted = FileManager.readString(context, fileName);
        if (objCrypted != null)
        {
            Crypto crypto = new Crypto(
                    new SharedPrefsBackedKeyChain(context),
                    new SystemNativeCryptoLibrary());

            String objString = null;
            try {
                byte decryptedObj[] = crypto.decrypt(objCrypted.getBytes(), new Entity(key));
                if (decryptedObj != null)
                    objString = new String(decryptedObj);
            } catch (KeyChainException e) {
                e.printStackTrace();
            } catch (CryptoInitializationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (objString != null)
                return Serializabler.toObject(objString);
        }
        return null;
    }
}
