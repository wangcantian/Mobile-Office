package com.mo.mobileoffice.common.widget;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mo.mobileoffice.R;

public class CalendarTest extends LinearLayout {
	public static final int COLOR_BG_WEEK_TITLE = Color.parseColor("#FF4167"); // ���ڱ��ⱳ����ɫ
	public static final int COLOR_TX_WEEK_TITLE = Color.parseColor("#FFFFFF"); // ���ڱ���������ɫ
	public static final int BEFORE_TODAY_BACKGROUND = Color
			.parseColor("#FFE4E4E4"); // ���ڱ���������ɫ
	// public static final int COLOR_TX_THIS_MONTH_DAY =
	// Color.parseColor("#aa564b4b"); // ��ǰ������������ɫ
	public static final int COLOR_TX_THIS_MONTH_DAY = Color
			.parseColor("#000000"); // ��ǰ������������ɫ
	public static final int COLOR_TX_OTHER_MONTH_DAY = Color
			.parseColor("#ff999999"); // ����������������ɫ
	public static final int COLOR_TX_THIS_DAY = Color.parseColor("#CD2836"); // 今天的日期颜色
	public static final int COLOR_BG_THIS_DAY = Color.parseColor("#ffcccccc"); // ��������������ɫ
	public static final int COLOR_BG_CALENDAR = Color.parseColor("#ffeeeeee"); // ��������ɫ

	private int ROWS_TOTAL = 6; // ����������
	private int COLS_TOTAL = 7; // ����������
	private String[][] dates = new String[6][7]; // ��ǰ��������

	private int calendarYear; // �������
	private int calendarMonth; // �����·�
	private Date thisday = new Date(); // ����
	private Date calendarday; // ��������µ�һ��(1��)
	private LinearLayout currentCalendar;

	private Map<String, Integer> marksMap = new HashMap<String, Integer>(); // ����ĳ�����ӱ���ע(Integer

	private Map<String, Integer> dayBgColorMap = new HashMap<String, Integer>(); // ����ĳ�����ӵı���ɫ
	private String[] weekday = new String[] { "日", "一", "二", "三", "四", "五", "六" }; // ���ڱ���
	private LinearLayout firstCalendar; // ��һ������

	public CalendarTest(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CalendarTest(Context context) {
		this(context, null);
	}

	private void init() {
		setBackgroundColor(COLOR_BG_CALENDAR);
		// ��ʼ����һ������
		firstCalendar = new LinearLayout(getContext());
		firstCalendar.setOrientation(LinearLayout.VERTICAL);
		firstCalendar.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
		addView(firstCalendar);
		drawFrame(firstCalendar);
		currentCalendar = firstCalendar;
		calendarYear = thisday.getYear() + 1900;
		calendarMonth = thisday.getMonth();
		calendarday = new Date(calendarYear - 1900, calendarMonth, 1);
		setCalendarDate();
	}

	private void drawFrame(LinearLayout oneCalendar) {
		// �����ĩ���Բ���
		LinearLayout title = new LinearLayout(getContext());
		title.setBackgroundColor(COLOR_BG_WEEK_TITLE);
		title.setOrientation(LinearLayout.HORIZONTAL);
		Resources res = getResources();
		tb = res.getDimension(R.dimen.historyscore_tb);
		LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(
				MarginLayoutParams.MATCH_PARENT,
				MarginLayoutParams.WRAP_CONTENT, 0.5f);
		title.setLayoutParams(layout);
		oneCalendar.addView(title);

		// �����ĩTextView
		for (int i = 0; i < COLS_TOTAL; i++) {
			TextView view = new TextView(getContext());
			view.setGravity(Gravity.CENTER);
			view.setPadding(0, 10, 0, 10);
			view.setText(weekday[i]);
			view.setTextColor(Color.WHITE);
			view.setLayoutParams(new LinearLayout.LayoutParams(0, -1, 1));
			title.addView(view);
		}

		// ������ڲ���
		LinearLayout content = new LinearLayout(getContext());
		content.setOrientation(LinearLayout.VERTICAL);
		content.setLayoutParams(new LinearLayout.LayoutParams(-1, 0, 7f));
		oneCalendar.addView(content);

		// �������TextView
		for (int i = 0; i < ROWS_TOTAL; i++) {
			LinearLayout row = new LinearLayout(getContext());
			row.setOrientation(LinearLayout.HORIZONTAL);
			row.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, 0, 1));
			content.addView(row);
			// ���������ϵ���
			for (int j = 0; j < COLS_TOTAL; j++) {
				RelativeLayout col = new RelativeLayout(getContext());
				col.setLayoutParams(new LinearLayout.LayoutParams(0,
						LayoutParams.MATCH_PARENT, 1));
				col.setBackgroundResource(R.drawable.calendar_bg);
				// col.setBackgroundResource(R.drawable.sign_dialog_day_bg);
				col.setClickable(false);
				row.addView(col);
			}

		}
	}

	private void setCalendarDate() {
		int weekday = calendarday.getDay();
		int firstDay = 1;
		int day = firstDay;
		int lastDay = getDateNum(calendarday.getYear(), calendarday.getMonth());
		int nextMonthDay = 1;
		int lastMonthDay = 1;

		for (int i = 0; i < ROWS_TOTAL; i++) {
			for (int j = 0; j < COLS_TOTAL; j++) {
				if (i == 0 && j == 0 && weekday != 0) {
					int year = 0;
					int month = 0;
					int lastMonthDays = 0;
					if (calendarday.getMonth() == 0) {
						year = calendarday.getYear() - 1;
						month = Calendar.DECEMBER;
					} else {
						year = calendarday.getYear();
						month = calendarday.getMonth() - 1;
					}

					lastMonthDays = getDateNum(year, month);
					int firstShowDay = lastMonthDays - weekday + 1;
					for (int k = 0; k < weekday; k++) {
						lastMonthDay = firstShowDay + k;
						RelativeLayout group = getDateView(0, k);
						group.setGravity(Gravity.TOP);
						TextView view = null;
						if (group.getChildCount() > 0) {
							view = (TextView) group.getChildAt(0);
						} else {
							RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
									-1, -1);
							view = new TextView(getContext());
							view.setLayoutParams(params);
							group.addView(view);
						}
						view.setText(Integer.toString(lastMonthDay));
						view.setTextColor(COLOR_TX_OTHER_MONTH_DAY);
						dates[0][k] = format(new Date(year, month, lastMonthDay));
						if (dayBgColorMap.get(dates[0][k]) != null) {
							view.setBackgroundResource(dayBgColorMap
									.get(dates[0][k]));
						} else {
							view.setBackgroundColor(Color.TRANSPARENT);
						}
						setMarker(group, 0, k);
					}
					j = weekday - 1;
				} else {
					RelativeLayout group = getDateView(i, j);
					group.setGravity(Gravity.TOP);
					TextView view = null;
					if (group.getChildCount() > 0) {
						view = (TextView) group.getChildAt(0);
					} else {
						RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
								-1, -1);
						view = new TextView(getContext());
						view.setLayoutParams(params);
						view.setGravity(Gravity.TOP);
						group.addView(view);
					}

					// ����
					if (day <= lastDay) {
						dates[i][j] = format(new Date(calendarday.getYear(),
								calendarday.getMonth(), day));
						view.setText(Integer.toString(day));
						// ����
						if (thisday.getDate() == day
								&& thisday.getMonth() == calendarday.getMonth()
								&& thisday.getYear() == calendarday.getYear()) {
							// view.setText("����");
							view.setTextColor(COLOR_TX_THIS_DAY);
							// view.setBackgroundResource(R.drawable.bg_sign_today);
						} else if (thisday.getMonth() == calendarday.getMonth()
								&& thisday.getYear() == calendarday.getYear()) {
							// ���Ʊ��µ���ɫ
							view.setTextColor(COLOR_TX_THIS_MONTH_DAY);
						} else {
							// ��������
							view.setTextColor(COLOR_TX_OTHER_MONTH_DAY);
						}
						// ��������������һ��Ĭ�ϵ�"����"����ɫ��������������ʱ���Ÿ�������䱳��ɫ
						// �������ڱ���ɫ
						if (dayBgColorMap.get(dates[i][j]) != null) {
							// view.setTextColor(Color.WHITE);
							// view.setBackgroundResource(dayBgColorMap.get(dates[i][j]));
						}
						// ���ñ��
						setMarker(group, i, j);
						day++;
						// �¸���
					} else {
						if (calendarday.getMonth() == Calendar.DECEMBER) {
							dates[i][j] = format(new Date(
									calendarday.getYear() + 1,
									Calendar.JANUARY, nextMonthDay));
						} else {
							dates[i][j] = format(new Date(
									calendarday.getYear(),
									calendarday.getMonth() + 1, nextMonthDay));
						}
						view.setText(Integer.toString(nextMonthDay));
						view.setTextColor(COLOR_TX_OTHER_MONTH_DAY);
						// �������ڱ���ɫ
						if (dayBgColorMap.get(dates[i][j]) != null) {
							// view.setBackgroundResource(dayBgColorMap
							// .get(dates[i][j]));
						} else {
							view.setBackgroundColor(Color.TRANSPARENT);
						}
						// ���ñ��
						setMarker(group, i, j);
						nextMonthDay++;
					}
				}
			}
		}
	}

	private float tb;

	// ���ñ��
	private void setMarker(RelativeLayout group, int i, int j) {
		int childCount = group.getChildCount();
		// dates[i][j]=2015-12-20��ΪҪ�Աȵ����ڣ�marksMap�а�����dates[i][j]ʱ�ͽ��������if���
		if (marksMap.get(dates[i][j]) != null) {
			if (childCount < 2) {
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
						(int) (tb * 2), (int) (tb * 2));
				// params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				// params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				params.setMargins(0, 0, 1, 1);
				params.addRule(RelativeLayout.CENTER_IN_PARENT);
				ImageView markView = new ImageView(getContext());
				markView.setImageResource(marksMap.get(dates[i][j]));
				markView.setLayoutParams(params);
				markView.setBackgroundResource(R.drawable.calendar_bg_tag);
				group.addView(markView);
			}
		} else {
			if (childCount > 1) {
				group.removeView(group.getChildAt(1));
			}
		}
	}

	/**
	 * ĳ���Ƿ񱻱����
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public boolean hasMarked(String date) {
		return marksMap.get(date) == null ? false : true;
	}

	/**
	 * ������������ĳ�����ڵı���ɫ
	 * 
	 * @param date
	 * @param color
	 */
	public void setCalendarDayBgColor(Date date, int color) {
		setCalendarDayBgColor(format(date), color);
	}

	/**
	 * ������������ĳ�����ڵı���ɫ
	 * 
	 * @param date
	 * @param color
	 */
	public void setCalendarDayBgColor(String date, int color) {
		dayBgColorMap.put(date, color);
		setCalendarDate();
	}

	public void showCalendar(int year, int month) {
		calendarYear = year;
		calendarMonth = month - 1;
		calendarday = new Date(calendarYear - 1900, calendarMonth, 1);
		setCalendarDate();
	}

	/**
	 * ����������һ�����
	 * 
	 * @param date
	 *            ����
	 * @param id
	 *            bitmap res id
	 */
	public void addMark(Date date, int id) {
		addMark(format(date), id);

	}

	/**
	 * ����������һ�����
	 * 
	 * @param date
	 *            ����
	 * @param id
	 *            bitmap res id
	 */
	public void addMark(String date, int id) {
		marksMap.put(date, id);
		// setCalendarDate();
		int row = 0, col = 0;
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if (date.equals(dates[i][j])) {
					row = i;
					col = j;
				}
			}
		}
		RelativeLayout group = (RelativeLayout) ((LinearLayout) ((LinearLayout) firstCalendar
				.getChildAt(1)).getChildAt(row)).getChildAt(col);
		if (group != null) {
			setMarker(group, row, col);
		} else {
			Toast.makeText(getContext(), "ǩ��ʧ��", 0).show();
		}

	}

	// ������·���󵽷�������ǩ������ɹ�
	private SendData mSendData;

	public void setSendDate(SendData mSendData) {
		this.mSendData = mSendData;
	}

	public interface SendData {
		public int sendTodayDate();
	}

	/**
	 * ����������һ����
	 * 
	 * @param date
	 *            ����
	 * @param id
	 *            bitmap res id
	 */
	public void addMarks(Date[] date, int id) {
		for (int i = 0; i < date.length; i++) {
			marksMap.put(format(date[i]), id);
			int row = 0, col = 0;
			for (int j = 0; j < 6; j++) {
				for (int k = 0; k < 7; k++) {
					if (date.equals(dates[j][k])) {
						row = j;
						col = k;
					}
				}
			}
			RelativeLayout group = (RelativeLayout) ((LinearLayout) ((LinearLayout) firstCalendar
					.getChildAt(1)).getChildAt(row)).getChildAt(col);
			if (group != null) {
				setMarker(group, row, col);
			}
		}
		// setCalendarDate();
	}

