package com.example.quickcashapp;

import static org.mockito.Mockito.*;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowNotification;
import org.robolectric.shadows.ShadowNotificationManager;

import static org.robolectric.Shadows.shadowOf;
import static org.junit.Assert.*;

//import androidx.test.core.app.ApplicationProvider;
//import androidx.test.ext.junit.runners.AndroidJUnit4;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest = Config.NONE) // 或提供正确的 manifest 路径 指定SDK版本
public class JobCheckServiceTest {

    private JobCheckService service;
    private ShadowNotificationManager shadowNotificationManager;

    @Before
    public void setUp() {
        // 创建JobCheckService实例
        service = Robolectric.setupService(JobCheckService.class);
        NotificationManager notificationManager = (NotificationManager) service.getSystemService(Context.NOTIFICATION_SERVICE);
        // 获取ShadowNotificationManager用于验证通知行为
        shadowNotificationManager = shadowOf(notificationManager);

    }
    /*@Test
    public void testOnHandleIntent() {
        // 创建一个含有必要数据的Intent
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), JobCheckService.class);
        intent.putExtra("job_details", "Developer;San Francisco");

        // 调用onHandleIntent处理Intent
        service.onHandleIntent(intent);

        // 获取通知列表
        List<Notification> notifications = shadowNotificationManager.getAllNotifications();

        // 确保列表不为空
        assertFalse("No notification！", notifications.isEmpty());//

        // 验证通知是否被正确发送
        Notification notification = notifications.get(0);//
        ShadowNotification shadowNotification = shadowOf(notification);


        // 获取通知内容并验证
        assertNotNull("Notification was not sent", notification);
        assertEquals("Job Match Found!", shadowNotification.getContentTitle());
        assertEquals("You have a new job match!", shadowNotification.getContentText());

    }*/

    //@Test
    //public void jobCheckService_DoesNotNotifyWhenJobDoesNotMatchPreferences() {
        // Setup preferences to something different than the job details
        //Intent intent = new Intent(ApplicationProvider.getApplicationContext(), JobCheckService.class);
        //intent.putExtra("job_details", "Nurse;Los Angeles");

        //service.onHandleIntent(intent);

        // Check that no notifications were sent
        //assertEquals("Expected no notifications", 0, shadowNotificationManager.size());
    //}
}
