package com.example.weihuagu.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.weihuagu.websocket.R;

import wendu.webviewjavascriptbridge.WVJBWebView;

public class WebActivity extends AppCompatActivity {

    private ProgressBar progressbar;
    private WVJBWebView webView;


    //    private String from = "https://college.quansuwangluo.com/m/demo.html";
    private String from = "https://need.quansuwangluo.com/H5/app-js.html";

    int i = 0;
    boolean closeOne = false;

    private String ztitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);


        progressbar = findViewById(R.id.progressbar);
        webView = findViewById(R.id.webview);


        initView();

        registerWeb();


    }

    private void registerWeb() {


        //本地调用JS

        //获取网页图片列表
        webView.registerHandler("getImageList", new WVJBWebView.WVJBHandler() {
            @Override
            public void handler(Object o, WVJBWebView.WVJBResponseCallback wvjbResponseCallback) {

            }
        });

        //手机摇一摇
        webView.registerHandler("shakePhone", new WVJBWebView.WVJBHandler() {
            @Override
            public void handler(Object o, WVJBWebView.WVJBResponseCallback wvjbResponseCallback) {

            }
        });




        //调用原生界面
        webView.registerHandler("openVc", new WVJBWebView.WVJBHandler() {
            @Override
            public void handler(Object o, WVJBWebView.WVJBResponseCallback wvjbResponseCallback) {
                Log.d("wvjsblog", "openVc: " + o.toString());
                wvjbResponseCallback.onResult(o);
            }
        });

        //播放音乐

        webView.registerHandler("playMusic", new WVJBWebView.WVJBHandler() {
            @Override
            public void handler(Object o, WVJBWebView.WVJBResponseCallback wvjbResponseCallback) {
                Log.d("wvjsblog", "playMusic: " + o.toString());
                wvjbResponseCallback.onResult(o);
            }
        });



        //播放视频
        webView.registerHandler("playVideo", new WVJBWebView.WVJBHandler() {
            @Override
            public void handler(Object o, WVJBWebView.WVJBResponseCallback wvjbResponseCallback) {
                Log.d("wvjsblog", "playVideo: " + o.toString());
                wvjbResponseCallback.onResult(o);
            }
        });


        //弹出toast消息框
        webView.registerHandler("showMsg", new WVJBWebView.WVJBHandler() {
            @Override
            public void handler(Object o, WVJBWebView.WVJBResponseCallback wvjbResponseCallback) {
                Log.d("wvjsblog", "showMsg: " + o.toString());
                wvjbResponseCallback.onResult(o);
            }
        });

        //调用相机相册
        webView.registerHandler("openCamera", new WVJBWebView.WVJBHandler() {
            @Override
            public void handler(Object o, WVJBWebView.WVJBResponseCallback wvjbResponseCallback) {
                Log.d("wvjsblog", "openCamera: " + o.toString());
                wvjbResponseCallback.onResult(o);
            }
        });




        //调用支付
        webView.registerHandler("openPay", new WVJBWebView.WVJBHandler() {
            @Override
            public void handler(Object o, WVJBWebView.WVJBResponseCallback wvjbResponseCallback) {
                Log.d("wvjsblog", "openPay: " + o.toString());
                wvjbResponseCallback.onResult(o);
            }
        });


        //调用分享

        webView.registerHandler("openShare", new WVJBWebView.WVJBHandler() {
            @Override
            public void handler(Object o, WVJBWebView.WVJBResponseCallback wvjbResponseCallback) {
                Log.d("wvjsblog", "openShare: " + o.toString());

                wvjbResponseCallback.onResult(o);
            }
        });


    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case 0x111:
                    progressbar.setVisibility(View.VISIBLE);
                    break;
                case 0x112:
                    if (progressbar != null) {
                        progressbar.setVisibility(View.GONE);
                    }

                    break;
                case 0x113:


                    break;
                case 0x130:
                    webView.goBack();
                    break;

            }
        }
    };


    private void initView() {

//        setTitle("客户级别");
//		setNavRight("",false);


        progressbar.setMax(100);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//没有缓存LOAD_NO_CACHE
        webSettings.setDatabaseEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setLoadWithOverviewMode(true);
        //给web提供调用接口
//        WebAppInterface webAppInterface = new WebAppInterface();
//        webView.addJavascriptInterface(webAppInterface, "Android");
//        webView.addJavascriptInterface(webAppInterface, "need");

        // 支持通过js打开新的窗口
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setSupportZoom(false);


        webView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边

                //  Log.e("--66-", "from="+from );


                if (url.contains("intent://")) {
                    return true;
                } else if (url.startsWith("weixin://wap/pay?")) {
//                    handler.sendEmptyMessageDelayed(0x130, 500);
                    // webView.goBack();

                    // 如下方案可在非微信内部WebView的H5页面中调出微信支付
                    Intent intent = new Intent();

                    intent.setAction(Intent.ACTION_VIEW);

                    intent.setData(Uri.parse(url));

                    startActivity(intent);

                    return true;
                } else {
                    WebView.HitTestResult hitTestResult = view.getHitTestResult();

                    i++;
                    if (i > 0) {
                        closeOne = true;
                    }

                    if (url.startsWith("tel:")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                    } else if ((url.startsWith("http:") || url.startsWith("https:")) && hitTestResult == null) {


                        view.loadUrl(url);

                    }
                    return false;
                }

            }


            //同意一切证书
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //super.onReceivedSslError(view, handler, error);
                handler.proceed(); // Ignore SSL certificate errors
                //super.onReceivedSslError(view, handler, error);

            }

            //网页加载失败
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                //6.0以上执行


            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                //6.0以下执行

            }

