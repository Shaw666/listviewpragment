package com.example.scq.test1;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by scq on 2017/7/12.
 */

public class HeadlinesFragment extends ListFragment {
    //定义内部接口引用
    OnHeadlineSelectedListener mCallback;
    private String TAG = "HeadlinesFragment";
    
    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnHeadlineSelectedListener {
        /** Called by HeadlinesFragment when a list item is selected，当点击list item时回调，携带点击位置信息
         */
        
        public void onArticleSelected(int position);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: in");
        
        // We need to use a different list item layout for devices older than Honeycomb
        int layout = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ?
                android.R.layout.simple_list_item_activated_1 : android.R.layout.simple_list_item_1;
        //if true 新api版本   false旧的
        // Create an array adapter for the list view, using the Ipsum headlines array 创建一个适配器ArrayAdapter<String>显示listview内容
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, Ipsum.Headlines));
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: in");
        // When in two-pane layout, set the listview to highlight the selected list item
        // (We do this during onStart because at the point the listview is available.)
        //根据id获取Fragment，需要在布局文件中使用Fragment标签加载Fragment
        if (getFragmentManager().findFragmentById(R.id.article) != null) {
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.i(TAG, "onAttach: in");
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception.确保依附的activity实现接口
        try {
            mCallback = (OnHeadlineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Notify the parent activity of selected item
        //通知activity选中的item
        Log.i(TAG, "onListItemClick: in");
        mCallback.onArticleSelected(position);

        // Set the item as checked to be highlighted when in two-pane layout,设为高亮
        getListView().setItemChecked(position, true);
    }
}
