//package com.example.views;
//
//
//import com.example.utils.BitmapUtils;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Canvas;
//import android.graphics.Paint;
//import android.graphics.Rect;
//import android.util.AttributeSet;
//import android.util.DisplayMetrics;
//import android.view.Display;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.FrameLayout;
//
//public class BlurControlView extends FrameLayout
//{
//	private Context mContext;
//	private BlurBkMaker mBlurBkMaker;// = new BlurBkMaker(mContext);
//    private Display mDisplay;
//    private DisplayMetrics mDisplayMetrics = new DisplayMetrics();
//    private boolean bBlur = false;
//    private Rect mRcSrc = null;
//    private Rect mRcDst = null;
//    
//	public BlurControlView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		mContext = context;
//		mBlurBkMaker = new BlurBkMaker(mContext);
//        setChildrenDrawingOrderEnabled(true);
//	}
//	
//	public void setBlurInvalible(boolean b)
//	{
//		bBlur = b;
//		if(!bBlur)
//		{
//			this.setVisibility(View.GONE);
//		}
//		else
//		{
//			this.setVisibility(View.VISIBLE);
//		}
//		
//	}
//	
//	@Override
//	protected void dispatchDraw(Canvas canvas) {
//			int w = getScreenW();
//			int h = getScreenH();
//			Bitmap blurBk = getBlurBk();
//			if (null != blurBk && !blurBk.isRecycled()) {
//				int blurTop = 0;
//				int blurH = blurBk.getHeight();
////				Debug.d("dispatchDraw bmp = " + blurBk + " blurTop = " + blurTop + " blurH = " + blurH);
//				// if (mStatusBar.isStatusBarTranslucent()) {
//				// blurTop = mStatusBar.getStatusBarView().getHeight();
//				// blurTop = mStatusBar.getStatusBarBlurH();
//				// }
//				mRcSrc.set(0, blurTop, blurBk.getWidth(), blurH);
//				mRcDst.set(0, 0, w, h);
//				canvas.drawBitmap(blurBk, mRcSrc, mRcDst, getBlurPaint());
//				// the bk color must draw after blur bk draw, otherwise the
//				// paint alpha
//				// will effective the bk color.
//				canvas.drawColor(getBlurBkColor());
//			}
//		super.dispatchDraw(canvas);
//	}
//	
//	public boolean isBlurEnable()
//	{
//		return bBlur;
//	}
//	
//	
//	public void makeBlurBk(boolean excludeSystemUILayer) {
//		
//		if (blurScreenshot(excludeSystemUILayer)) {
//			mBlurBkMaker.setDrawFraction(1.0f);
//			setBlurInvalible(true);
////			mStatusBarView.blur(false);
//			// mNotificationPanel.blur();
////			mNotificationPanelHolder.blur();
//			invalidate();
//		}
//	}
//
//	private boolean blurScreenshot(boolean excludeSystemUILayer) {
//		boolean ret = mBlurBkMaker.screenshot(false,
//				excludeSystemUILayer);
//		if (ret) {
//			mBlurBkMaker.blurScreenshot();
//			return true;
//		} else {
//			return false;
//		}
//	}
// 
//	public void setFraction(float f)
//	{
////		Debug.d("child setBlurFraction f = " + f);
//		mBlurBkMaker.setDrawFraction(f);
//		invalidate();
//	}
//	
//	public Bitmap getBlurBk() {
//		return mBlurBkMaker.getBlurBk();
//	}
//
//	public int getStatusBarBlurH() {
//		return mBlurBkMaker.getStatusBarBlurH();
//	}
//
//	public int getNavigationBarBlurH() {
//		return mBlurBkMaker.getNavigationBarBlurH();
//	}
//
//	public int getStatusBarH() {
//		return mBlurBkMaker.getStatusBarH();
//	}
//
//	public int getNavigationBarH() {
//		return mBlurBkMaker.getNavigationBarH();
//	}
//
//	public int getScreenW() {
//		if(mDisplay == null)
//		{
//			mDisplay = ((WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//		}
//		mDisplay.getRealMetrics(mDisplayMetrics);
//		return mDisplayMetrics.widthPixels;
//	}
//
//	public int getScreenH() {
//		if(mDisplay == null)
//		{
//			mDisplay = ((WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
//		}
//		mDisplay.getRealMetrics(mDisplayMetrics);
//		return mDisplayMetrics.heightPixels;
//	}
//
//	public Paint getBlurPaint() {
//		return mBlurBkMaker.getDrawPaint();
//	}
//	
//	public int getBlurBkColor() {
//		return mBlurBkMaker.getDrawColor();
//	}
//}
