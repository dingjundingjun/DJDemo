/**
 * Copyright(C): 2014 步步高教育电子有限公司 Project Name: SynRecite_H10 Package Name:
 * com.eebbk.recite Filename: WordMemoryTestFragment.java Author(S): Rjdeng
 * Created Date: 2014-3-11 上午10:24:03 Version: V1.00 Description: 单词测试界面
 */

package com.example.Activity;

import com.bbk.example.demo.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;


public class TestFragment extends DialogFragment
{
	public static final String FRAGMENT_TAG = "WordMemoryTestFragment";

	private Activity mActivity;
	private AlertDialog mAlertDialog;
	private View mView;
	private LayoutInflater mInflater;

	public void onAttach(Activity activity)
	{
		// TODO Auto-generated method stub
		super.onAttach(activity);

		try
		{
		}
		catch (Exception e)
		{
			// TODO: handle exception
			throw new ClassCastException(activity.toString()
					+ " must implement onButtonClickListener");
		}
	}

	@SuppressLint("NewApi")
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		mActivity = getActivity();

		mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = mInflater.inflate(R.layout.main_word_test, null);

		// 添加动画
		mView.setScaleX(0.5f);
		mView.setScaleY(0.5f);
		mView.animate()
				.scaleX(1f)
				.scaleY(1f)
				.setDuration(500)
				.setInterpolator(
						AnimationUtils.loadInterpolator(mActivity,
								android.R.anim.decelerate_interpolator))
				.setListener(null);

		mAlertDialog = new AlertDialog.Builder(mActivity,AlertDialog.THEME_TRADITIONAL).setView(mView).create();
		mAlertDialog.show();
		mAlertDialog.setCancelable(true);
		mAlertDialog.setCanceledOnTouchOutside(true);

		View view = ((View) mView.getParent());
		if(view != null)
		{
			view.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
				}
			});
			
			View viewRoot = (View) mView.getParent().getParent();
			viewRoot.setBackgroundColor(0x00000000);
			viewRoot.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					mAlertDialog.dismiss();
				}
			});
		}
		

		return mAlertDialog;
	}

	public void show()
	{
		if (mAlertDialog != null)
		{
			mAlertDialog.show();
		}
	}

}
