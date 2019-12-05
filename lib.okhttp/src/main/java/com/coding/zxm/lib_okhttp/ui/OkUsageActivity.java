package com.coding.zxm.lib_okhttp.ui;

import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.coding.zxm.lib_okhttp.R;
import com.coding.zxm.lib_okhttp.interceptor.GzipRequestInterceptor;
import com.coding.zxm.lib_okhttp.interceptor.LoggingInterceptor;
import com.coding.zxm.libcore.ui.BaseActivity;
import com.coding.zxm.libutil.DisplayUtil;
import com.itkacher.okhttpprofiler.OkHttpProfilerInterceptor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;
import okio.Buffer;
import okio.BufferedSink;

/**
 * Created by ZhangXinmin on 2019/5/28.
 * Copyright (c) 2018 . All rights reserved.
 */
public class OkUsageActivity extends BaseActivity implements View.OnClickListener {

    private ExecutorService mHttpPool;
    private TextView mResultTv;

    @Override
    protected Object setLayout() {
        return R.layout.activity_okhttp_usage;
    }

    @Override
    protected void initParamsAndValues() {
        Intent intent = getIntent();
        if (intent != null) {
            final String label = intent.getStringExtra(DisplayUtil.PARAMS_LABEL);
            if (!TextUtils.isEmpty(label)) {
                setTitle(label);
            }
        }

        mHttpPool = Executors.newSingleThreadExecutor();
    }

    @Override
    protected void initViews() {
        mResultTv = findViewById(R.id.tv_result);

        findViewById(R.id.btn_do_get).setOnClickListener(this);
        findViewById(R.id.btn_do_get).setOnClickListener(this);
        findViewById(R.id.btn_accessing_headers).setOnClickListener(this);
        findViewById(R.id.btn_post_form).setOnClickListener(this);
        findViewById(R.id.btn_multipart_request).setOnClickListener(this);
        findViewById(R.id.btn_response_caching).setOnClickListener(this);
        findViewById(R.id.btn_pre_call).setOnClickListener(this);
        findViewById(R.id.btn_authorization).setOnClickListener(this);
        findViewById(R.id.btn_log_interceptor).setOnClickListener(this);
        findViewById(R.id.btn_network_interceptor).setOnClickListener(this);
        findViewById(R.id.btn_rewriting_request).setOnClickListener(this);
        findViewById(R.id.btn_customizing_certificates).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int viewId = v.getId();
        if (viewId == R.id.btn_do_get) {
//            asynGet();
            synGet();
        } else if (viewId == R.id.btn_do_post) {
//            postString();
            postStreaming();
        } else if (viewId == R.id.btn_accessing_headers) {
            accessingHeaders();
        } else if (viewId == R.id.btn_post_form) {
            postFormParameters();
        } else if (viewId == R.id.btn_multipart_request) {
            postMultipartRequest();
        } else if (viewId == R.id.btn_response_caching) {
            responseCaching();
        } else if (viewId == R.id.btn_pre_call) {
            preCallConfiguration();
        } else if (viewId == R.id.btn_authorization) {
            authentication();
        } else if (viewId == R.id.btn_log_interceptor) {
            addLogInterceptor();
        } else if (viewId == R.id.btn_network_interceptor) {
            addNetworkInterceptor();
        } else if (viewId == R.id.btn_rewriting_request) {
            rewritingRequest();
        } else if (viewId == R.id.btn_customizing_certificates) {
            customizingCertificates();
        }
    }


