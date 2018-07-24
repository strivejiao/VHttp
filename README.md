# VHttp网络库使用指南
VHttp网络基础库是基于Retrofit+RxJava开发的，通过封装Retrofit和RxJava，打造的轻便、易用、移动的网络访问库；
## 引入
在gradle中引入：
```
 api 'com.github.strivejiao:VHttp:1.0.2'
```
## 初始化
在Application中添加以下代码：
```
VHttp.CONFIG()
             .baseUrl(baseUrl)
             .httpHeaders(headers)
             .log(true)
             .interceptor(new HttpLogInterceptor().setLevel(HttpLogInterceptor.Level.BODY));
```


**basheUrl：** 基础Url，即你要访问的服务器地址；

**httpHeaders：** 通用请求头，访问的服务器添加请求头数据，是个map集合
```
 Map<String, String> headers = new HashMap<>();
 headers.put("mKey", "mobile");
 headers.put("version", "V1.0%281%29");
 headers.put("imei", "351670062419259");
```

**log：** log日志开关 false是关

**interceptor：** 拦截器，可以根据自己的需求传入相应的拦截器，以上示例传入的是日志拦截器，并说明了日志拦截的等级（日志输出的内容）；

## GET请求示例
```
        VHttp.GET(suffixUrl).request(new ACallback<Object>() {

            @Override
            public void onSuccess(Object result) {

            }

            @Override
            public void onFail(int errorCode, String errorMsg) {

            }
        });
```
**suffixUrl** 是你访问的接口地址

**ACallback<Object>** Object 是接口返回的bean对象，可以根据需要自定义

## POST请求示例
```
        VHttp.POST(suffixUrl)
                .tag(netTag)
                .addForm(key, value)
                .request(new ACallback<Object>() {

                    @Override
                    public void onSuccess(Object result) {

                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {

                    }
                });

```
**tag** 是你请求接口的标志

**addForm** 是key value形式的请求参数，示例中只添加了一个参数键值对，也可以添加Map集合形式的参数
```
        VHttp.POST(suffixUrl)
                .tag(netTag)
                .addParams(Map)
```
如果我们在POST请求中想获取接口返回的源数据，即未解析的json字符串，可以按照以下代码处理：
```
        VHttp.POST(suffixUrl)
                .tag(netTag)
                .addParams(Map)
                .request(new ACallback<String>() {

                    @Override
                    public void onSuccess(String result) {

                    }

                    @Override
                    public void onFail(int errorCode, String errorMsg) {

                    }
                });
```
即在ACallback回调中传入String即可
