package com.example.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;








import java.util.ArrayList;
import java.util.List;

import com.bbk.example.demo.R;
import com.example.utils.Debug;

/**
 * Created by dj on 2015/7/19.
 * email:dingjun0225@gmail.com
 */
public class TitleScroolView extends LinearLayout{
    private Context mContext;
    private View mParentView;
    private LinearLayout mContentLayout;
    private List<String> mTitleList;
    private List<TextView> mTitleViewList = new ArrayList<TextView>();
    private OnTitleClickListener mOnTitleClickListener;
    private final int ANIM_DURATION = 500;
    private final int LABEL_STATUS_NORMAL = 0;
    private final int LABEL_STATUS_PRESS = 100;
    public TitleScroolView(Context context) {
        super(context);
        mContext = context;
    }

    public TitleScroolView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void init(){
        mParentView = View.inflate(mContext, R.layout.title_scroll_layout, null);
        mContentLayout = (LinearLayout)mParentView.findViewById(R.id.content_layout);
        initTitle();
        this.addView(mParentView);
    }

    public void setOnTitleClickListener(OnTitleClickListener otc)
    {
    	mOnTitleClickListener = otc;
    }
    
    public void setTitleList(List<String> list)
    {
        mTitleList = list;

    }

    private void initTitle()
    {
        if(mTitleList == null || mTitleList.size() <= 0)
        {
            return;
        }
        mTitleViewList.clear();
        int len = mTitleList.size();
        Debug.d("mTitleList.size() = " + mTitleList.size());
        final float textSize = mContext.getResources().getDimension(R.dimen.title_scrool_label_text_size);
        int textViewMinWidth = mContext.getResources().getDimensionPixelSize(R.dimen.title_scrool_label_text_min_width);
        final int colorNormal = mContext.getResources().getColor(R.color.title_scrool_text_color_normal);
        final int colorPress = mContext.getResources().getColor(R.color.title_scrool_text_color_press);
        for(int i = 0; i < len;i++)
        {
            Debug.d("title = " + mTitleList.get(i));
            TextView textView = new TextView(mContext);
            textView.setTextSize(textSize);
            textView.setText(mTitleList.get(i));
            textView.setMinWidth(textViewMinWidth);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(colorNormal);
            textView.setId(i);
            textView.setTag(LABEL_STATUS_NORMAL);
            textView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int id = v.getId();
					if(mOnTitleClickListener != null)
					{
						for(int j = 0;j < mTitleViewList.size();j++)
						{
							TextView tempView = mTitleViewList.get(j);
							if(tempView.getId() != id)
							{
								tempView.setTextSize(textSize);
								tempView.setTextColor(colorNormal);
								if(((Integer)tempView.getTag()) == LABEL_STATUS_PRESS)
								{
									Animation am = new ScaleAnimation(1.2f,1.0f,1.2f,1.0f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
									am.setFillAfter(true);
									am.setDuration(ANIM_DURATION);
									tempView.setAnimation(am);
									tempView.setTag(LABEL_STATUS_NORMAL);
								}
							}
							else
							{
								mOnTitleClickListener.onClick(id);
								tempView.setTextSize(mContext.getResources().getDimension(R.dimen.title_scrool_label_text_press_size));
								tempView.setTextColor(colorPress);
								Animation am = new ScaleAnimation(1.0f,1.2f,1.0f,1.2f,Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
								am.setFillAfter(true);
								am.setDuration(ANIM_DURATION);
								tempView.setAnimation(am);
								tempView.setTag(LABEL_STATUS_PRESS);
							}
						}
						
					}
				}
			});
            mContentLayout.addView(textView);
            mTitleViewList.add(textView);
        }
    }

    public interface OnTitleClickListener
    {
    	public abstract void onClick(int id);
    }
}
