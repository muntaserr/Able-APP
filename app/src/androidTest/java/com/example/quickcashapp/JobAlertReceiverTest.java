package com.example.quickcashapp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import android.content.Context;
import android.content.Intent;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

import androidx.test.core.app.ApplicationProvider;

@RunWith(MockitoJUnitRunner.class)
public class JobAlertReceiverTest {

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testOnReceive() {
        // 创建一个Mock对象来模拟Context
        //Context context = ApplicationProvider.getApplicationContext();
        Context mockContext = mock(Context.class);
        // 创建一个Intent，模拟接收到的广播Intent
        Intent mockIntent = new Intent("com.example.quickcashapp.ACTION_FIND_JOB");
        mockIntent.putExtra("job_details", "Sample job details");

        // 实例化接收器
        JobAlertReceiver receiver = new JobAlertReceiver();
        // 触发onReceive方法
        receiver.onReceive(mockContext, mockIntent);

        // 使用ArgumentCaptor来捕获传递给startService的Intent
        ArgumentCaptor<Intent> argumentCaptor = ArgumentCaptor.forClass(Intent.class);
        verify(mockContext).startService(argumentCaptor.capture());

        // 检查传递的Intent是否具有正确的信息
        Intent capturedIntent = argumentCaptor.getValue();
        assertEquals(JobCheckService.class.getName(), capturedIntent.getComponent().getClassName());
        assertEquals("Sample job details", capturedIntent.getStringExtra("job_details"));
    }
}
