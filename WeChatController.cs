using UnityEngine;

/// <summary>
/// Create By Jooki: https://www.yuque.com/jooki
/// 使用流程：
/// WeChatController是一个单例， 请自行给变量 _className 赋值
/// 使用前先调用 Init() 进行初始化，然后调用 IsWeChatAppInstalled() 检查用户设备上是否安装了微信;
/// 之后为保险起见调用 IsWeChatAppInstalled() 判断微信是否已经安装;
/// 最后就是按照需要分享的消息类型调用对应的方法
/// </summary>
public class WeChatController
{
    /// <summary>
    /// com.unityplugins.wechatshare是在Android Studio中创建的 Module,
    /// MainActivity 是一个Activity， 对外供C#调用的方法都写在这个Activity内
    /// </summary>
    private readonly string _className = "com.unityplugins.wechatshare.MainActivity";
    private string _APP_ID = "";
    private AndroidJavaClass mainActivityClass = null;

    private static WeChatController _instance;
    public static WeChatController Instance {
        get {
            if (_instance == null) {
                _instance = new WeChatController();
            }
            return _instance;
        }
    }

    /// <summary>
	/// 初始化微信SDK， APPID是用户在微信开放平台注册是所分配的应用唯一标识， 可在微信开放平台找到
	/// </summary>
	/// <param name="APPID"></param>
	public void Init(string APPID) {
        _APP_ID = APPID;
        mainActivityClass = new AndroidJavaClass(_className);
        // 第一个参数： MainActivity中的方法名
        // 第二、三个参数： 分别是SendMessageToUnity需要接收的第一和第二个参数，对比上面Java的代码
        mainActivityClass.CallStatic("InitWeChat", Application.identifier, _APP_ID);
    }

    /// <summary>
	/// 判断是否是否安装了微信
	/// </summary>
	/// <returns></returns>
    public bool IsWeChatAppInstalled() {
        return mainActivityClass.CallStatic<bool>("IsWeChatInstalled");
    }

    /// <summary>
	/// 分享链接至微信，缩略图用的是APP Icon
	/// </summary>
	/// <param name="scene">分享至什么场景, 0-对话、1-朋友圈、2-收藏</param>
	/// <param name="url">网页链接</param>
	/// <param name="title">标题</param>
	/// <param name="description">描述</param>
    public void ShareWebpageToWX(int scene, string url, string title, string description) {
        mainActivityClass.CallStatic("ShareWebpageToWX", scene, url, title, description);
    }

    /// <summary>
	/// 分享文字至微信
	/// </summary>
	/// <param name="scene">分享至什么场景, 0-对话、1-朋友圈、2-收藏</param>
	/// <param name="text">要分享的文本内容</param>
    public void ShareTextToWX(int scene, string text) {
        mainActivityClass.CallStatic("ShareTextToWX", scene, text);
    }

    /// <summary>
	/// 分享图片至微信
	/// </summary>
	/// <param name="scene">分享至什么场景, 0-对话、1-朋友圈、2-收藏</param>
	/// <param name="image">要分享的图片</param>
    public void ShareImageToWX(int scene, Texture2D image) {
        mainActivityClass.CallStatic("ShareImageToWX", scene, ImageToBytes(image));
    }

    /// <summary>
	/// 分享音乐至微信
	/// </summary>
	/// <param name="scene">分享至什么场景, 0-对话、1-朋友圈、2-收藏</param>
	/// <param name="musicUrl">音乐的URL</param>
	/// <param name="title">标题</param>
	/// <param name="description">描述</param>
    public void ShareMusicToWX(int scene, string musicUrl, string title, string description) {
        mainActivityClass.CallStatic("ShareMusicToWX", scene, musicUrl, title, description);
    }


    /// <summary>
	/// 分享视频至微信
	/// </summary>
	/// <param name="scene">分享至什么场景, 0-对话、1-朋友圈、2-收藏</param>
	/// <param name="videoUrl">视频的URL</param>
	/// <param name="title">标题</param>
	/// <param name="description">描述</param>
    public void ShareVideoToWX(int scene, string videoUrl, string title, string description) {
        mainActivityClass.CallStatic("ShareVideoToWX", scene, videoUrl, title, description);
    }

    /// <summary>
	/// 分享小程序至微信
	/// </summary>
	/// <param name="scene">分享至什么场景, 0-对话、1-朋友圈、2-收藏</param>
	/// <param name="lowVersionUrl">兼容低版本的网页链接</param>
	/// <param name="miniProgramAPPID">小程序原始ID, 获取方法：登录小程序管理后台-设置-基本设置-帐号信息</param>
	/// <param name="path">小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"</param>
	/// <param name="title">标题</param>
	/// <param name="description">描述</param>
	/// <param name="coverImg">分享封面图片</param>
    public void ShareMinniProgramToWX(int scene, string lowVersionUrl, string miniProgramAPPID, string path,
                                      string title, string description, Texture2D coverImg) {
        mainActivityClass.CallStatic("ShareMinniProgramToWX", scene, lowVersionUrl, miniProgramAPPID, path, title, description, ImageToBytes(coverImg));
    }

    /// <summary>
	/// 把一张Texture2D的图片信息转为Byte[]
	/// </summary>
	/// <param name="texture"></param>
	/// <returns></returns>
    private byte[] ImageToBytes(Texture2D texture) {
        var imageData = texture.EncodeToPNG();
        return imageData;
	}
}
