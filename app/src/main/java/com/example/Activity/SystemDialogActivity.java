package com.example.Activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.xmlpull.v1.XmlPullParser;

import com.bbk.example.demo.R;
import com.example.utils.DJTextView;
import com.example.utils.Debug;
import com.example.utils.Util;
import com.example.views.ForgetPasswordDialog;
import com.example.views.M1000PowerOffDialog1.OnCallBack;
import com.example.views.M1000PowerOffDialog1;

import android.R.style;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources.Theme;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class SystemDialogActivity extends FragmentActivity implements
		OnClickListener {
	private final String TAG = "SystemDialogActivity";
	/** 关机按钮 */
	private Button mShutDownBtn;
	/**M1000的关机对话框按钮*/
	private Button mShutDownM1000Btn;
	/**两个按钮的普通对话框*/
	private Button mTwoNormalBtn;
	/**三个按钮的普通对话框*/
	private Button mThreeNormalBtn;
	/** 普通对话框 */
	private Button mNormalBtn;
	/** 多按钮对话框 */
	private Button mMultBtn;
	/** 列表对话框 */
	private Button mListBtn;
	/** 单选对话框 */
	private Button mSingleChooseBtn;
	/** 多选对话框 */
	private Button mMultChooseBtn;
	/** 进度对话框 */
	private Button mProgressBtn;
	/** 显示进度对话框 */
	private Button mShowProgressBtn;
	/** 显示字库 0~65535 */
	private Button mShowFonts;
	/**显示忘记密码对话框*/
	private Button mShowForgetPasswordBtn;
	
	/** 测试字库 */
	private DJTextView mTestFonts;
	private LinearLayout mMainLayout;

	private EditText mEditText1;
	private EditText mEditText2;
	private EditText mEditText3;
	private String mFontStr;
	ProgressDialog mLoadFontProcessDia;
	private UIHandle mUIHandle = new UIHandle();

	public class UIHandle extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 100: {
				mTestFonts.setText(mFontStr);
				mLoadFontProcessDia.dismiss();
				break;
			}
			}
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        getWindow().getDecorView().setSystemUiVisibility(0x00002000);
//		final Window win = getWindow();
//      getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN );
		setContentView(R.layout.system_dialog);
		setFullScreenWithTranslate();
		init();
//		switchTransSystemUI(true);
	}

	/**
	 * 丁俊 2015年4月25日
	 */
	public void init() {
		mShutDownBtn = (Button) findViewById(R.id.shut_down_dialog);
		mShutDownBtn.setOnClickListener(this);
		mShutDownM1000Btn = (Button)findViewById(R.id.shut_down_dialog_M1000);
		mShutDownM1000Btn.setOnClickListener(this);
		mNormalBtn = (Button) findViewById(R.id.normal_dialog);
		mNormalBtn.setSoundEffectsEnabled(false);
		mTwoNormalBtn = (Button)findViewById(R.id.two_normal_dialog);
		mTwoNormalBtn.setOnClickListener(this);
		mThreeNormalBtn = (Button)findViewById(R.id.three_normal_dialog);
		mThreeNormalBtn.setOnClickListener(this);
		mMultBtn = (Button) findViewById(R.id.mult_btn_dialog);
		mListBtn = (Button) findViewById(R.id.list_dialog);
		mSingleChooseBtn = (Button) findViewById(R.id.single_choose_dialog);
		mMultChooseBtn = (Button) findViewById(R.id.mult_choose_dialog);
		mProgressBtn = (Button) findViewById(R.id.progress_dialog);
		mShowProgressBtn = (Button) findViewById(R.id.showProcess_dialog);
		mShowFonts = (Button) findViewById(R.id.showFont);
		
		mShowForgetPasswordBtn = (Button)findViewById(R.id.show_forget_password_dialog);
		mShowForgetPasswordBtn.setOnClickListener(this);
		
		mNormalBtn.setOnClickListener(this);
		mMultBtn.setOnClickListener(this);
		mListBtn.setOnClickListener(this);
		mSingleChooseBtn.setOnClickListener(this);
		mMultChooseBtn.setOnClickListener(this);
		mProgressBtn.setOnClickListener(this);
		mShowProgressBtn.setOnClickListener(this);
		mShowFonts.setOnClickListener(this);

		mTestFonts = (DJTextView) findViewById(R.id.test_str);

		mEditText1 = (EditText) findViewById(R.id.edit_1);
		mEditText1.setImeOptions(EditorInfo.IME_ACTION_NEXT);
		mEditText1.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				Log.d(TAG, "onEditorAction actionId = " + event.getAction());
				Log.d(TAG, "onEditorAction event = " + event.getKeyCode());
				return false;
			}
		});
		mEditText2 = (EditText) findViewById(R.id.edit_2);
		mEditText2.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		mEditText2.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				Log.d(TAG, "onEditorAction actionId = " + event.getAction());
				Log.d(TAG, "onEditorAction event = " + event.getKeyCode());
				return false;
			}
		});
		mEditText3 = (EditText) findViewById(R.id.edit_3);
		mEditText3.setImeOptions(EditorInfo.IME_ACTION_NEXT);
		mEditText3.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				Log.d(TAG, "onEditorAction actionId = " + event.getAction());
				Log.d(TAG, "onEditorAction event = " + event.getKeyCode());
				return false;
			}
		});

		mMainLayout = (LinearLayout) findViewById(R.id.main_layout);
