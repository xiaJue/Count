package com.xiajue.count.count.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.xiajue.count.count.R;

import static com.xiajue.count.count.constant.ConstString.GIT_ABOUT_FILE_ADDRESS;

/**
 * Created by xiaJue on 2018/1/3.
 */
public class AboutActivity extends BaseActivity {
    private WebView mWebView;
    private ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        bindViews();
        set();
    }

    private void bindViews() {
        mWebView = (WebView) findViewById(R.id.about_webView);
        mProgressBar = (ProgressBar) findViewById(R.id.about_progress);
    }

    private void set() {
        mWebView.loadUrl(GIT_ABOUT_FILE_ADDRESS);
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);//支持javaScript
        //设置 缓存模式
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCacheEnabled(true);
        //提高渲染的优先级
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //更新进度条
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (i == 100) {
                    mProgressBar.setVisibility(View.GONE);
                    mWebView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about_refresh:
                mWebView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                mWebView.reload();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