//	/**
//	 * ����������һ����
//	 * 
//	 * @param date
//	 *            ����
//	 * @param id
//	 *            bitmap res id
//	 */
//	public void addMarks(List<String> date, int id) {
//		for (int i = 0; i < date.size(); i++) {
//			marksMap.put(date.get(i), id);
//			int row = 0, col = 0;
//			for (int j = 0; j < 6; j++) {
//				for (int k = 0; k < 7; k++) {
//					if (date.equals(dates[j][k])) {
//						row = j;
//						col = k;
//					}
//				}
//			}
//			RelativeLayout group = (RelativeLayout) ((LinearLayout) ((LinearLayout) firstCalendar
//					.getChildAt(1)).getChildAt(row)).getChildAt(col);
//			if (group != null) {
//				setMarker(group, row, col);
//			}
//		}
//		// setCalendarDate();
//	}
	
	 /**
	   * 在日历上做一组标记
	   * 
	   * @param date 日期
	   * @param id bitmap res id
	   */
	  public void addMarks(List<String> date, int id) {
	    for (int i = 0; i < date.size(); i++) {
	      marksMap.put(date.get(i), id);
	    }
	    setCalendarDate();
	  }

	/**
	 * ���ݵ�ǰ�£�չʾһ������
	 * 
	 * @param year
	 * @param month
	 */
	public void showCalendar() {
		Date now = new Date();
		calendarYear = now.getYear() + 1900;
		calendarMonth = now.getMonth();
		calendarday = new Date(calendarYear - 1900, calendarMonth, 1);
		setCalendarDate();
	}

	/**
	 * �������кŻ�ð�װÿһ�����ӵ�LinearLayout
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	private RelativeLayout getDateView(int row, int col) {
		return (RelativeLayout) ((LinearLayout) ((LinearLayout) currentCalendar
				.getChildAt(1)).getChildAt(row)).getChildAt(col);
	}

	/**
	 * ��Dateת�����ַ���->2013-3-3
	 */
	private String format(Date d) {
		return addZero(d.getYear() + 1900, 4) + "-"
				+ addZero(d.getMonth() + 1, 2) + "-" + addZero(d.getDate(), 2);
	}

	// 2��4
	private static String addZero(int i, int count) {
		if (count == 2) {
			if (i < 10) {
				return "0" + i;
			}
		} else if (count == 4) {
			if (i < 10) {
				return "000" + i;
			} else if (i < 100 && i > 10) {
				return "00" + i;
			} else if (i < 1000 && i > 100) {
				return "0" + i;
			}
		}
		return "" + i;
	}

	private int getDateNum(int year, int month) {
		Calendar time = Calendar.getInstance();
		time.clear();
		time.set(Calendar.YEAR, year + 1900);
		time.set(Calendar.MONTH, month);
		return time.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
}
