package com.xiajue.count.count.biz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiajue.count.count.R;
import com.xiajue.count.count.bean.CountDataBean;
import com.xiajue.count.count.constant.TypeModel;
import com.xiajue.count.count.utils.StringUtils;

import java.util.List;

import static com.xiajue.count.count.constant.ConfigHelper.setFractionTextColor;

/**
 * Created by xiaJue on 2017/12/28.
 */
public class PHAdapter extends BaseAdapter {
    private List<CountDataBean> mList;
    private LayoutInflater mLayoutInflater;

    public PHAdapter(Context context, List<CountDataBean> countDataBeanList) {
        mLayoutInflater = LayoutInflater.from(context);
        mList = countDataBeanList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewH h;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_ph, null);
            h = new ViewH();
            h.fraction = (TextView) convertView.findViewById(R.id
                    .item_ph_fraction);
            h.type = (TextView) convertView.findViewById(R.id
                    .item_ph_type);
            h.numbers = (TextView) convertView.findViewById(R.id
                    .item_ph_numbers);
            h.model = (TextView) convertView.findViewById(R.id
                    .item_ph_model);
            h.countNumber = (TextView) convertView.findViewById(R.id
                    .item_ph_count_number);
            h.useTime = (TextView) convertView.findViewById(R.id
                    .item_ph_use_time);
            h.phDate = (TextView) convertView.findViewById(R.id
                    .item_ph_date);
            convertView.setTag(h);
        } else {
            h = (ViewH) convertView.getTag();
        }
        CountDataBean bean = mList.get(position);
        setText(h.type, TypeModel.getStringFromInt(bean.type, TypeModel.GET_TYPE));
        setText(h.model, TypeModel.getStringFromInt(bean.model, TypeModel.GET_MODEL));
        setText(h.numbers, bean.getNumbers_from() + "-" + bean.getNumbers_to());
        setText(h.fraction, bean.getFraction() + "分");
        setFractionTextColor(h.fraction, bean.getFraction());
        setText(h.countNumber, bean.getCountNumber() + "题");
        setText(h.useTime, "用时 " + StringUtils.formatDate(bean.getUseTime()));
        setText(h.phDate, StringUtils.formatDateString(bean.getLastTime()));
        return convertView;
    }

    private void setText(TextView tv, String text) {
        tv.setText(text);
    }

    class ViewH {
        public TextView fraction;
        public TextView type;
        public TextView numbers;
        public TextView model;
        public TextView countNumber;
        public TextView useTime;
        public TextView phDate;
    }
}
