package com.example.Activity;

import java.io.File;

import com.bbk.example.demo.R;
import com.example.utils.Debug;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

public class NotificationActivity extends Activity implements OnClickListener
{
	private NotificationManager mNotificationManager;
	private int mNotificationIndex = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification_layout);
		init();
	}
	
	public void init()
	{
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		final Window win = getWindow();
//		win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		mNotificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		findViewById(R.id.send_notification_normal).setOnClickListener(this);
		findViewById(R.id.send_notification_unclearable).setOnClickListener(this);
		findViewById(R.id.send_notification_free_normal).setOnClickListener(this);
		findViewById(R.id.send_notification_free_unclearable).setOnClickListener(this);
		findViewById(R.id.cancle_notification).setOnClickListener(this);
		findViewById(R.id.update_notification).setOnClickListener(this);
		findViewById(R.id.send_notification_ontime).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.send_notification_normal:
		{
			sendNotificationNormal(mNotificationIndex++);
			break;
		}
		case R.id.send_notification_free_normal:
		{
			sendNotificationFreeNormal(mNotificationIndex++);
			break;
		}
		case R.id.send_notification_free_unclearable:
		{
			sendNotificationFreeUnClearNormal(mNotificationIndex++);
			break;
		}
		case R.id.send_notification_unclearable:
		{
			sendNotificationUnClearNormal(mNotificationIndex++);
			break;
		}
		case R.id.cancle_notification:
		{
			if(mNotificationIndex >= 1)
			{
				cancleNotification(--mNotificationIndex);
			}
			else
			{
				Toast.makeText(this, "没有通知可以取消了", Toast.LENGTH_SHORT).show();
			}
			break;
		}
		case R.id.update_notification:
		{
			if(mNotificationIndex >= 1)
			{
				updateNotification(mNotificationIndex-1);
			}
			else
			{
				Toast.makeText(this, "没有通知可以更新", Toast.LENGTH_SHORT).show();
			}
			break;
		}
		case R.id.send_notification_ontime:
		{
			sendNotificationOnTime();
			break;
		}
		}
	}
	
	private void sendNotificationOnTime()
	{
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				int i = 10;
				while(i > 0)
				{
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					sendNotificationNormal(mNotificationIndex++);
					i--;
				}
			}
		});
		thread.start();
	}
	
	private void updateNotification(int id)
	{
		Intent intent = new Intent(this,NotificationActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);  
//		if(testN == null) {
//			testN = new NotificationCompat.Builder(this);
//		}
//		testN.setSmallIcon(R.drawable.ic_launcher);
//		testN.setContentTitle("通知 id = " + id);
		testN.setContentText("跟新了这个通知 id = " + id);
//		testN.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.test_icon));
		testN.setSmallIcon(R.drawable.ic_launcher);
//		testN.addAction(R.drawable.alert_dialog_gb, "action1", pendingIntent);
//		testN.setOngoing(true);
//		NotificationCompat.BigTextStyle notifStyle = new NotificationCompat.BigTextStyle(testN).bigText("跟新一个通知 \n 文件夹 \n 哈哈哈");
//		testN.setOngoing(true);
//		nb.setProgress(100, 60, true);
		mNotificationManager.notify(id, testN.getNotification());
	}
	
	private void sendNotificationNormal(int id)
	{
//		Toast.makeText(this,"哈哈哈哈 2222",Toast.LENGTH_SHORT).show();
//		File file = new File("/mnt/sdcard/20702098");
//		if(file.isDirectory() && file.exists())
//		{
//			Toast.makeText(this, "有文件夹", Toast.LENGTH_SHORT).show();
//			Intent intent = new Intent(this,NotificationActivity.class);  
//			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);  
//			NotificationCompat.Builder nb = new NotificationCompat.Builder(this);
//			nb.setSmallIcon(R.drawable.loading_3);
//			nb.setContentTitle("通知 id = " + id);
//			nb.setContentText("这是一个普通标准的测试通知");
//			nb.setProgress(100, 60, true);
//			nb.setContentIntent(pendingIntent);
//			nb.setAutoCancel(true);
//			nb.setDefaults(Notification.DEFAULT_ALL & ~Notification.DEFAULT_SOUND);
//			mNotificationManager.notify(id, nb.getNotification());
//		}
//		else
//		{
//			Toast.makeText(this, "没有文件夹", Toast.LENGTH_SHORT).show();
//			Intent intent = new Intent(this,NotificationActivity.class);  
//			PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);  
//			NotificationCompat.Builder nb = new NotificationCompat.Builder(this);
//			nb.build().flags |= Notification.FLAG_ONGOING_EVENT;
//			nb.build().flags |= Notification.FLAG_NO_CLEAR;
//			nb.setSmallIcon(R.drawable.loading_3);
//			nb.setContentTitle("通知 id = " + id);
//			nb.setContentText("这是一个普通标准的测试通知");
//			nb.setProgress(100, 60, true);
//			nb.setContentIntent(pendingIntent);
////			nb.setAutoCancel(true);
////			nb.setDefaults(Notification.DEFAULT_ALL);
////			nb.getNotification().flags |= Notification.FLAG_ONGOING_EVENT;
////			nb.getNotification().flags |= Notification.FLAG_NO_CLEAR;
////			nb.setOngoing(true);
//			mNotificationManager.notify(id, nb.build());
//			Debug.d("flags = " + nb.build().flags);
//		}
//		Notification notification =new Notification(R.drawable.brightness_small,   
//                "督导系统", System.currentTimeMillis()); 
//		notification.icon  = R.drawable.test_icon;
        //FLAG_AUTO_CANCEL   该通知能被状态栏的清除按钮给清除掉
        //FLAG_NO_CLEAR      该通知不能被状态栏的清除按钮给清除掉
        //FLAG_ONGOING_EVENT 通知放置在正在运行
        //FLAG_INSISTENT     是否一直进行，比如音乐一直播放，知道用户响应
//        notification.flags |= Notification.FLAG_ONGOING_EVENT; // 将此通知放到通知栏的"Ongoing"即"正在运行"组中   
//        notification.flags |= Notification.FLAG_NO_CLEAR; // 表明在点击了通知栏中的"清除通知"后，此通知不清除，经常与FLAG_ONGOING_EVENT一起使用   
//        notification.flags |= Notification.FLAG_SHOW_LIGHTS;   
//        DEFAULT_ALL     使用所有默认值，比如声音，震动，闪屏等等
//        DEFAULT_LIGHTS  使用默认闪光提示
//        DEFAULT_SOUNDS  使用默认提示声音
//        DEFAULT_VIBRATE 使用默认手机震动，需加上<uses-permission android:name="android.permission.VIBRATE" />权限
//        notification.defaults = Notification.DEFAULT_LIGHTS; 
        //叠加效果常量
        //notification.defaults=Notification.DEFAULT_LIGHTS|Notification.DEFAULT_SOUND;
//        notification.ledOnMS =5000; //闪光时间，毫秒
        // 设置通知的事件消息   
//        CharSequence contentTitle ="标题"; // 通知栏标题   
//        CharSequence contentText ="内容" + id; // 通知栏内容   
        
//        Intent notificationIntent =new Intent(this, MainActivity.class); // 点击该通知后要跳转的Activity   
//        PendingIntent contentItent = PendingIntent.getActivity(this, 0, notificationIntent, 0);   
//        notification.setLatestEventInfo(this, contentTitle, contentText, contentItent);   
        // 把Notification传递给NotificationManager   
//        mNotificationManager.notify(id, notification);  
		Intent intent = new Intent(this,NotificationActivity.class);  
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);  
		testN = new NotificationCompat.Builder(this);
