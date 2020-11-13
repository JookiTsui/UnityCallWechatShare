# Unity实现微信分享功能
通过Android Studio接入微信开放平台SDK，再制作Android Lib编译成AAR文件，最终放入Unity项目中调用
**另：Unity iOS平台接入微信SDK请参考[这篇文章](https://www.yuque.com/jooki/hcm4it/vfn3uk)

# Tips
1. 项目已经生成了AAR文件，可在项目目录 build/outpus/aar 找到
2. 项目使用的微信开放平台SDK版本是6.6.4， 有需要使用最新版本SDK的可以自行前往[下载最新版本](https://bintray.com/wechat-sdk-team/maven/com.tencent.mm.opensdk%3Awechat-sdk-android-without-mta#files/com/tencent/mm/opensdk/wechat-sdk-android-without-mta)
3. 具体调用AAR中得到方法请参照项目根目录下的C#文件(WeChatController.cs), 文件开头有使用步骤，各个方法已尽可能写好了注释

# 具体实现过程
实现过程请参照[此链接: Unity接入第三方Android SDK——之微信开放平台](https://www.yuque.com/jooki/hcm4it/ft6for)  
这篇文章对Android开发有经验的人来说可能有点啰嗦，但是对新手来说是福音，这也是笔者第一次尝试Android开发

# 参考资料
项目的实现过程参考了网上不少资料，这里列举一些，有些可能有遗漏，如有侵权某位朋友的内容，望手下留情及时告知，必将立马更正
### Unity调用安卓
https://medium.com/jasons-devblog/creating-an-aar-plugin-for-unity-31ed219cb7f5  
https://zhuanlan.zhihu.com/p/119052124  
https://zhuanlan.zhihu.com/p/162967864  
### 微信开放平台对应APP的签名生成方法
https://www.cnblogs.com/details-666/p/signature.html