//		 mTestFonts.setText(Util.getAllUnicode(161,700));
	}

	public void setFullScreenWithTranslate()
	{
//	  Window window = getWindow();
//	  window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//	  window.getDecorView().setSystemUiVisibility(0x00002000);
//	  window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//	  window.setStatusBarColor(Color.TRANSPARENT);
		Window window = getWindow();
		window.getDecorView().getWindowSystemUiVisibility();
		window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | 0x00002000);
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		window.setStatusBarColor(Color.TRANSPARENT);
	}

	public void setStatusBarColor()
	{
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );
		//这个设置了以后，状态栏图标将变成灰色，不设置默认白色
		//需要用6.0的sdk,否则用值0x00002000;
		window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//	       window.getDecorView().setSystemUiVisibility(0x00002000);
//	       window.setStatusBarColor(Color.TRANSPARENT);
		window.setStatusBarColor(Color.BLUE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.shut_down_dialog: {
//			 showShutDownDialog();
//			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//			File file=new File("/mnt/sdcard/DCIM/Camera/");
//			Uri mUri = Uri.fromFile(file);
//			intent.setDataAndType(mUri, "image/*");
//			startActivityForResult(intent, 1);
//			 handleConfirmDlgMsg(11);
			break;
		}
		case R.id.normal_dialog: {
			 showNormalDia();
//			showMyDia();
			break;
		}
		case R.id.two_normal_dialog:
		{
			showTwoNormalDia();
			break;
		}
		case R.id.three_normal_dialog:
		{
			showThreeNormalDia();
			break;
		}
		case R.id.mult_btn_dialog: {
			showMultiDia();
			break;
		}
		case R.id.list_dialog: {
			showListDia();
			break;
		}
		case R.id.single_choose_dialog: {
			showSinChosDia();
			break;
		}
		case R.id.mult_choose_dialog: {
			showMultiChosDia();
			break;
		}
		case R.id.progress_dialog: {
			showReadProcess();
			break;
		}
		case R.id.showProcess_dialog: {
			showProcessDia();
			break;
		}
		case R.id.showFont: {
			// Thread loadFont = new Thread(new Runnable() {
			// @Override
			// public void run() {
			// // mFontStr = Util.getAllUnicode();
			// mUIHandle.sendEmptyMessage(100);
			// }
			// });
			// loadFont.start();
			// showProcessDia();
			// new byte[]{-70, -93, -78, -50, -31, -53, -93}
			// BA A3 B2 CE E1 CB A3
			// String utf8 = Util.getAllUnicode(52914);
			// String utf8=new String(new byte[]{-70, -93, -78, -50, -31, -53,
			// -93},"UTF-8");
			// mTestFonts.setText(utf8);
			// String utf8 = Util.getAllUnicode(63);
			// mTestFonts.setText(utf8);
			Toast.makeText(this, "this is a sim toast test", Toast.LENGTH_SHORT)
					.show();
			// byte[] data = Util.readFile("/mnt/sdcard/dddddd0.txt");
			// for(int i = 0;i < data.length;i++)
			// {
			// Log.d(TAG, "data = " + data[i]);
			// }
			// String str = "";
			// Paint paint = mTestFonts.getPaint();
			// try {
			// str = new String(data,"gbk");
			// for(int i = 0;i < str.length()-1;i++)
			// {
			// String tmpStr = str.substring(i, i+1);
			// float[] widths = new float[1];
			// int textWidth = paint.getTextWidths(tmpStr, widths);
			// Log.d(TAG, "textWidth = " + widths[0] + " str = " + tmpStr);
			// }
			// } catch (UnsupportedEncodingException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// mTestFonts.setText(str);
//			Intent intent = new Intent("com.android.internal.policy.impl.PhoneWindowManager.DELAYED_KEYGUARD");
//			intent.putExtra("unable_keyguard", 1);
//			this.sendBroadcast(intent);
//			Debug.d("hahahahahah");
			this.sendBroadcast(new Intent("com.android.systemui.flashliht.off"));
//			setLockScreenWallpaper("/mnt/sdcard/default_keyguard_wallpaper.png");
			break;
		}
		case R.id.shut_down_dialog_M1000:
		{
			final M1000PowerOffDialog1 mod = new M1000PowerOffDialog1(this,R.style.powerofftransparentDialog);
			mod.create();
			mod.show();
			mod.setCallBack(new OnCallBack() {
				//
				@Override
				public void onReboot() {
					Debug.d("onReboot");
					mod.dismiss();
				}
				
				@Override
				public void onPowerOff() {
					Debug.d("onPowerOff");
					mod.dismiss();
				}
			});
//			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  
//            startActivityForResult(intent, 1);
//            overridePendingTransition(R.anim.activity_enter, R.anim.activity_exit);
			break;
		}
		case R.id.show_forget_password_dialog:
		{
			Debug.d("show forget password dialog");
			ForgetPasswordDialog fp = new ForgetPasswordDialog(this);
			fp.setmCurrentId(123456789);
			fp.setmPasswordId(123456789);
			fp.create();
			fp.setCanceledOnTouchOutside(false);
			fp.show();
			break;
		}
		}
	}

	private void showThreeNormalDia() {
		AlertDialog.Builder normalDia = new AlertDialog.Builder(this);
		normalDia.setTitle("2个按钮");
		normalDia.setMessage("普通对话框的message内容");
		normalDia.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.d(TAG, "确定");
					}
				});
		normalDia.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Log.d(TAG, "取消");
					}
				});
		normalDia.setNeutralButton("中间的", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Log.d(TAG, "中间的");
			}
		});
		AlertDialog showDialog = normalDia.create();
		showDialog.show();
	}

	private void showTwoNormalDia() {
		AlertDialog.Builder normalDia = new AlertDialog.Builder(this);
		normalDia.setTitle("3个按钮");
		normalDia.setMessage("普通对话框的message内容");
		normalDia.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.d(TAG, "确定");
					}
				});
		normalDia.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Log.d(TAG, "取消");
					}
				});
		AlertDialog showDialog = normalDia.create();
		showDialog.show();
	}

	public void showNotify(Context context) {
		// Intent notificationIntent = new Intent(getApplicationContext(),
		// UpdateDownload.class);
		// PendingIntent contentIntent =
		// PendingIntent.getActivity(getApplicationContext(), 0,
		// notificationIntent, 0);
		Notification.Builder builder = new Notification.Builder(context);
		builder.setContentTitle("this is title");
		builder.setContentText("this is msg");
		builder.setSmallIcon(R.drawable.ic_launcher);
		// builder.setContentIntent(contentIntent);
		builder.setAutoCancel(true);
		builder.setWhen(System.currentTimeMillis());
		NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		Notification notification = builder.getNotification();
		// notification.flags |= Notification.FLAG_ONGOING_EVENT;
		// notification.flags |= Notification.FLAG_NO_CLEAR;
		// notification.flags |= Notification.FLAG_ONGOING_EVENT;

		notificationmanager.notify(2, notification);
	}

	public void showShutDownDialog() {
		Dialog sConfirmDialog;
		sConfirmDialog = new Dialog(this, R.style.transparentDialog);
		LayoutInflater li = (LayoutInflater) this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = li.inflate(R.layout.shutdown_dialog, null);
		Button shutdown_btn = (Button) v.findViewById(R.id.shutdown_btn);
		shutdown_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(SystemDialogActivity.this, "重启",
						Toast.LENGTH_SHORT).show();
			}
		});
		sConfirmDialog.setContentView(v);
		sConfirmDialog.setCanceledOnTouchOutside(false);
		sConfirmDialog.show();
		
		Toast.makeText(this, "显示关机对话框", Toast.LENGTH_SHORT).show();
	}

	/* 普通的对话框 */
	private void showNormalDia() {
		// 16974985 Theme_DeviceDefault_Dialog_Alert
		//
		// Log.d(TAG,null);
		Thread.currentThread().getStackTrace()[2].getLineNumber();
//		AlertDialog.Builder normalDia = new AlertDialog.Builder(this,
//				AlertDialog.THEME_TRADITIONAL);
//		Theme.DeviceDefault.Light.Dialog
		//AlertDialog.THEME_HOLO_LIGHT
		AlertDialog.Builder normalDia = new AlertDialog.Builder(this,AlertDialog.THEME_HOLO_LIGHT) {
		};
//		normalDia.setView(new EditText(this));
		normalDia.setTitle("haha");
		normalDia.setMessage("普通对话框的message内容");
		normalDia.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.d(TAG, "确定");
					}
				});
		normalDia.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Log.d(TAG, "取消");
					}
				});
		
		AlertDialog showDialog = normalDia.create();
