package com.unityplugins.wechatshare;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.unity3d.player.UnityPlayer;

public class MainActivity extends Activity {

    //unity项目启动时的上下文
    public static Activity unityActivity;
    private static Context context;
    // Unity项目中Player Settings中设置的包名
    public static String PackageName = "";

    public static void InitWeChat(String packageName, String APP_ID){
        PackageName = packageName;
        WeChatController.GetInstance().Init(getContext(), APP_ID);
    }

    // 判断是否安装了微信
    public static boolean IsWeChatInstalled() {
        return WeChatController.GetInstance().IsWeChatInstalled();
    }

    // scene: 分享到哪个场景
    // 0-对话、1-朋友圈、2-收藏
    public static void ShareWebpageToWX(int scene, String url, String title, String description){
        WeChatController.GetInstance().ShareWebpageToWX(scene, url, title, description);
    }

    // 分享文字至微信
    public static void ShareTextToWX(int scene, String text) {
        WeChatController.GetInstance().ShareTextToWX(scene, text);
    }

    // 分享图片至微信
    public static void ShareImageToWX(int scene, byte[] imgData) {
        WeChatController.GetInstance().ShareImageToWX(scene, imgData);
    }

    // 分享音乐至微信
    public static void ShareMusicToWX(int scene, String musicUrl, String title, String description) {
        WeChatController.GetInstance().ShareMusicToWX(scene, musicUrl, title, description);
    }

    // 分享视频至微信
    public static void ShareVideoToWX(int scene, String videoUrl, String title, String description) {
        WeChatController.GetInstance().ShareVideoToWX(scene, videoUrl, title, description);
    }

    // 分享小程序至微信
    public static void ShareMinniProgramToWX(int scene, String lowVersionUrl, String miniProgramAPPID, String path,
                                      String title, String description, byte[] coverImgData){
        WeChatController.GetInstance().ShareMinniProgramToWX(scene, lowVersionUrl, miniProgramAPPID, path, title, description, coverImgData);
    }

    /**
     * 利用反射机制获取unity项目的上下文
     * @return
     */
    private static Context getContext(){
        if(null == unityActivity) {
            try {
                Class<?> classtype = Class.forName("com.unity3d.player.UnityPlayer");
                Activity activity = (Activity) classtype.getDeclaredField("currentActivity").get(classtype);
                unityActivity = activity;
                context = activity;
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage());
            } catch (NoSuchFieldException e) {
                System.out.println(e.getMessage());
            }
        }
        return context;
    }

    // 安卓调用Unity的方法示例
    public static void SendMessageToUnity(String gameObjectName, String UnityMethodName){
        // 第一个参数：Unity中某个GameObject的gameObjectName
        // 第二个参数：挂在gameObjectName上的脚本的某个方法
        // 第三个参数：UnityMethodName这个方法接受的参数，这里是个String
        UnityPlayer.UnitySendMessage(gameObjectName, UnityMethodName, "Android发来的贺电");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
