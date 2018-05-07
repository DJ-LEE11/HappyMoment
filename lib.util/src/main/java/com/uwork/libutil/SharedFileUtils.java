package com.uwork.libutil;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author 李栋杰
 * @time 2017/8/1  23:56
 * @desc 数据缓存
 */
public class SharedFileUtils {
    public static final String FILE_NAME = "pk_file";


    private SharedPreferences sp;

    public SharedFileUtils(Context context) {
        sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
    }

    public void putString(String key, String value) {
        if (key == null || key.equals(""))
            throw new IllegalArgumentException(
                    "Key can't be null or empty string");
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        if (key == null || key.equals(""))
            throw new IllegalArgumentException(
                    "Key can't be null or empty string");
        return sp.getString(key, "");
    }

    public int getInt(String key) {
        return sp.getInt(key,0);
    }

    public void putInt(String key, int value) {
        Editor e = sp.edit();
        e.putInt(key, value);
        e.commit();
    }

    public void putBoolean(String key, boolean value) {
        if (key == null || key.equals(""))
            throw new IllegalArgumentException(
                    "Key can't be null or empty string");
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key) {
        return sp.getBoolean(key, false);
    }

    public long getLong(String key) {
        return sp.getLong(key, 0);
    }

    public void putLong(String key, long value) {
        Editor editor = sp.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void putBean(String key, Object obj) {
        if (obj instanceof Serializable) {// obj必须实现Serializable接口，否则会出问题
            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(out);
                oos.writeObject(obj);
                String string64 = new String(Base64.encode(out.toByteArray(),
                        0));
                Editor editor = sp.edit();
                editor.putString(key, string64).commit();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            throw new IllegalArgumentException(
                    "the obj must implement Serializble");
        }

    }

    public  Object getBean(String key) {
        Object obj = null;
        try {
            String base64 = sp.getString(key, "");
            if (base64.equals("")) {
                return null;
            }
            byte[] base64Bytes = Base64.decode(base64.getBytes(), 1);
            ByteArrayInputStream input = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(input);
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public boolean isContainsKey(String key) {
        return sp.contains(key);
    }

    public void remove(String key) {
        if (key == null || key.equals(""))
            throw new IllegalArgumentException(
                    "Key can't be null or empty string");
        Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }
}
