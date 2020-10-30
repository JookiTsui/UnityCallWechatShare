package com.unityplugins.wechatshare;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WeChatController {

    public static IWXAPI api;

    private String _APP_ID = "";
    private static WeChatController Instance = null;

    public static WeChatController GetInstance() {
        if (Instance == null) {
            Instance = new WeChatController();
        }
        return Instance;
    }

    public void Init(Context content, String APP_ID) {
        _APP_ID = APP_ID;
        RegisterAppToWX(content);
    }

    private void RegisterAppToWX(Context content) {
        api = WXAPIFactory.createWXAPI(content, _APP_ID, true);
        api.registerApp(_APP_ID);
    }

    // 判断是否安装了微信
    public boolean IsWeChatInstalled() {

        return api.isWXAppInstalled();
    }

    // 分享链接至微信
    // scene: 分享到哪个场景 0 代表对话、1 代表朋友圈、2 代码收藏
    public void ShareWebpageToWX(int scene, String url, String title, String description) {
        // 设置网址
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = url;

        // 设置标题
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = title;
        msg.description = description;

        // 设置缩略图
        msg.thumbData = GetDrawableIconByPackageName(MainActivity.PackageName);

        // 构造一个发信请求
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = BuildTransaction("webpage");
        req.message = msg;
        req.scene = scene;

        // 发送请求给微信客户端
        api.sendReq(req);
    }

    // 分享文字至微信
    public void ShareTextToWX(int scene, String text) {
        //初始化一个 WXTextObject 对象，填写分享的文本内容
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = text;

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = BuildTransaction("text");
        req.message = msg;
        req.scene = scene;
        //调用api接口，发送数据到微信
        api.sendReq(req);
    }

    // 分享图片至微信
    public void ShareImageToWX(int scene, byte[] imgData) {
        WXImageObject imgObj = new WXImageObject(imgData);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        msg.thumbData = GetDrawableIconByPackageName(MainActivity.PackageName);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = BuildTransaction("img");
        req.message = msg;
        req.scene = scene;
        api.sendReq(req);
    }

    // 分享音乐至微信
    public void ShareMusicToWX(int scene, String musicUrl, String title, String description){
        //初始化一个WXMusicObject，填写url
        WXMusicObject music = new WXMusicObject();
        music.musicUrl= musicUrl;

        //用 WXMusicObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = music;
        msg.title = title;
        msg.description = description;
        msg.thumbData = GetDrawableIconByPackageName(MainActivity.PackageName);

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = BuildTransaction("music");
        req.message = msg;
        req.scene = scene;
        //调用api接口，发送数据到微信
        api.sendReq(req);
    }

    // 分享视频至微信
    public void ShareVideoToWX(int scene, String videoUrl, String title, String description){
        //初始化一个WXVideoObject，填写url
        WXVideoObject video = new WXVideoObject();
        video.videoUrl =videoUrl;

        //用 WXVideoObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = title;
        msg.description = description;
        msg.thumbData = GetDrawableIconByPackageName(MainActivity.PackageName);

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = BuildTransaction("video");
        req.message =msg;
        req.scene = scene;

        //调用api接口，发送数据到微信
        api.sendReq(req);
    }

    // 分享小程序至微信
    public void ShareMinniProgramToWX(int scene, String lowVersionUrl, String miniProgramAPPID, String path,
                                      String title, String description, byte[] coverImgData){
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = lowVersionUrl; // 兼容低版本的网页链接
        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = miniProgramAPPID;     // 小程序原始id
        miniProgramObj.path = path;   //小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = title;                    // 小程序消息title
        msg.description = description;        // 小程序消息desc
        msg.thumbData = coverImgData;           // 小程序消息封面图片，小于128k

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = BuildTransaction("miniProgram");
        req.message = msg;
        req.scene = scene;
        api.sendReq(req);
    }

    private String BuildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    // 通过包名获取应用的 Drawable icon 并转为 byte[] 数据
    private byte[] GetDrawableIconByPackageName(String packageName) {
        Drawable icon = GetAppIcon(packageName);
        Bitmap bitmap = FileTool.DrawableToBitmap(icon);
        return FileTool.BitmapToByte(bitmap);
    }

    /// 通过包名获取对应的 Drawable 数据
    private Drawable GetAppIcon(String packageName) {
        try {
            PackageManager pm = MainActivity.unityActivity.getPackageManager();
            ApplicationInfo info = pm.getApplicationInfo(packageName, 0);
            return info.loadIcon(pm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
