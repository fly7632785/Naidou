package com.itspeed.naidou.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.itspeed.naidou.R;
import com.itspeed.naidou.model.bean.CookBook;
import com.itspeed.naidou.model.bean.Topic;

import org.kymjs.kjframe.utils.KJLoger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by jafir on 15/9/14.
 */
public class RecommendAdapter extends BaseAdapter {
    private static final  int TYPE_CookBook = 0;
    private static final  int  TYPE_Topic = 1;

    private Context context;
    //菜谱
    private ArrayList<Object> cookBooks;
    //话题
    private ArrayList<Object> topics;

    //整合数据
    private List<Object> data = new ArrayList<>();


    public RecommendAdapter(Context context, ArrayList<Object> cookBooks, ArrayList<Object> topics) {
        this.context = context;
        this.cookBooks = cookBooks;
        this.topics = topics;

        data.addAll(cookBooks);
        data.addAll(topics);

        //按时间排序来填充数据
        Collections.sort(data, new MyComparator());

        for(Object o:data){
            if(o instanceof CookBook){
                //处理
                CookBook c = (CookBook) o;
                KJLoger.debug("time:+"+c.getTime());
                KJLoger.debug("菜谱");
            }else if (o instanceof Topic){
                //处理
                Topic t = (Topic) o;
                KJLoger.debug("time:+"+t.getTime());
                KJLoger.debug("title:+" + t.getTitle());
                KJLoger.debug("话题");
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        int result = 0;
        if(data.get(position) instanceof CookBook){
            result = TYPE_CookBook;
        }else if(data.get(position) instanceof Topic){
            result = TYPE_Topic;
        }
        return result;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        KJLoger.debug("datasize"+data.size());
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        int type = getItemViewType(position);
        KJLoger.debug("getView " + position + " " + convertView + " type = " + type);
        if (convertView == null) {
            holder1 = new ViewHolder1();
            holder2 = new ViewHolder2();
            switch (type) {
                case TYPE_CookBook:
                    convertView = View.inflate(context,R.layout.item_cookbook, null);
                    holder1.imageView = (ImageView) convertView.findViewById(R.id.item_list_cookbook_img);
                    holder1.time = (TextView) convertView.findViewById(R.id.item_list_cookbook_time);
                    convertView.setTag(R.id.tag_first,holder1);
                    break;
                case TYPE_Topic:
                    convertView = View.inflate(context,R.layout.item_topic, null);
                    holder2.textView= (TextView) convertView.findViewById(R.id.item_list_topic_text);
                    holder2.time= (TextView) convertView.findViewById(R.id.item_list_topic_time);
                    convertView.setTag(R.id.tag_second,holder2);
                    break;
            }

        } else {
            KJLoger.debug("daozherl!!!!!!!!!!!!!");
            switch (type) {
                case TYPE_CookBook:
                    holder1 = (ViewHolder1) convertView.getTag(R.id.tag_first);
                    break;
                case TYPE_Topic:
                    holder2 = (ViewHolder2) convertView.getTag(R.id.tag_second);
                    break;
            }
        }

        Object o  = data.get(position);
        switch (type) {
            case TYPE_CookBook:
                CookBook cookBook = (CookBook) o;
                holder1.imageView.setImageResource(R.mipmap.ic_launcher);

                holder1.time.setText("cookBookTime:"+cookBook.getTime());

                break;


            case TYPE_Topic:
                Topic topic = (Topic) o;
                KJLoger.debug("topicTime:" + topic.getTime());
                holder2.textView.setText("topicTitle:" + topic.getTitle());
                holder2.time.setText("topicTime:" + topic.getTime());

                break;
        }

//        if(o instanceof CookBook) {
//
//        }else if(o instanceof Topic){
//
//        }

        return convertView;

    }



    public class MyComparator implements Comparator {

        public int compare(Object arg0, Object arg1) {
            if(arg0 instanceof CookBook && arg1 instanceof  Topic ) {

                CookBook cookBook = (CookBook) arg0;
                Topic topic = (Topic) arg1;
                return Integer.valueOf(cookBook.getTime()).compareTo(Integer.valueOf(topic.getTime()));

            }else if( arg0 instanceof  Topic && arg1 instanceof CookBook ){
                Topic topic = (Topic) arg0;
                CookBook cookBook = (CookBook) arg1;

                return Integer.valueOf(topic.getTime()).compareTo(Integer.valueOf(cookBook.getTime()));

            } else if(arg0 instanceof  CookBook && arg1 instanceof CookBook) {

                CookBook cookBook0 = (CookBook) arg0;
                CookBook cookBook1 = (CookBook) arg1;
                return Integer.valueOf(cookBook0.getTime()).compareTo(Integer.valueOf(cookBook1.getTime()));

            }else {
                Topic topic0 = (Topic) arg0;
                Topic topic1 = (Topic) arg1;
                return Integer.valueOf(topic0.getTime()).compareTo(Integer.valueOf(topic1.getTime()));
            }
        }
    }

    private static class ViewHolder1 {
        public TextView time;
        public ImageView imageView;
    }

    private static class ViewHolder2 {
        public TextView time;
        public TextView textView;
    }

}
