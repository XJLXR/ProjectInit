简单说明 
=======
Newlife 基于Google Material Design设计开发，项目采取的是MVP架构开发，由于还是摸索阶段，很多不是规范主要用于熟悉现在主流框架和MVP模式RxJava+Retrofit2+Okhttp 实现API数据请求以及缓存
-------
####首先详细讲解一下RxJava+Retrofit2+Okhttp  第一步:导包

```Java
    compile 'io.reactivex.rxjava2:rxjava:2.x.y'
    compile 'io.reactivex.rxjava2:rxandroid:2.0.1'
    compile 'com.squareup.retrofit2:retrofit:2.2.0'
    compile 'com.squareup.retrofit2:converter-gson:2.2.0'
    compile 'com.squareup.retrofit2:adapter-rxjava2:2.2.0'
```

第二步:新建API接口
```Java
public interface WXApiServer {

	/**
	 * https://api.tianapi.com/wxnew/?key=APIKEY&num=10
	 *
	 * key	string	是	urlParam	API密钥（请在个人中心获取）	用户自己的key
	 num	int	是	urlParam	指定返回数量，最大50	10
	 src	string	否	urlParam	指定来源为某微信公众号	例如：人民日报
	 rand	int	否	urlParam	参数值为1则随机获取	0
	 word	string	否	urlParam	检索关键词	上海
	 page	int	否	urlParam	翻页，每页输出数量跟随num参数	1，若指定文章来源则必带此参数
	 */

	String BASE_WX_URL = "https://api.tianapi.com/";
	String IPIKEY="a168a48492d240fae6fd2c6b6ea4e7b4";

	@GET("wxnew")
	Observable<WXNews> getNewsList(@Query("key") String key, @Query("num") int num, @Query("page") int page);

	@GET("wxnew")
	Observable<WXNews> getNewsList(@QueryMap Map<String, String> map);

}

```
第三步:新建retrofit抽象类
```Java

public class HttpMethod {

	private static final int DEFAULT_TIMEOUT = 5;

	private static HttpMethod  INSTANCE;
	private OkHttpClient sOkHttpClient;

	/**
	 * 无网络时设缓存有效期为两天
	 */
	public static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;

	/**
	 * 有网时设置缓存为1天
	 */

	public static final long CACHE_STALE_AGE = 60 * 60;

	/**
	 * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
	 * max-stale 指示客户机可以接收超出超时期间的响应消息。如果指定max-stale消息的值，那么客户机可接收超出超时期指定值之内的响应消息。
	 */
	public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;

	/**
	 * 查询网络的Cache-Control设置，头部Cache-Control设为max-age=0
	 * (假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)时则不会使用缓存而请求服务器
	 */
	public static final String CACHE_CONTROL_AGE = "max-age=0";

	private final Retrofit wxRetrofit;
	private final WXApiServer wxApiServer;
	private Cache mCache;

	public WXApiServer getWxApiServer(){
		return wxApiServer;
	}


	private HttpMethod(){
 
		wxRetrofit = new Retrofit.Builder()
				.client(getsOkHttpClient())
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.baseUrl(WXApiServer.BASE_WX_URL)
				.build();

		wxApiServer = wxRetrofit.create(WXApiServer.class);
	}


	/**
	 * 根据传入的baseUrl，和api创建retrofit
	 */
	private <T> T createApi(Class<T> clazz, String baseUrl) {

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(baseUrl)
				.client(getsOkHttpClient())
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.build();

		return retrofit.create(clazz);
	}

	public static HttpMethod getInstance(){
		if (INSTANCE == null){
			synchronized (HttpMethod.class){
				INSTANCE = new HttpMethod();
			}
		}
		return INSTANCE;
	}

	private  OkHttpClient getsOkHttpClient(){

		if (sOkHttpClient == null){
			synchronized (HttpMethod.class){
				mCache = new Cache(new File(MyApplication.getAppContext().getCacheDir(),"HttpCache"),1024*1024*100);
				if (sOkHttpClient == null){
					sOkHttpClient = new OkHttpClient.Builder()
							.cache(mCache)
							.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
							.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
							.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
							.retryOnConnectionFailure(true)
							.addNetworkInterceptor(loggingInterceptor)
							.addInterceptor(mRewriteCacheControlInterceptor)
							.addInterceptor(mLoggingInterceptor)
							.build();
				}
			}
		}

		return sOkHttpClient;
	}



	/**
	 * //拦截请求和响应日志并输出，其实有很多封
	 * 装好的日志拦截插件，大家也可以根据个人喜好选择。
	 */

	private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
		@Override
		public void log(String message) {
			Log.i("HttpManager",message);
		}
	}).setLevel(HttpLoggingInterceptor.Level.BODY);


	/**
	 * 云端响应头拦截器，用来配置缓存策略
	 * Dangerous interceptor that rewrites the server's cache-control header.
	 */
	private final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
		@Override
		public Response intercept(Chain chain) throws IOException {
			Request request = chain.request();

			//有网络时只从网络获取
			if (CommonUtil.isNetworkAvailable(MyApplication.getAppContext())) {
				request = request.newBuilder()
						.cacheControl(CacheControl.FORCE_NETWORK)
						.build();
			}else {
				//无网络时只从缓存中读取
				request = request.newBuilder()
						.cacheControl(CacheControl.FORCE_CACHE)
						.build();
				MyLogger.kLog().d("no nets");
			}


			Response originalResponse = chain.proceed(request);
			if (CommonUtil.isNetworkAvailable(MyApplication.getAppContext())) {
				//有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
				String cacheControl = request.cacheControl().toString();
				return originalResponse.newBuilder()
						.removeHeader("Pragma")
						.header("Cache-Control","public, max-age=" + CACHE_STALE_AGE)
						.build();
			} else {
				return originalResponse.newBuilder()
						.removeHeader("Pragma")
						.header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_STALE_SEC)
						.build();
			}
		}
	};


	private final Interceptor mLoggingInterceptor = new Interceptor() {
		@Override
		public Response intercept(Chain chain) throws IOException {
			Request request = chain.request();
			long t1 = System.nanoTime();
			i(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));
			Response response = chain.proceed(request);
			long t2 = System.nanoTime();
			LogUtils.i(String.format(Locale.getDefault(), "Received response for %s in %.1fms%n%s",
					response.request().url(), (t2 - t1) / 1e6d, response.headers()));
			return response;
		}
	};

}
```
第四步 获取数据
```java
public class WxModel  {

	/**
	 * 获取数据
	 * @param map
	 * @return
	 */
	public Observable<WXNews> getNewsList(Map<String,String> map){
		return HttpMethod.getInstance().getWxApiServer().getNewsList(map)
				.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}
}
```

## MvpModel和NormelModel是在NewLife上进行优化和抽取的用于项目开始搭建，可以使用MVP和MVC模式，实现网络加载错误，正在加载，数据为空，重新加载等
 ```java
 public interface BaseViewI<V> {


	/**
	 * 数据加载失败
	 * tag==1,正常错误，可以只显示toast
	 * tag=0,非正常错误，需要考虑显示错误界面
	 * */
	public void showDataError(String errorMessage, int tag);

	/**
	 *数据加载成功
	 * */
	public void showDataSuccess(V datas);

	/**
	 * toggle show loading
	 * @param toggle
	 * 正在加载中布局
	 */
	public void toggleShowLoading(boolean toggle, String msg);


	/**
	 * toggle show empty
	 * @param toggle
	 * 空数据布局
	 */
	public void toggleShowEmpty(boolean toggle, String msg, View.OnClickListener onClickListener);

	/**
	 * toggle show error
	 * @param toggle
	 * 数据加载错误布局
	 */
	public void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener);


	/**
	 * toggle show network error
	 * @param toggle
	 * 网络错误布局
	 */
	public void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener);

	/**
	 * 重新加载
	 */
	public void toggleRestore();
}

 ```
