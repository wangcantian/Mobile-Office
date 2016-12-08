package com.mo.mobileoffice.function.checkin.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mo.mobileoffice.R;
import com.mo.mobileoffice.function.checkin.bean.CheckIn_HistoryDataFragment_Respond.Data;
import com.mo.mobileoffice.function.user.model.UserModel;
import com.squareup.picasso.Picasso;

public class CheckInHistoryAdater extends Adapter<ViewHolder> {
	private Context context;
	private ArrayList<Data> data;
	private UserModel mUserModel;

	private static final int TYPE_ITEM = 0;
	private static final int TYPE_FOOTER = 1;

	public interface OnItemClickListener {
		void onItemClick(View view, int position);

		void onItemLongClick(View view, int position);
	}

	private OnItemClickListener onItemClickListener;

	public CheckInHistoryAdater(Context context, ArrayList<Data> data) {
		this.context = context;
		this.data = data;
		mUserModel = new UserModel(context);
	}

	public void addAll(ArrayList<Data> datas) {
		data.addAll(datas);
	}

	@Override
	public int getItemCount() {
		return data.size() == 0 ? 0 : data.size() + 1;
	}

	@Override
	public int getItemViewType(int position) {
		if (position + 1 == getItemCount()) {
			return TYPE_FOOTER;
		} else {
			return TYPE_ITEM;
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		if (viewType == TYPE_ITEM) {
			View view = LayoutInflater.from(context).inflate(
					R.layout.fragment_checkinhistory_item,parent,false);
			CheckInHistoryHolder holder = new CheckInHistoryHolder(view);
			return holder;
		} else if (viewType == TYPE_FOOTER) {
			View view = LayoutInflater.from(context).inflate(
					R.layout.fragment_checkin_history_footerview,parent,false);
			FooterViewHolder holder = new FooterViewHolder(view);
			return holder;
		}
		return null;
	}

	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {
		if (holder instanceof CheckInHistoryHolder) {
			CheckInHistoryHolder mHolder = (CheckInHistoryHolder) holder;
			mHolder.userName.setText(mUserModel.getCurrUserInfo().getName());
			mHolder.place.setText("签到地点:" + data.get(position).getPlace());
			mHolder.time.setText("签到时间:" + data.get(position).getTime().split(" ")[0]);
			Picasso.with(context).load(mUserModel.getUserHeadPic()).
			placeholder(R.drawable.ico_default_headpic).into(mHolder.userImage);

			if (onItemClickListener != null) {
				holder.itemView.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						int position = holder.getPosition();
						onItemClickListener.onItemClick(v, position);
					}
				});

				holder.itemView
						.setOnLongClickListener(new OnLongClickListener() {

							@Override
							public boolean onLongClick(View v) {
								int position = holder.getPosition();
								onItemClickListener
										.onItemLongClick(v, position);
								return false;
							}
						});
			}
		}
	}

	public static class CheckInHistoryHolder extends ViewHolder {
		TextView userName;
		TextView place;
		TextView time;
		ImageView userImage;

		public CheckInHistoryHolder(View view) {
			super(view);
			userName = changeView(view, R.id.user);
			place = changeView(view, R.id.place);
			time = changeView(view, R.id.time);
			userImage = changeView(view, R.id.userimg);
		}
	}

	static class FooterViewHolder extends ViewHolder {

		public FooterViewHolder(View view) {
			super(view);
		}

	}

	@SuppressWarnings("unchecked")
	private static <T> T changeView(View view, int id) {
		return (T) (view.findViewById(id));
	}
}
