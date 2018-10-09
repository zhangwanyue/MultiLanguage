### 多语言配置
* 参考文档：
https://developer.android.com/guide/topics/resources/localization
https://developer.android.com/training/basics/supporting-devices/languages

* `res/`
新建`res/values-zh`文件夹存放中文语言配置
关于资源文件的存放，命名规则，调用规则：https://developer.android.com/guide/topics/resources/providing-resources

* 设置语言
将用户选择的语言保存到shared_preference中，并调用设置当前语言:
```android
if(SDK_INT >= 17){
    configuration.setLocale(userLocale);
    context.createConfigurationContext(configuration);
}else{
    configuration.locale = userLocale;
    resources.updateConfiguration(configuration, displayMetrics);
}
```


