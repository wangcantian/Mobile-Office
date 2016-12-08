package com.mo.mobileoffice.function.announce.popview;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.common.base.BasePopView;
import com.mo.mobileoffice.common.tool.DisplayTool;
import com.mo.mobileoffice.common.tool.StringTool;
import com.mo.mobileoffice.common.widget.SlipPopWin.OnScrollListener;
import com.mo.mobileoffice.function.announce.bean.AnnounceBean;
import com.mo.mobileoffice.function.upload.tool.ImageLoader;
import com.squareup.picasso.Picasso;

/** 公共详情 **/
public class AnnoDetailView extends BasePopView implements OnScrollListener {
	private TextView tv_title;
	private ImageView iv_headpic;
	private TextView tv_author;
	private TextView tv_content;
	private LinearLayout ll_pic_list;
	
	private AnnounceBean mAnno;
	private boolean mIsShowPic = false;// 当前公共是否有包含图片，没有的话退出不用清除缓存

	public AnnoDetailView(Context context, Bundle bundle) {
		super(context, bundle);
	}
	
	@Override
	protected void init() {
		mAnno = (AnnounceBean) mBundle.getSerializable("anno");
		
		tv_title = (TextView) mRootView.findViewById(R.id.tv_title);
		iv_headpic = (ImageView) mRootView.findViewById(R.id.iv_headpic);
		tv_author = (TextView) mRootView.findViewById(R.id.tv_author);
		tv_content = (TextView) mRootView.findViewById(R.id.tv_content);
		ll_pic_list = (LinearLayout) mRootView.findViewById(R.id.ll_pic_list);
		setOnScrollListener(this);
		
		tv_title.setText(mAnno.getTitle());
		tv_author.setText(mAnno.getUser_name() + "   " + mAnno.getCreate_time());
		tv_content.setText(mAnno.getContent());
		
		Picasso.with(mContext).load(mAnno.getPic_url()).placeholder(R.drawable.ico_default_headpic).into(iv_headpic);
	}

	@Override
	protected int getContentViewId() {
		return R.layout.layout_anno_detail;
	}

	@Override
	public void OnScrollToTopEnd() {
		// 当滑动到顶部才开始
		String[] urls = mAnno.getAnn_pic_url().split(",");
		for (int i = 0; i < urls.length; i++) {
			if (StringTool.isEmpty(urls[i])) {
				continue;
			}
			mIsShowPic = true;
			int width = DisplayTool.getScreenWidth(mContext) - DisplayTool.dp2px(mContext, 40);
			int height = width / 5 * 3;
			ImageView imageView = new ImageView(mContext);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, height);
			params.bottomMargin = 10;
			ll_pic_list.addView(imageView, params);
			Picasso.with(mContext).load(urls[i]).into(imageView);
		}
	}

	@Override
	public void OnScrollToEnd() {
		if (mIsShowPic) {
			// 当滑动到底部就清楚图片缓存
			ImageLoader.getInstance().cancelTask();
			ImageLoader.getInstance().clearCache();
		}
	}

}
