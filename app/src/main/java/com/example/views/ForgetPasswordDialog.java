package com.example.views;

import com.bbk.example.demo.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ForgetPasswordDialog extends Dialog implements android.view.View.OnClickListener{
	private TextView mCurrentIDView;
	private EditText mInputPasswordEdit;
	private Button mOkBtn;
	private Button mCancelBtn;
	private long mCurrentId;
	private long mPasswordId; 
	

	public ForgetPasswordDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public ForgetPasswordDialog(Context context, int theme) {
		super(context, theme);
	}

	public ForgetPasswordDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forget_password_dialog);
		init();
	}
	
	private void init()
	{
		mCurrentIDView = (TextView)findViewById(R.id.current_id);
		mInputPasswordEdit = (EditText)findViewById(R.id.input_password);
		mOkBtn = (Button)findViewById(R.id.ok);
		mCancelBtn = (Button)findViewById(R.id.cancel);
		mOkBtn.setOnClickListener(this);
		mCancelBtn.setOnClickListener(this);
		mInputPasswordEdit.requestFocus();
		mCurrentIDView.setText("" + mCurrentId);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.ok:
		{
			String str = mInputPasswordEdit.getText().toString();
			if(str.equals(""+ mPasswordId))
			{
				dismiss();
			}
			else
			{
			}
			break;
		}
		case R.id.cancel:
		{
			dismiss();
			break;
		}
		}
	}

	public long getmCurrentId() {
		return mCurrentId;
	}

	public void setmCurrentId(long mCurrentId) {
		this.mCurrentId = mCurrentId;
	}

	public long getmPasswordId() {
		return mPasswordId;
	}

	public void setmPasswordId(long mPasswordId) {
		this.mPasswordId = mPasswordId;
	}
}
