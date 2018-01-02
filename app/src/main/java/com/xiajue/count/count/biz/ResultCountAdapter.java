package com.xiajue.count.count.biz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiajue.count.count.R;
import com.xiajue.count.count.bean.ResultCountListBean;

import java.util.List;

/**
 * Created by xiaJue on 2017/12/25.
 */

public class ResultCountAdapter extends BaseAdapter {
    private List<ResultCountListBean> mList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public ResultCountAdapter(Context context, List<ResultCountListBean> list) {
        mList = list;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
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
            convertView = mLayoutInflater.inflate(R.layout.item_result_count, null);
            h = new ViewH();
            convertView.setTag(h);
            h.mAnswerTextView = (TextView) convertView.findViewById(R.id
                    .item_result_user_answer_textView);
            h.mSubjectTextView = (TextView) convertView.findViewById(R.id
                    .item_result_subject_textView);
        } else {
            h = (ViewH) convertView.getTag();
        }
        ResultCountListBean bean = mList.get(position);
        h.mSubjectTextView.setText(bean.getSubject());
        h.mAnswerTextView.setText(bean.getAnswer());
        h.mAnswerTextView.setTextColor(bean.getAnswerColor());
        return convertView;
    }

    class ViewH {
        public TextView mSubjectTextView;
        public TextView mAnswerTextView;
    }
}