//		testN.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.battery_error_dark));
		testN.setSmallIcon(R.drawable.test_protext);
//		testN.setSmallIcon(R.drawable.icon);
		testN.setContentTitle("通知 id = " + id);
		testN.setContentText("ddd ");
//		NotificationCompat.BigTextStyle notifStyle = new NotificationCompat.BigTextStyle(testN).bigText(" 跟新一个通知 \n 文件夹 \n 哈哈哈");
		//		testN.setContentText("这是一个不可删除的标准测试通知好" +
//				"阿这是一个不可删除的标准测试通知好" +
//				"阿这是一个/n不可删除的标准测试通知\n好阿" +
//				"这是一个不可删除的\n标准测试通知好阿这是一个" +
//				"不可删除的标准测试通知好阿这是一个不可删除的标" +
//				"准测试通知好阿这是一个不可删除的标准测试通知好阿" +
//				"这是一个不可删除的标准测试通知好阿这是一个不可删除" +
//				"的标准测试通知好阿这是一个不可删除的标准测试通知好阿这是" +
//				"一个不可删除的标准测试通知好阿这是一个不可删除的标准" +
//				"测试通知好阿这是一个不可删除的标准测试通知好阿");
//		testN.setProgress(100, 60, true);
		testN.setContentIntent(pendingIntent);
		testN.setWhen(0);
        testN.setAutoCancel(true);
//        testN.setTicker("haha");
//        testN.setLocalOnly(true);
        testN.setDefaults(Notification.DEFAULT_SOUND);