//		setDialogCenter(showDialog.getWindow());
		setDialogNoAnimate(showDialog.getWindow());
//		showDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_PHONE);
		showDialog.show();
//		showDialog.getWindow().setGravity(Gravity.CENTER);
//		showDialog.getWindow().setWindowAnimations(R.style.general_dialog_style);
//		showDialog.getWindow().setWindowAnimations(0);
		
		// int width = 1536;
		// int height = 2048;
		// showDialog.getWindow().setLayout(width, height);
		// showDialog.show();

	}

	public static final int FLAG_TRANSLUCENT_STATUS = 0x04000000;

	/* 多按钮对话框 */
	private void showMultiDia() {
		AlertDialog.Builder multiDia = new AlertDialog.Builder(this,R.style.transparentDialog);
		// multiDia.setIcon(R.drawable.ic_launcher);
		multiDia.setTitle("多选项对话框");
		multiDia.setMessage("这是一个多选对话框哦");
		multiDia.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				});
//		multiDia.setNeutralButton("按钮二", new DialogInterface.OnClickListener() {
//
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//			}
//		});
		multiDia.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				});
		AlertDialog showDialog = multiDia.create();
		showDialog.show();
//		showDialog.getWindow().setGravity(Gravity.CENTER);
//		showDialog.getWindow().setWindowAnimations(R.style.general_dialog_style);
	}

	/* 列表对话框 */
	private void showListDia() {
		final String[] mList = { "选项1", "选项2", "选项3", "选项4", "选项5", "选项6",
				"选项7" };
		AlertDialog.Builder listDia = new AlertDialog.Builder(this,AlertDialog.THEME_HOLO_DARK);   //没改
		listDia.setTitle("列表对话框");
		listDia.setItems(mList, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		listDia.create().show();
	}

	/* 单项选择对话框 */
	int yourChose = -1;

	private void showSinChosDia() {
		final String[] mList = { "选项1", "选项2", "选项3", "选项4", "选项5", "选项6",
				"选项7" ,"选项8","选项9","选项10","选项11","选项12","选项13","选项14","选项15","选项16"};
		yourChose = -1;
		AlertDialog.Builder sinChosDia = new AlertDialog.Builder(this,AlertDialog.THEME_HOLO_LIGHT);
		sinChosDia.setTitle("单项选择对话框");
//		sinChosDia.setMessage("这里是message");
		sinChosDia.setSingleChoiceItems(mList, 0,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						yourChose = which;
					}
				});
