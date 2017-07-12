package com.example.scq.test1;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;


//实现需要通信Fragment的内部接口
public class MainActivity extends FragmentActivity
        implements HeadlinesFragment.OnHeadlineSelectedListener {
    private String TAG = "mianactivity";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_articles);
        Log.i(TAG, "onCreate: in");
        // Check whether the activity is using the layout version with
        // the fragment_container FrameLayout.
        // If so, we must add the first fragment
        // 如果不是xml加载需要使用Fragment事务加载Fragment
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            // Create an instance of ExampleFragment
            HeadlinesFragment firstFragment = new HeadlinesFragment();

            // In case this activity was started with special instructions from an Intent,
            // pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout添加Fragment到activity的主要方法
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    public void onArticleSelected(int position) {
        // The user selected the headline of an article from the HeadlinesFragment
        Log.i(TAG, "onArticleSelected: in");
        // Capture the article fragment from the activity layout
        ArticleFragment articleFrag = (ArticleFragment)
                getSupportFragmentManager().findFragmentById(R.id.article);

        if (articleFrag != null) {
            // If article frag is available, we're in two-pane layout...
            Log.i(TAG, "articleFragment: Youxiao");
            // Call a method in the ArticleFragment to update its content
            articleFrag.updateArticleView(position);

        } else {
            // If the frag is not available, we're in the one-pane layout and must swap frags...
            Log.i(TAG, "articleFragment: wuxiao");
            // Create fragment and give it an argument for the selected article
            ArticleFragment newFragment = new ArticleFragment();
            //通过bundle携带数据，传递数据给articleFragment，Fragment之间的通信方式
            Bundle args = new Bundle();
            args.putInt(ArticleFragment.ARG_POSITION, position);
            newFragment.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back 添加返回栈
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction提交事务
            transaction.commit();
        }
    }
}