//		testN.setOngoing(true);
//		testN.getNotification().flags|= Notification.FLAG_AUTO_CANCEL;
		
//		NotificationCompat.BigTextStyle notifStyle = new NotificationCompat.BigTextStyle(testN).bigText("aaa \n bbb \n ccc");
//		testN.setOngoing(true);
//		NotificationCompat.BigTextStyle notifStyle = new NotificationCompat.BigTextStyle(builder).bigText(buf);
		mNotificationManager.notify(id, testN.getNotification());
		return;
    }
	
	private NotificationCompat.Builder testN;
	
	private void sendNotificationUnClearNormal(int id)
	{
		Intent intent = new Intent(this,NotificationActivity.class);  
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);  
		NotificationCompat.Builder nb = new NotificationCompat.Builder(this);
		nb.setSmallIcon(R.drawable.test_prtext_1);
		nb.setContentTitle("通知 id = " + id);
		nb.setContentText("这是一个不可删除的标准测试通知");
		nb.setProgress(100, 60, true);
		nb.setContentIntent(pendingIntent);
//		nb.setOngoing(true);
//		nb.getNotification().flags |= Notification.FLAG_ONGOING_EVENT;
//		nb.addAction(R.drawable.alert_dialog_gb, "action1", pendingIntent);
//		nb.setFullScreenIntent(pendingIntent, false);
//		nb.getNotification().fullScreenIntent
//		RemoteViews expandedView = new RemoteViews(getPackageName(), R.layout.test_camera);
//		nb.setContent(expandedView).build();
//		nb.getNotification().bigContentView = expandedView;
//		nb.setStyle(new NotificationCompat.BigPictureStyle() // 设置通知样式为大型图片样式
//        .bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.charge_button_normal)));
		mNotificationManager.notify(id, nb.getNotification());
	}
	
	private void sendNotificationFreeNormal(int id)
	{
		Intent intent = new Intent(this,NotificationActivity.class);  
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);  
		 
		NotificationCompat.Builder nb = new NotificationCompat.Builder(this);
		nb.setSmallIcon(R.drawable.loading_3);
		nb.setContentTitle("通知 id = " + id);
		nb.setContentText("这是一个自定义测试通知");
		nb.setProgress(100, 60, true);
		nb.setContentIntent(pendingIntent);
		nb.addAction(R.drawable.alert_dialog_gb, "action1", pendingIntent);
//		nb.setFullScreenIntent(pendingIntent, false);
//		nb.getNotification().fullScreenIntent
//		RemoteViews expandedView = new RemoteViews(getPackageName(), R.layout.test_camera);
//		nb.setContent(expandedView).build();
//		nb.getNotification().bigContentView = expandedView;
		nb.setStyle(new NotificationCompat.BigPictureStyle() // 设置通知样式为大型图片样式
        .bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.charge_button_normal)));
		mNotificationManager.notify(id, nb.getNotification());
		mNotificationManager.notify(id,nb.getNotification());
		mNotificationManager.notify("aa",id,nb.getNotification());
	}
	
	private void sendNotificationFreeUnClearNormal(int id)
	{
		Intent intent = new Intent(this,NotificationActivity.class);  
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);  
		 
		NotificationCompat.Builder nb = new NotificationCompat.Builder(this);
		nb.setSmallIcon(R.drawable.loading_3);
		nb.setContentTitle("通知 id = " + id);
		nb.setContentText("这是一个自定义不可删除测试通知");
		nb.setProgress(100, 60, true);
		nb.setContentIntent(pendingIntent);
		nb.addAction(R.drawable.alert_dialog_gb, "action1", pendingIntent);
		nb.setOngoing(true);
//		nb.setFullScreenIntent(pendingIntent, false);
//		nb.getNotification().fullScreenIntent
//		RemoteViews expandedView = new RemoteViews(getPackageName(), R.layout.test_camera);
//		nb.setContent(expandedView).build();
//		nb.getNotification().bigContentView = expandedView;
		nb.setStyle(new NotificationCompat.BigPictureStyle() // 设置通知样式为大型图片样式
        .bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.charge_button_normal)));
		mNotificationManager.notify(id, nb.getNotification());
	}
	
	private void cancleNotification(int id)
	{
		mNotificationManager.cancel(id);
	}

	@Override
	protected void onDestroy() {
		mNotificationManager.cancelAll();
		super.onDestroy();
	}
	
	public class VisionObserver extends ContentObserver{

        public VisionObserver(Handler handler) {
            super(handler);
        }   

        @Override
        public void onChange(boolean selfChange,Uri uri) {
            super.onChange(selfChange);
        }   
    }   

}
