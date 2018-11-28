# HttpRequestLib
一个封装了OKhttp和retrofit的简单易用的网络库

+ 链式调用方式，简单易用
+ 配合Rxjava,支持请求取消
+ 支持每个请求使用完整的URL或使用全局的baseUrl
+ 自定义OKhttp的配置
+ 可以监听请求前和后的回调
+ 可以方便地设置全局或单个header

### 1.引入：
```gradle
    implementation 'com.devzld:HttpRequestLib:1.0.0'
```

### 2.在APP的application里初始化：

```java    
    HttpUtil.getInstance().init(this, true, new IHttpLoader() {
                @Override
                public Retrofit getRetrofit() {
                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    
                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(30, TimeUnit.SECONDS)
                            .addInterceptor(loggingInterceptor)
                            .retryOnConnectionFailure(true);
    
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://gank.io")
                            .client(builder.build())
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                    return retrofit;
                }
            });
```

其中bool值是是否是调试模式
            
### 3.使用：

```java
	new HttpRequest("/api/random/data/Android/20")
	                .params("param1", "param1")
	                .headers("header1", "header1")
	                .callback(new IHttpCallback() {
	                    @Override
	                    public void success(String url, String res) {
	                        Toast.makeText(MainActivity.this, res, Toast.LENGTH_LONG).show();
	                    }
	
	                    @Override
	                    public void error(String url, int code, String msg) {
	                        Toast.makeText(MainActivity.this, code + msg, Toast.LENGTH_LONG).show();
	                    }
	                })
	                .get(mDisposable);
```
	                    
	            