    /**
     * 向服务器发送一个字符串
     * 注意:小于1M
     */
    private void postString() {

        final MediaType MEDIA_TYPE_MARKDOWN
                = MediaType.parse("text/x-markdown; charset=utf-8");

        final OkHttpClient client = new OkHttpClient();
        String postBody = ""
                + "Releases\n"
                + "--------\n"
                + "\n"
                + " * _1.0_ May 6, 2013\n"
                + " * _1.1_ June 15, 2013\n"
                + " * _1.2_ August 11, 2013\n";
        Request request = new Request.Builder()
                .url("https://api.github.com/markdown/raw")
                .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, postBody))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Log.d(TAG, response.body().string());

        } catch (IOException e) {
            Log.e(TAG, "Catch an exception : " + e.toString());
        }
    }

    /**
     * 向服务器发送大文件
     */
    private void postStreaming() {
        final MediaType MEDIA_TYPE_MARKDOWN
                = MediaType.parse("text/x-markdown; charset=utf-8");

        final OkHttpClient client = new OkHttpClient();

        mHttpPool.execute(new Runnable() {
            @Override
            public void run() {
                final RequestBody requestBody = new RequestBody() {
                    @Override
                    public MediaType contentType() {
                        return MEDIA_TYPE_MARKDOWN;
                    }

                    @Override
                    public void writeTo(BufferedSink sink) throws IOException {
                        sink.writeUtf8("Numbers\n");
                        sink.writeUtf8("-------\n");
                        for (int i = 2; i <= 997; i++) {
                            sink.writeUtf8(String.format(" * %s = %s\n", i, factor(i)));
                        }
                    }

                    private String factor(int n) {
                        for (int i = 2; i < n; i++) {
                            int x = n / i;
                            if (x * i == n) return factor(x) + " × " + i;
                        }
                        return Integer.toString(n);
                    }
                };

                final Request request = new Request.Builder()
                        .url("https://api.github.com/markdown/raw")
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    Log.d(TAG, "postStreaming()..result : " + response.body().string());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 异步请求
     */
    private void asynGet() {

        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("https://publicobject.com/helloworld.txt")
                .build();

        final Call call = client.newCall(request);
        //
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //失败
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final ResponseBody body = response.body();
                if (!response.isSuccessful()) {
                    throw new IOException("Unexcepted code " + response);
                }
                //请求头
                final Headers headers = response.headers();
                final int count = headers.size();
                for (int i = 0; i < count; i++) {
                    Log.d(TAG, headers.name(i) + " : " + headers.value(i));
                }
                Log.d(TAG, "result : " + body.string());

            }
        });
    }

    /**
     * 同步请求
     * 不能在主线程中操作
     */
    private void synGet() {

        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("https://publicobject.com/helloworld.txt")
                .build();

        mHttpPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final Response response = client.newCall(request).execute();
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0; i < responseHeaders.size(); i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Headers
     * 不能在主线程中操作
     */
    private void accessingHeaders() {
        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url("https://api.github.com/repos/square/okhttp/issues")
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build();

        mHttpPool.execute(new Runnable() {
            @Override
            public void run() {

                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    System.out.println("Server: " + response.header("Server"));
                    System.out.println("Date: " + response.header("Date"));
                    System.out.println("Vary: " + response.headers("Vary"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Posting form parameters
     */
    private void postFormParameters() {
        final OkHttpClient client = new OkHttpClient();
        mHttpPool.execute(new Runnable() {
            @Override
            public void run() {
                final RequestBody formBody = new FormBody.Builder()
                        .add("search", "Jurassic Park")
                        .build();

                final Request request = new Request.Builder()
                        .url("https://en.wikipedia.org/w/index.php")
                        .post(formBody)
                        .build();
                try {
                    final Response response = client.newCall(request).execute();
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    Log.d(TAG, "postFormParameters()..result : " + response.body().string());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Posting a multipart request
     */
    private void postMultipartRequest() {
        final String IMGUR_CLIENT_ID = "...";

        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

        final OkHttpClient client = new OkHttpClient();

        // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", "Square Logo")
                .addFormDataPart("image", "logo-square.png",
                        RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png")))
                .build();

        final Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                .url("https://api.imgur.com/3/image")
                .post(requestBody)
                .build();

        mHttpPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final Response response = client.newCall(request).execute();
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);
                    Log.d(TAG, "postMultipartRequest()..result : " + response.body().string());
                } catch (IOException e) {
                    Log.d(TAG, "postMultipartRequest()..error : " + e.toString());
                }
            }
        });
    }

    /**
     * Response Caching
     */
    private void responseCaching() {
        final String okhttpCacheDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
        final int cacheSize = 10 * 1024 * 1024; // 10 MiB
        final Cache cache = new Cache(new File(okhttpCacheDir, "okhttp_cache"), cacheSize);
        final OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .build();

        final Request request = new Request.Builder()
                .url("http://publicobject.com/helloworld.txt")
                .build();
        mHttpPool.execute(new Runnable() {
            @Override
            public void run() {

                String response1Body = "1";
                try {
                    final Response response1 = client.newCall(request).execute();
                    if (!response1.isSuccessful())
                        throw new IOException("Unexpected code " + response1);
                    response1Body = response1.body().string();
                    System.out.println("Response 1 response:          " + response1);
                    System.out.println("Response 1 cache response:    " + response1.cacheResponse());
                    System.out.println("Response 1 network response:  " + response1.networkResponse());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String response2Body = "2";
                try (Response response2 = client.newCall(request).execute()) {
                    if (!response2.isSuccessful())
                        throw new IOException("Unexpected code " + response2);

                    response2Body = response2.body().string();
                    System.out.println("Response 2 response:          " + response2);
                    System.out.println("Response 2 cache response:    " + response2.cacheResponse());
                    System.out.println("Response 2 network response:  " + response2.networkResponse());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Response 2 equals Response 1? " + response1Body.equals(response2Body));
            }
        });


    }

    /**
     * Per-call Configuration
     */
    private void preCallConfiguration() {
        final OkHttpClient client = new OkHttpClient();

        final Request request = new Request.Builder()
                .url("http://httpbin.org/delay/1")
                .build();

        mHttpPool.execute(new Runnable() {
            @Override
            public void run() {


                //500ms
                try {
                    final OkHttpClient client1 = client.newBuilder()
                            .readTimeout(500, TimeUnit.MILLISECONDS)
                            .build();
                    final Response response = client1.newCall(request).execute();
                    Log.d(TAG, "client1..response : " + response.body().string());
                } catch (IOException e) {
                    Log.e(TAG, "client1 failed : " + e.toString());
                }

                //500ms
                try {
                    final OkHttpClient client2 = client.newBuilder()
                            .readTimeout(3000, TimeUnit.MILLISECONDS)
                            .build();
                    final Response response = client2.newCall(request).execute();
                    Log.d(TAG, "client2..response : " + response.body().string());
                } catch (IOException e) {
                    Log.e(TAG, "client2 failed : " + e.toString());
                }
            }
        });

    }

    /**
     * Handling authentication
     */
    private void authentication() {
        final OkHttpClient client = new OkHttpClient.Builder()
                .authenticator(new Authenticator() {
                    @Override
                    public Request authenticate(Route route, Response response) throws IOException {
                        if (response.request().header("Authorization") != null) {
                            return null;
                        }
                        Log.d(TAG, "Authorization for response : " + response);
                        Log.d(TAG, "challenges : " + response.challenges());
                        final String credential = Credentials.basic("jesse", "password1");
                        return response.request()
                                .newBuilder()
                                .header("Authorization", credential)
                                .build();
                    }
                }).build();

        mHttpPool.execute(new Runnable() {
            @Override
            public void run() {
                final Request request = new Request.Builder()
                        .url("http://publicobject.com/secrets/hellosecret.txt")
                        .build();

                try {
                    final Response response = client.newCall(request).execute();
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);
                    Log.d(TAG, "response : " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Add log interceptor.
     */
    private void addLogInterceptor() {
        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .build();

        final Request request = new Request.Builder()
                .url("http://www.publicobject.com/helloworld.txt")
                .addHeader("User-Agent", getString(R.string.app_name))
                .build();

        mHttpPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        Log.e(TAG, "请求失败~");
                    }
                    Log.d(TAG, "response : " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Network Interceptors
     */
    private void addNetworkInterceptor() {
        final OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new LoggingInterceptor())
                .build();

        final Request request = new Request.Builder()
                .url("http://www.publicobject.com/helloworld.txt")
                .addHeader("User-Agent", getString(R.string.app_name))
                .build();

        mHttpPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        Log.e(TAG, "请求失败~");
                    }
                    Log.d(TAG, "response : " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Rewriting Responses
     */
    private void rewritingRequest() {
        final OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new GzipRequestInterceptor())
                .addInterceptor(new OkHttpProfilerInterceptor())
                .build();

        final Request request = new Request.Builder()
                .url("http://www.publicobject.com/helloworld.txt")
                .addHeader("User-Agent", getString(R.string.app_name))
                .build();

        mHttpPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    final Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        Log.e(TAG, "请求失败~");
                    }
                    Log.d(TAG, "response : " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    /**
     * Customizing Trusted Certificates
     */
    private void customizingCertificates() {
        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;
        try {
            trustManager = trustManagerForCertificates(trustedCertificatesInputStream());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }

        final OkHttpClient client = new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                .build();

        final Request request = new Request.Builder()
                .url("https://publicobject.com/helloworld.txt")
                .build();

        mHttpPool.execute(new Runnable() {
            @Override
            public void run() {
                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0; i < responseHeaders.size(); i++) {
                        Log.d(TAG, responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    Log.d(TAG, response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Returns a trust manager that trusts {@code certificates} and none other. HTTPS services whose
     * certificates have not been signed by these certificates will fail with a {@code
     * SSLHandshakeException}.
     *
     * <p>This can be used to replace the host platform's built-in trusted certificates with a custom
     * set. This is useful in development where certificate authority-trusted certificates aren't
     * available. Or in production, to avoid reliance on third-party certificate authorities.
     *
     * <p>See also {@link CertificatePinner}, which can limit trusted certificates while still using
     * the host platform's built-in trust store.
     *
     * <h3>Warning: Customizing Trusted Certificates is Dangerous!</h3>
     *
     * <p>Relying on your own trusted certificates limits your server team's ability to update their
     * TLS certificates. By installing a specific set of trusted certificates, you take on additional
     * operational complexity and limit your ability to migrate between certificate authorities. Do
     * not use custom trusted certificates in production without the blessing of your server's TLS
     * administrator.
     */
    private X509TrustManager trustManagerForCertificates(InputStream in)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        char[] password = "password".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    /**
     * Returns an input stream containing one or more certificate PEM files. This implementation just
     * embeds the PEM files in Java strings; most applications will instead read this from a resource
     * file that gets bundled with the application.
     */
    private InputStream trustedCertificatesInputStream() {
        // PEM files for root certificates of Comodo and Entrust. These two CAs are sufficient to view
        // https://publicobject.com (Comodo) and https://squareup.com (Entrust). But they aren't
        // sufficient to connect to most HTTPS sites including https://godaddy.com and https://visa.com.
        // Typically developers will need to get a PEM file from their organization's TLS administrator.
        String comodoRsaCertificationAuthority = ""
                + "-----BEGIN CERTIFICATE-----\n"
                + "MIIF2DCCA8CgAwIBAgIQTKr5yttjb+Af907YWwOGnTANBgkqhkiG9w0BAQwFADCB\n"
                + "hTELMAkGA1UEBhMCR0IxGzAZBgNVBAgTEkdyZWF0ZXIgTWFuY2hlc3RlcjEQMA4G\n"
                + "A1UEBxMHU2FsZm9yZDEaMBgGA1UEChMRQ09NT0RPIENBIExpbWl0ZWQxKzApBgNV\n"
                + "BAMTIkNPTU9ETyBSU0EgQ2VydGlmaWNhdGlvbiBBdXRob3JpdHkwHhcNMTAwMTE5\n"
                + "MDAwMDAwWhcNMzgwMTE4MjM1OTU5WjCBhTELMAkGA1UEBhMCR0IxGzAZBgNVBAgT\n"
                + "EkdyZWF0ZXIgTWFuY2hlc3RlcjEQMA4GA1UEBxMHU2FsZm9yZDEaMBgGA1UEChMR\n"
                + "Q09NT0RPIENBIExpbWl0ZWQxKzApBgNVBAMTIkNPTU9ETyBSU0EgQ2VydGlmaWNh\n"
                + "dGlvbiBBdXRob3JpdHkwggIiMA0GCSqGSIb3DQEBAQUAA4ICDwAwggIKAoICAQCR\n"
                + "6FSS0gpWsawNJN3Fz0RndJkrN6N9I3AAcbxT38T6KhKPS38QVr2fcHK3YX/JSw8X\n"
                + "pz3jsARh7v8Rl8f0hj4K+j5c+ZPmNHrZFGvnnLOFoIJ6dq9xkNfs/Q36nGz637CC\n"
                + "9BR++b7Epi9Pf5l/tfxnQ3K9DADWietrLNPtj5gcFKt+5eNu/Nio5JIk2kNrYrhV\n"
                + "/erBvGy2i/MOjZrkm2xpmfh4SDBF1a3hDTxFYPwyllEnvGfDyi62a+pGx8cgoLEf\n"
                + "Zd5ICLqkTqnyg0Y3hOvozIFIQ2dOciqbXL1MGyiKXCJ7tKuY2e7gUYPDCUZObT6Z\n"
                + "+pUX2nwzV0E8jVHtC7ZcryxjGt9XyD+86V3Em69FmeKjWiS0uqlWPc9vqv9JWL7w\n"
                + "qP/0uK3pN/u6uPQLOvnoQ0IeidiEyxPx2bvhiWC4jChWrBQdnArncevPDt09qZah\n"
                + "SL0896+1DSJMwBGB7FY79tOi4lu3sgQiUpWAk2nojkxl8ZEDLXB0AuqLZxUpaVIC\n"
                + "u9ffUGpVRr+goyhhf3DQw6KqLCGqR84onAZFdr+CGCe01a60y1Dma/RMhnEw6abf\n"
                + "Fobg2P9A3fvQQoh/ozM6LlweQRGBY84YcWsr7KaKtzFcOmpH4MN5WdYgGq/yapiq\n"
                + "crxXStJLnbsQ/LBMQeXtHT1eKJ2czL+zUdqnR+WEUwIDAQABo0IwQDAdBgNVHQ4E\n"
                + "FgQUu69+Aj36pvE8hI6t7jiY7NkyMtQwDgYDVR0PAQH/BAQDAgEGMA8GA1UdEwEB\n"
                + "/wQFMAMBAf8wDQYJKoZIhvcNAQEMBQADggIBAArx1UaEt65Ru2yyTUEUAJNMnMvl\n"
                + "wFTPoCWOAvn9sKIN9SCYPBMtrFaisNZ+EZLpLrqeLppysb0ZRGxhNaKatBYSaVqM\n"
                + "4dc+pBroLwP0rmEdEBsqpIt6xf4FpuHA1sj+nq6PK7o9mfjYcwlYRm6mnPTXJ9OV\n"
                + "2jeDchzTc+CiR5kDOF3VSXkAKRzH7JsgHAckaVd4sjn8OoSgtZx8jb8uk2Intzna\n"
                + "FxiuvTwJaP+EmzzV1gsD41eeFPfR60/IvYcjt7ZJQ3mFXLrrkguhxuhoqEwWsRqZ\n"
                + "CuhTLJK7oQkYdQxlqHvLI7cawiiFwxv/0Cti76R7CZGYZ4wUAc1oBmpjIXUDgIiK\n"
                + "boHGhfKppC3n9KUkEEeDys30jXlYsQab5xoq2Z0B15R97QNKyvDb6KkBPvVWmcke\n"
                + "jkk9u+UJueBPSZI9FoJAzMxZxuY67RIuaTxslbH9qh17f4a+Hg4yRvv7E491f0yL\n"
                + "S0Zj/gA0QHDBw7mh3aZw4gSzQbzpgJHqZJx64SIDqZxubw5lT2yHh17zbqD5daWb\n"
                + "QOhTsiedSrnAdyGN/4fy3ryM7xfft0kL0fJuMAsaDk527RH89elWsn2/x20Kk4yl\n"
                + "0MC2Hb46TpSi125sC8KKfPog88Tk5c0NqMuRkrF8hey1FGlmDoLnzc7ILaZRfyHB\n"
                + "NVOFBkpdn627G190\n"
                + "-----END CERTIFICATE-----\n";
        String entrustRootCertificateAuthority = ""
                + "-----BEGIN CERTIFICATE-----\n"
                + "MIIEkTCCA3mgAwIBAgIERWtQVDANBgkqhkiG9w0BAQUFADCBsDELMAkGA1UEBhMC\n"
                + "VVMxFjAUBgNVBAoTDUVudHJ1c3QsIEluYy4xOTA3BgNVBAsTMHd3dy5lbnRydXN0\n"
                + "Lm5ldC9DUFMgaXMgaW5jb3Jwb3JhdGVkIGJ5IHJlZmVyZW5jZTEfMB0GA1UECxMW\n"
                + "KGMpIDIwMDYgRW50cnVzdCwgSW5jLjEtMCsGA1UEAxMkRW50cnVzdCBSb290IENl\n"
                + "cnRpZmljYXRpb24gQXV0aG9yaXR5MB4XDTA2MTEyNzIwMjM0MloXDTI2MTEyNzIw\n"
                + "NTM0MlowgbAxCzAJBgNVBAYTAlVTMRYwFAYDVQQKEw1FbnRydXN0LCBJbmMuMTkw\n"
                + "NwYDVQQLEzB3d3cuZW50cnVzdC5uZXQvQ1BTIGlzIGluY29ycG9yYXRlZCBieSBy\n"
                + "ZWZlcmVuY2UxHzAdBgNVBAsTFihjKSAyMDA2IEVudHJ1c3QsIEluYy4xLTArBgNV\n"
                + "BAMTJEVudHJ1c3QgUm9vdCBDZXJ0aWZpY2F0aW9uIEF1dGhvcml0eTCCASIwDQYJ\n"
                + "KoZIhvcNAQEBBQADggEPADCCAQoCggEBALaVtkNC+sZtKm9I35RMOVcF7sN5EUFo\n"
                + "Nu3s/poBj6E4KPz3EEZmLk0eGrEaTsbRwJWIsMn/MYszA9u3g3s+IIRe7bJWKKf4\n"
                + "4LlAcTfFy0cOlypowCKVYhXbR9n10Cv/gkvJrT7eTNuQgFA/CYqEAOwwCj0Yzfv9\n"
                + "KlmaI5UXLEWeH25DeW0MXJj+SKfFI0dcXv1u5x609mhF0YaDW6KKjbHjKYD+JXGI\n"
                + "rb68j6xSlkuqUY3kEzEZ6E5Nn9uss2rVvDlUccp6en+Q3X0dgNmBu1kmwhH+5pPi\n"
                + "94DkZfs0Nw4pgHBNrziGLp5/V6+eF67rHMsoIV+2HNjnogQi+dPa2MsCAwEAAaOB\n"
                + "sDCBrTAOBgNVHQ8BAf8EBAMCAQYwDwYDVR0TAQH/BAUwAwEB/zArBgNVHRAEJDAi\n"
                + "gA8yMDA2MTEyNzIwMjM0MlqBDzIwMjYxMTI3MjA1MzQyWjAfBgNVHSMEGDAWgBRo\n"
                + "kORnpKZTgMeGZqTx90tD+4S9bTAdBgNVHQ4EFgQUaJDkZ6SmU4DHhmak8fdLQ/uE\n"
                + "vW0wHQYJKoZIhvZ9B0EABBAwDhsIVjcuMTo0LjADAgSQMA0GCSqGSIb3DQEBBQUA\n"
                + "A4IBAQCT1DCw1wMgKtD5Y+iRDAUgqV8ZyntyTtSx29CW+1RaGSwMCPeyvIWonX9t\n"
                + "O1KzKtvn1ISMY/YPyyYBkVBs9F8U4pN0wBOeMDpQ47RgxRzwIkSNcUesyBrJ6Zua\n"
                + "AGAT/3B+XxFNSRuzFVJ7yVTav52Vr2ua2J7p8eRDjeIRRDq/r72DQnNSi6q7pynP\n"
                + "9WQcCk3RvKqsnyrQ/39/2n3qse0wJcGE2jTSW3iDVuycNsMm4hH2Z0kdkquM++v/\n"
                + "eu6FSqdQgPCnXEqULl8FmTxSQeDNtGPPAUO6nIPcj2A781q0tHuu2guQOHXvgR1m\n"
                + "0vdXcDazv/wor3ElhVsT/h5/WrQ8\n"
                + "-----END CERTIFICATE-----\n";
        return new Buffer()
                .writeUtf8(comodoRsaCertificationAuthority)
                .writeUtf8(entrustRootCertificateAuthority)
                .inputStream();
    }

    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
