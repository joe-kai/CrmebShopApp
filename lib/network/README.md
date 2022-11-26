### 使用说明
可在BaseActivity实现LoadingEvent,ToastEvent,StateEvent用来监听网络请求变化

在Application中，通过RetrofitBuilder.initInterceptor(XXXInterceptor())添加拦截器
