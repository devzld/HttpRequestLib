# HttpRequestLib
一个封装了OKhttp和retrofit的简单易用的网络库

### 1.引入：

    implementation 'com.devzld:HttpRequestLib:1.0.0'
    

### 2.在APP的application里初始化：
    
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
            
### 3.使用：

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
	                    
	            