//            @Override
//            public void onPageCommitVisible(WebView view, String url) {
//                super.onPageCommitVisible(view, url);
//
//                closeDialog();
//}
        });

        webView.setWebChromeClient(new WebChromeClient() {

                                       // For Android < 3.0
//                                       public void openFileChooser(ValueCallback<Uri> valueCallback) {
//                                           uploadMessage = valueCallback;
//                                           openImageChooserActivity();
//                                       }
//
//                                       // For Android  >= 3.0
//                                       public void openFileChooser(ValueCallback valueCallback, String acceptType) {
//                                           uploadMessage = valueCallback;
//                                           openImageChooserActivity();
//                                       }
//
//                                       //For Android  >= 4.1
//                                       public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {
//                                           uploadMessage = valueCallback;
//                                           openImageChooserActivity();
//                                       }
//
//                                       // For Android >= 5.0
//                                       @Override
//                                       public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
//                                           uploadMessageAboveL = filePathCallback;
//                                           openImageChooserActivity();
//                                           return true;
//                                       }


                                       @Override
                                       public void onProgressChanged(WebView view, int newProgress) {
                                           super.onProgressChanged(view, newProgress);
                                           /*
                                            *newProgress 为1-100之间的整数
                                            * newProgress = 100时，意味着网页加载完毕
                                            * newProgress < 100时，意味着网页正在加载
                                            **/
                                           if (newProgress == 100) {
                                               // 网页加载完毕，关闭进度提示
                                               handler.sendEmptyMessage(0x112);
                                               progressbar.setProgress(0);
                                           } else {
                                               // 网页正在加载
                                               handler.sendEmptyMessage(0x111);
                                               progressbar.setProgress(newProgress);

                                           }
                                       }

                                       @Override
                                       public boolean onJsAlert(WebView view, String url, String message, JsResult result) {

                                           final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                           builder.setTitle("")
                                                   .setMessage(message)
                                                   .setPositiveButton("确定", null);

                                           // 不需要绑定按键事件
                                           // 屏蔽keycode等于84之类的按键
                                           builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                                               public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                                   return true;
                                               }
                                           });
                                           // 禁止响应按back键的事件
                                           builder.setCancelable(false);
                                           AlertDialog dialog = builder.create();
                                           dialog.show();
                                           result.confirm();// 因为没有绑定事件，需要强行confirm,否则页面会变黑显示不了内容。
                                           return true;
                                       }

                                       @Override
                                       public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {

                                           final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                           builder.setTitle("")
                                                   .setMessage(message)
                                                   .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                       public void onClick(DialogInterface dialog, int which) {
                                                           result.confirm();
                                                       }
                                                   })
                                                   .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                                                       public void onClick(DialogInterface dialog, int which) {
                                                           result.cancel();
                                                       }
                                                   });
                                           builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                               @Override
                                               public void onCancel(DialogInterface dialog) {
                                                   result.cancel();
                                               }
                                           });

                                           // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
                                           builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                                               @Override
                                               public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                                   return true;
                                               }
                                           });
                                           // 禁止响应按back键的事件
                                           // builder.setCancelable(false);
                                           AlertDialog dialog = builder.create();
                                           dialog.show();
                                           return true;
                                           // return super.onJsConfirm(view, url, message, result);

                                       }

                                       /**
                                        * 覆盖默认的window.prompt展示界面，避免title里显示为“：来自file:////”
                                        * window.prompt('请输入您的域名地址', '618119.com');
                                        */
                                       public boolean onJsPrompt(WebView view, String url, String message,
                                                                 String defaultValue, final JsPromptResult result) {
                                           final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                                           builder.setTitle("")
                                                   .setMessage(message)
                                                   .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                       public void onClick(DialogInterface dialog, int which) {
                                                           result.confirm();
                                                       }
                                                   })
                                                   .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                                                       public void onClick(DialogInterface dialog, int which) {
                                                           result.cancel();
                                                       }
                                                   });
                                           builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                               @Override
                                               public void onCancel(DialogInterface dialog) {
                                                   result.cancel();
                                               }
                                           });


                                           // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
                                           builder.setOnKeyListener(new DialogInterface.OnKeyListener() {
                                               public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                                   return true;
                                               }
                                           });

                                           // 禁止响应按back键的事件
                                           // builder.setCancelable(false);
                                           AlertDialog dialog = builder.create();
                                           dialog.show();
                                           return true;
                                           // return super.onJsPrompt(view, url, message, defaultValue,
                                           // result);
                                       }


                                       @Override
                                       public void onReceivedTitle(WebView view, String title) {
                                           super.onReceivedTitle(view, title);


                                           ztitle = title;

                                           handler.sendEmptyMessage(0x113);
                                       }


                                   }


        );

        webView.loadUrl(from);


    }


}
