package com.unityplugins.wechatshare;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;


// 该类用来作为文件工具
// 2、Drawable 转为 Bitmap
// 3、Bitmap 转为 byte[]
public class FileTool {

    /// Bitmap 转为 byte[]
    public synchronized static byte[] BitmapToByte(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /// Drawable 转为 Bitmap
    public synchronized static Bitmap DrawableToBitmap(Drawable drawable) {
        return (((android.graphics.drawable.BitmapDrawable) drawable).getBitmap());
    }
}
