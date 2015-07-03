package com.varejodigital.utilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateFormat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public class FileManager {

    public interface FileManagerWriteCallback
    {
        void onWriteFinish(String fileName, Bitmap bitmap);
    }

    public static String getWritablePath(Context context) {
        if (context.getExternalFilesDir(null) != null) {
            String path = context.getExternalFilesDir(null).getAbsolutePath();
            File file = new File(path);
            if (file.exists())
                return path;
            else {
                path = context.getFilesDir().getAbsolutePath();
                file = new File(path);
                if (file.exists())
                    return path;
            }
        }

        return null;
    }

    public static String getWritablePath(Context context, String fileName) {
        String path = getWritablePath(context);
        if (path != null)
            path = path + "/" + fileName;

        return path;
    }

    // fileName = nome do arquivo apenas puro sem o caminho completo
    public static boolean write(Context context, String fileName, String text) {
        String path = FileManager.getWritablePath(context);
        if (path != null) {
            File file = new File(path + "/" + fileName);
            if (file != null) {
                FileOutputStream writer;
                try {
                    writer = new FileOutputStream(file);
                    writer.write(text.getBytes());
                    writer.flush();
                    writer.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return false;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean write(Context context, String fileName,
                                Bitmap bitmap, Bitmap.CompressFormat format) {
        String path = com.varejodigital.utilities.FileManager.getWritablePath(context);
        if (path != null) {
            File file = new File(path + "/" + fileName);
            if (file != null) {
                FileOutputStream writer;
                try {
                    writer = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, writer);
                    writer.flush();
                    writer.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return false;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }
        }

        return true;
    }

    public static void writeAsync(final Context context, final String fileName,
                                  final Bitmap bitmap, final Bitmap.CompressFormat format, final FileManagerWriteCallback callback)
    {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                write(context, fileName, bitmap, format);
                if (callback != null)
                    callback.onWriteFinish(fileName, bitmap);
            }
        });
        thread.start();
    }

    public static String readString(Context context, String fileName) {
        String result = null;
        String path = com.varejodigital.utilities.FileManager.getWritablePath(context);
        if (path != null) {
            String filePath = path + "/" + fileName;
            File file = new File(filePath);
            if (file.exists()) {
                FileInputStream fis;
                try {
                    fis = new FileInputStream(file);
                    if (fis != null) {
                        StringBuffer fileContent = new StringBuffer("");

                        byte[] buffer = new byte[1024];
                        while (fis.read(buffer) != -1) {
                            fileContent.append(new String(buffer));
                        }

                        result = fileContent.toString();
                    }
                    fis.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    public static Bitmap readBitmap(Context context, String fileName) {
        String path = com.varejodigital.utilities.FileManager.getWritablePath(context);
        if (path != null) {
            String filePath = path + "/" + fileName;
            File file = new File(filePath);
            if (file.exists()) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                return BitmapFactory.decodeFile(filePath, options);

            }
        }

        return null;
    }

    static public byte[] readBytes(Context context, InputStream istr) {
        if (istr == null) return null;

        byte[] result = null;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            int size;
            byte[] buffer = new byte[1024];
            while ((size = istr.read(buffer, 0, 1024)) >= 0) {
                outputStream.write(buffer, 0, size);
            }
            istr.close();
            result = outputStream.toByteArray();
            outputStream.close();
            ;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean clearCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                return deleteDir(dir);
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }

    public static boolean copyFile(String src, String dst) {
        InputStream in = null;
        OutputStream out = null;
        BufferedInputStream bin = null;
        BufferedOutputStream bout = null;
        try {
            in = new FileInputStream(src);
            out = new FileOutputStream(dst);
            bin = new BufferedInputStream(in);
            bout = new BufferedOutputStream(out);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = bin.read(buf)) > 0) {
                bout.write(buf, 0, len);
            }
            bin.close();
            bout.close();
            in.close();
            out.close();

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String filename(String filePath) {
        int sep = filePath.lastIndexOf("/");
        return filePath.substring(sep + 1, filePath.length());
    }

    public static String extension(String filePath) {
        int sep = filePath.lastIndexOf(".");
        return filePath.substring(sep + 1);
    }

    public static String basename(String filePath) {
        String fileName = filename(filePath);
        if (fileName.indexOf(".") > -1)
            return fileName.replace("." + extension(fileName), "");
        else
            return fileName;
    }

    public static String filenameTmp(String prefix) {
        String fileName = DateFormat.format("yyyyMMddhhmmss", new Date()).toString();
        if (prefix != null)
            fileName = prefix + fileName;
        return fileName;
    }
}
