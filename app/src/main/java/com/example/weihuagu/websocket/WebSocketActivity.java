package com.example.weihuagu.websocket;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.example.weihuagu.MainActivity;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WebSocketActivity extends AppCompatActivity implements View.OnClickListener {


    private ScrollView svChat;
    private EditText etAddress;
    private Spinner spAddress;
    private Button btnConnect;
    private Button btnClose;
    private EditText etDetails;

    private EditText etName;
    private EditText etMessage;
    private Button btnSend;

    private WebSocketClient client;// 连接客户端
    private DraftInfo selectDraft;// 连接协议
    private NotificationManager myManager;

    private Notification myNotification;

    private static final int NOTIFICATION_ID_1 = 1;

    private MyService.MyBinder myBinder;
    private String message;
    private String message2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_socket);
        initView();

        btnConnect.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnSend.setOnClickListener(this);


        Intent intent = new Intent(this, MyService.class);
        startService(intent);


        Intent bindIntent = new Intent(this, MyService.class);
        bindService(bindIntent, connection, BIND_AUTO_CREATE);
    }



    private void initView() {

        svChat = findViewById(R.id.svChat);
        etAddress = findViewById(R.id.etAddress);
        spAddress = findViewById(R.id.spAddress);
        btnConnect = findViewById(R.id.btnConnect);
        btnClose = findViewById(R.id.btnClose);
        etDetails = findViewById(R.id.etDetails);
        etName = findViewById(R.id.etName);
        etMessage = findViewById(R.id.etMessage);
        btnSend = findViewById(R.id.btnSend);

    }


    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyService.MyBinder) service;
            myBinder.startDownload();
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initNotifica(String message) {


        PendingIntent pi = PendingIntent.getActivity(
                getBaseContext(),
                100,
                new Intent(this, MainActivity.class),
                PendingIntent.FLAG_CANCEL_CURRENT
        );


        myManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Notification.Builder myBuilder = new Notification.Builder(getBaseContext());
        myBuilder.setContentTitle("提示")
                .setContentText(message)
                .setTicker("您收到新的消息")
                //设置状态栏中的小图片，尺寸一般建议在24×24，这个图片同样也是在下拉状态栏中所显示
                .setSmallIcon(R.mipmap.ic_launcher)
//                .setLargeIcon(LargeBitmap)
                //设置默认声音和震动
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true)//点击后取消
                .setWhen(System.currentTimeMillis())//设置通知时间
                //android5.0加入了一种新的模式Notification的显示等级，共有三种：
                //VISIBILITY_PUBLIC  只有在没有锁屏时会显示通知
                //VISIBILITY_PRIVATE 任何情况都会显示通知
                //VISIBILITY_SECRET  在安全锁和没有锁屏的情况下显示通知
                .setContentIntent(pi);  //3.关联PendingIntent
        myNotification = myBuilder.build();
        //4.通过通知管理器来发起通知，ID区分通知
        myManager.notify(NOTIFICATION_ID_1, myNotification);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConnect:

                Log.e("wlf", "btnConnect：");
                try {

                    String address = etAddress.getText().toString().trim();
//                    String address = "wss://needws.quansuwangluo.com:8484";
                    if (address.contains("JSR356-WebSocket")) {
                        address += etName.getText().toString().trim();
                    }


                    Log.e("wlf", "连接地址：" + address);
//                    client = new WebSocketClient(new URI(address), selectDraft.draft) {
                    client = new WebSocketClient(new URI(address)) {
                        @Override
                        public void onOpen(final ServerHandshake serverHandshakeData) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    etDetails.append("已经连接到服务器【" + getURI() + "】\n");

                                    Log.e("wlf", "已经连接到服务器【" + getURI() + "】");

                                    etAddress.setEnabled(false);
                                    btnConnect.setEnabled(false);
                                    etName.setEnabled(false);

                                    btnClose.setEnabled(true);
                                    btnSend.setEnabled(true);
                                }
                            });
                        }

                        @Override
                        public void onMessage(final String message) {
                            runOnUiThread(new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                                @Override
                                public void run() {
                                    etDetails.append("获取到服务器信息【" + message + "】\n");

                                    Log.e("wlf", "获取到服务器信息【" + message + "】");


                                    message2 = message;

                                    if (isShow == true) {
                                        initNotifica(message);
                                    }


                                }
                            });
                        }

                        @Override
                        public void onClose(final int code, final String reason, final boolean remote) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    etDetails.append("断开服务器连接【" + getURI() + "，状态码： " + code + "，断开原因：" + reason +
                                            "】\n");

                                    Log.e("wlf", "断开服务器连接【" + getURI() + "，状态码： " + code + "，断开原因：" + reason + "】");

                                    etAddress.setEnabled(true);
                                    btnConnect.setEnabled(true);
                                    etName.setEnabled(true);

                                    btnClose.setEnabled(false);
                                    btnSend.setEnabled(false);
                                }
                            });
                        }

                        @Override
                        public void onError(final Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    etDetails.append("连接发生了异常【异常原因：" + e + "】\n");

                                    Log.e("wlf", "连接发生了异常【异常原因：" + e + "】");

                                    etAddress.setEnabled(true);
                                    btnConnect.setEnabled(true);
                                    etName.setEnabled(true);

                                    btnClose.setEnabled(false);
                                    btnSend.setEnabled(false);
                                }
                            });
                        }
                    };
                    client.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btnClose:
                if (client != null) {
                    client.close();
                }
                break;
            case R.id.btnSend:
                try {
                    if (client != null) {
                        client.send(etName.getText().toString().trim() + "说：" + etMessage.getText().toString().trim());
                        svChat.post(new Runnable() {
                            @Override
                            public void run() {
                                svChat.fullScroll(View.FOCUS_DOWN);
                                etMessage.setText("");
                                etMessage.requestFocus();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (client != null) {
            client.close();
        }
    }


    boolean isShow = false;


    @Override
    protected void onResume() {
        super.onResume();

//        if (myManager != null) {
//            myManager.cancel(NOTIFICATION_ID_1);
//        }

        isShow = false;

        Log.e("dasas", "onResume");

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onStop() {
        super.onStop();

        isShow = true;

//        initNotifica(message2);
        Log.e("dasas", "onStop");
    }

    private class DraftInfo {

        private final String draftName;
        private final Draft draft;

        public DraftInfo(String draftName, Draft draft) {
            this.draftName = draftName;
            this.draft = draft;
        }

        @Override
        public String toString() {
            return draftName;
        }
    }
}