//		sinChosDia.setPositiveButton("确定",
//				new DialogInterface.OnClickListener() {
//
//					@Override
//					public void onClick(DialogInterface dialog, int which) {
//						// TODO Auto-generated method stub
//						if (yourChose != -1) {
//						}
//					}
//				});
		sinChosDia.create().show();
	}

	ArrayList<Integer> myChose = new ArrayList<Integer>();

	private void showMultiChosDia() {
		final String[] mList = { "选项1", "选项2", "选项3", "选项4", "选项5", "选项6",
				"选项7" };
		final boolean mChoseSts[] = { false, false, false, false, false, false,
				false };
		myChose.clear();
		AlertDialog.Builder multiChosDia = new AlertDialog.Builder(this);
		multiChosDia.setTitle("多项选择对话框");
		multiChosDia.setMultiChoiceItems(mList, mChoseSts,
				new DialogInterface.OnMultiChoiceClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which,
							boolean isChecked) {
						// TODO Auto-generated method stub
						if (isChecked) {
							myChose.add(which);
						} else {
							myChose.remove(which);
						}
					}
				});
		multiChosDia.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						int size = myChose.size();
						String str = "";
						for (int i = 0; i < size; i++) {
							str += mList[myChose.get(i)];
						}
					}
				});
		multiChosDia.create().show();
	}

	// 进度读取框需要模拟读取
	ProgressDialog mReadProcessDia = null;
	public final static int MAX_READPROCESS = 100;

	private void showReadProcess() {
		mReadProcessDia = new ProgressDialog(this);
		mReadProcessDia.setProgress(0);
		mReadProcessDia.setTitle("进度条窗口");
		mReadProcessDia.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mReadProcessDia.setMax(MAX_READPROCESS);
		mReadProcessDia.show();
	}

	/* 读取中的对话框 */
	private void showProcessDia() {
		mLoadFontProcessDia = new ProgressDialog(this);
		mLoadFontProcessDia.setTitle("进度条框");
		mLoadFontProcessDia.setMessage("内容读取中...");
		mLoadFontProcessDia.setIndeterminate(true);
		mLoadFontProcessDia.show();
	}

	// 新开启一个线程，循环的累加，一直到100然后在停止
	// @Override
	// public void run()
	// {
	// int Progress= 0;
	// while(Progress < MAX_READPROCESS)
	// {
	// try {
	// Thread.sleep(100);
	// Progress++;
	// mReadProcessDia.incrementProgressBy(1);
	// } catch (InterruptedException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// //读取完了以后窗口自消失
	// mReadProcessDia.cancel();
	// }

	public static final int SYSTEM_UI_FLAG_TRANSLUCENT_BAR = 0x00000800;
	public static final int SYSTEM_UI_FLAG_CUSTOM_BAR_COLOR = 0x00002000;

	private void switchTransSystemUI(boolean on) {
		View view = getWindow().getDecorView();
		int systemUIVis = view.getSystemUiVisibility();
		if (on) {
			systemUIVis |= FLAG_TRANSLUCENT_STATUS;
		} else {
			systemUIVis &= ~FLAG_TRANSLUCENT_STATUS;
		}
		view.setSystemUiVisibility(systemUIVis);

		Field drawsSysBackgroundsField;
		try {
			drawsSysBackgroundsField = WindowManager.LayoutParams.class
					.getField("FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS");
			getWindow().addFlags(drawsSysBackgroundsField.getInt(null));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* 普通的对话框 */
	private void showMyDia() {
		Thread.currentThread().getStackTrace()[2].getLineNumber();
		DemoAlertDialog normalDia = new DemoAlertDialog(this);
		// AlertDialog.Builder normalDia=new AlertDialog.Builder(this);
		// new AlertDialog.Builder(this,AlertDialog.THEME_TRADITIONAL);
		// normalDia.setIcon(R.drawable.ic_launcher);
		// normalDia.setTitle("普通的对话框");
		normalDia.setMessage("普通对话框的message内容");
		// normalDia.setPositiveButton("确定", new
		// DialogInterface.OnClickListener() {
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// }
		// });
		// normalDia.setNegativeButton("取消", new
		// DialogInterface.OnClickListener() {
		// @Override
		// public void onClick(DialogInterface dialog, int which) {
		// // TODO Auto-generated method stub
		// }
		// });
		// normalDia.create().show();
		
		
		
		normalDia.show();
		normalDia.getWindow().setLayout(1536, 2048);
		
		View view = normalDia.getWindow().getDecorView();
		int systemUIVis = view .getSystemUiVisibility();
		systemUIVis |= FLAG_TRANSLUCENT_STATUS;
		view .setSystemUiVisibility(systemUIVis);

		Field drawsSysBackgroundsField;
		try {
			drawsSysBackgroundsField = WindowManager.LayoutParams.class
					.getField("FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS");
			getWindow().addFlags(drawsSysBackgroundsField.getInt(null));
		} catch (Exception e) {
			e.printStackTrace();
		}
		normalDia.show();
//		View view = normalDia.getWindow().getDecorView();
//		int systemUIVis = view .getSystemUiVisibility();
//		systemUIVis |= FLAG_TRANSLUCENT_STATUS;
//		view .setSystemUiVisibility(systemUIVis);
//
//		Field drawsSysBackgroundsField;
//		try {
//			drawsSysBackgroundsField = WindowManager.LayoutParams.class
//					.getField("FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS");
//			getWindow().addFlags(drawsSysBackgroundsField.getInt(null));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

    private void setLockScreenWallpaper(String wallpaperPath) {
        Intent intent = new Intent("com.eebbk.lockscreen.wallpaper.change");
        intent.putExtra("keyguardwallpaper", wallpaperPath);
        sendBroadcast(intent);
    }
    
    private void handleConfirmDlgMsg( int flag) {
		Log.d("@M_" + TAG, "Show confirm dialog");
		Builder builder = new AlertDialog.Builder(this);
//		builder.setTitle(R.string.notify_dialog_title);
		builder.setCancelable(false);

		LayoutInflater inflater = LayoutInflater.from(this);
//		View view = inflater.inflate(R.layout.notify_dialog_customview, null);
//		builder.setView(view);
//		TextView messageText = (TextView) view.findViewById(R.id.message);
//		mTimeCountDown = (TextView) view.findViewById(R.id.count_timer);
//		mCheckBox = (CheckBox) view.findViewById(R.id.checkbox);
//		mCheckBox.setChecked(true);
//		if ((flag & IMobileManager.PERMISSION_FLAG_USERCONFIRM) > 0) {
//			mCheckBox.setVisibility(View.GONE);
//		}
//		
//		String label = PermControlUtils.getApplicationName(this, record.mPackageName);
//		String msg = getString(R.string.notify_dialog_msg_body, label,
//				PermControlUtils.getMessageBody(this, record.mPermissionName));
//		messageText.setText(msg);

		AlertDialog mAlertDlg = builder.create();
//		mAlertDlg.setOnDismissListener(this);
		mAlertDlg.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//		setStatusBarEnableStatus(false); 
		mAlertDlg.show();

		mAlertDlg.getWindow().getDecorView().setBackground(null);
		mAlertDlg.getWindow().setBackgroundDrawable(null);
//		mTimeCountDown = (TextView) mAlertDlg.findViewById(android.R.id.button2);
//		((TextView) mAlertDlg.findViewById(android.R.id.title)).setGravity(Gravity.CENTER);
//		updateCount(COUNT_DOWN_TIMER);
	}
    
    final public static int PRIVATE_FLAG_PREVENT_DIALOG_CENTER = 0x08000000;
    final public static int PRIVATE_FLAG_PREVENT_DIALOG_NO_ANIMATE = 0x10000000;
//	public static void setDialogCenter(Activity activity)
//	{
//		setFlags(activity,PRIVATE_FLAG_PREVENT_DIALOG_CENTER,PRIVATE_FLAG_PREVENT_DIALOG_CENTER);
//	}
	private static void setDialogCenter(Window window) {
		try {
			Method method = Window.class.getDeclaredMethod("setPrivateFlags",int.class,int.class);   
			method.setAccessible(true);
			method.invoke(window, PRIVATE_FLAG_PREVENT_DIALOG_CENTER,PRIVATE_FLAG_PREVENT_DIALOG_CENTER);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void setDialogNoAnimate(Window window) {
		try {
			Method method = Window.class.getDeclaredMethod("setPrivateFlags",int.class,int.class);   
			method.setAccessible(true);
			method.invoke(window, PRIVATE_FLAG_PREVENT_DIALOG_NO_ANIMATE,PRIVATE_FLAG_PREVENT_DIALOG_NO_ANIMATE);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

//	public static void setFlags(Activity activity, int flag, int mask)
//	{
//		try {
//			setPrivateFlags(activity,flag,mask);
//		} catch (Exception e) {
//		}
//	}
}
