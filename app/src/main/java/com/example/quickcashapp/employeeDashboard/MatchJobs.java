package com.example.quickcashapp.employeeDashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.quickcashapp.R;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.OAuth2Credentials;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MatchJobs extends AppCompatActivity {
    private static final String CREDENTIALS_FILE_PATH = "key.json";
    // 定义一个常量，表示存储Google服务密钥的文件路径
    private static final String PUSH_NOTIFICATION_ENDPOINT = "https://fcm.googleapis.com/v1/projects/csci3130-push-notificati-238a4/messages:send";
    // 定义一个常量，表示Firebase Cloud Messaging的推送通知端点。
    private Button sendNotificationBtn;
    private RequestQueue requestQueue;
    // 定义一个私有变量，用于引用Volley的请求队列。

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        // 调用init方法，用于初始化界面组件和设置。
        setListeners();
        // 调用setListeners方法，用于设置界面组件的事件监听器。
    }

    private void getAccessToken(Context context, AccessTokenListener listener) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        // 创建一个单线程执行器，用于执行需要线程隔离的任务。
        executorService.execute(() -> {
            try {
                InputStream serviceAccountStream = context.getAssets().open(CREDENTIALS_FILE_PATH);
                // 打开资产目录下的Google服务密钥文件
                GoogleCredentials googleCredentials = GoogleCredentials.fromStream(serviceAccountStream)
                        .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
                // 从输入流中读取Google凭据，并限定凭据的使用范围。
                googleCredentials.refreshIfExpired(); // Refresh the token if expired
                String token = googleCredentials.getAccessToken().getTokenValue();
                // 获取访问令牌的值。
                listener.onAccessTokenReceived(token);
                // 调用监听器的onAccessTokenReceived方法，传递令牌。
                Log.d("AccessToken", "Received token: " + token);
                // 记录令牌的获取情况。
            } catch (IOException e) {
                listener.onAccessTokenError(e);
                // 如果发生IO异常，调用监听器的onAccessTokenError方法。
            } finally {
                executorService.shutdown(); // Proper place to shutdown
            }
        });
    }

    private void init() {
        sendNotificationBtn = findViewById(R.id.sendNotificationBtn);
        // 初始化发送通知按钮。
        requestQueue = Volley.newRequestQueue(this);
        // 初始化Volley的请求队列。
        FirebaseMessaging.getInstance().subscribeToTopic("jobs")
                // 调用Firebase Messaging API订阅主题"jobs"。
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM Subscribe", "Failed to subscribe to jobs topic");
                        // 如果订阅失败，记录警告信息。
                    } else {
                        Log.d("FCM Subscribe", "Subscribed to jobs topic successfully");
                    }
                });
    }

    private void setListeners() {
        sendNotificationBtn.setOnClickListener(view -> getAccessToken(this, new AccessTokenListener() {
            @Override
            public void onAccessTokenReceived(String token) {
                sendNotification(token);
                // 当接收到访问令牌后，调用sendNotification方法发送通知。
            }

            @Override
            public void onAccessTokenError(Exception exception) {
                Toast.makeText(MatchJobs.this, "Error getting access token: " + exception.getMessage(), Toast.LENGTH_LONG).show();
                // 如果在获取访问令牌时发生错误，显示错误消息的Toast通知。
                exception.printStackTrace();
            }
        }));
    }

    @SuppressLint("LongLogTag")
    private void sendNotification(String authToken) {
        try {
            // Build the notification payload
            JSONObject notificationJSONBody = new JSONObject();
            notificationJSONBody.put("title", "New Job Created");
            notificationJSONBody.put("body", "A new job is created in your city.");
            // More payload setup
            // Send the notification
        } catch (JSONException e) {
            Log.e("NotificationJSONException", "Error creating notification JSON: " + e.getMessage());
            Toast.makeText(this, "Error creating notification payload", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
