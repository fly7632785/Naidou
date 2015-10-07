package com.itspeed.naidou.app.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.itspeed.naidou.R;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

/**
 * 指示器
 * Created by berial on 15/9/30.
 */
public class AdapterIndicator extends RadioGroup {

	private Context mContext;

	private int mCount;

	public AdapterIndicator(Context context) {
		this(context, null);
	}

	public AdapterIndicator(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setGravity(Gravity.CENTER);
		setOrientation(HORIZONTAL);
	}

	public void setPointCount(int count) {
		mCount = count;
		initIndicator();
	}

	private void initIndicator() {
		removeAllViews();
		for(int i = 0; i < mCount; i++) {
			RadioButton button = new RadioButton(mContext);
			button.setButtonDrawable(R.drawable.selector_indicator_color);
			button.setPadding(5, 0, 5, 0);
			button.setId(i);
			addView(button);
		}
		if(mCount != 0) {
			select(0);
		}
	}

	public void select(int position) {
		check(position);
	}

	/**
	 * 绑定RecyclerViewPager
	 * @param viewPager RecyclerViewPager
	 */
	public void bindView(final RecyclerViewPager viewPager) {
		if(viewPager != null) {
			viewPager.addOnPageChangedListener(new RecyclerViewPager.OnPageChangedListener() {
				@Override
				public void OnPageChanged(int i, int position) {
					select(position % mCount);
				}
			});
//			setOnCheckedChangeListener(new OnCheckedChangeListener() {
//				@Override
//				public void onCheckedChanged(RadioGroup group, int checkedId) {
//					viewPager.smoothScrollToPosition(checkedId);
//				}
//			});
		}
	}
}
