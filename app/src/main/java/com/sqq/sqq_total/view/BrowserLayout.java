package com.sqq.sqq_total.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sqq.sqq_total.R;
import com.sqq.sqq_total.utils.TranslateUtils;

/**
 * Created by Administrator on 2016/6/7.
 */
public class BrowserLayout extends FrameLayout {

    Context con;
    private WebView mWebView = null;
    private ProgressBar mProgressBar = null;
    private int mProgressHeight = 5;
    private String mLoadUrl;
    View vg;
    ImageView titleImg;
    TextView titleTv;
    OnImgClickCallback callback;

    public BrowserLayout(Context context) {
        this(context, null);
    }

    public BrowserLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public BrowserLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void initView(Context context){
        con = context;
        vg = LayoutInflater.from(context).inflate(R.layout.webview_new, this, true);
        titleImg = (ImageView) vg.findViewById(R.id.web_back_img);
        titleImg.setClickable(true);
        titleImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback!=null){
                    callback.OnImgClick();
                }
            }
        });
        titleTv  = (TextView) vg.findViewById(R.id.web_back_tv);

        //addView(vg, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        //mProgressBar = (ProgressBar) LayoutInflater.from(context).inflate(R.layout.progress_horizontal, null);
        mProgressBar = (ProgressBar) vg.findViewById(R.id.my_profile_progress);
        mProgressBar.setProgress(0);
        //addView(mProgressBar, LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, mProgressHeight, getResources().getDisplayMetrics()));

        mWebView = (WebView) vg.findViewById(R.id.webview_fr);

        /*WebView webView = new WebView(context);
        webView.setScrollContainer(false);
        webView.setScrollbarFadingEnabled(false);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        WebSettings settings = webView.getSettings();
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        settings.setBuiltInZoomControls(false);
        // 设置显示缩放按钮
        settings.setSupportZoom(false);
        // 支持缩放
        StringBuffer str = new StringBuffer();
        str.append("<html$amp;>amp;$lt;meta http-equiv=\"Content-Type\"
        content=\"text/html; charset=utf-8\" /$amp;>amp;
        $lt;body$amp;>amp;$lt;p style=\"word-break:break-all; padding:12px;\"$amp;>amp;$quot;) .append("Hello World
        !") .append(" </p$amp;>amp;
        $lt;/body$amp;>amp;
        $lt;/html$amp;>amp;
        $quot;);
        webView.loadDataWithBaseURL("", str.toString(), "text/html", "UTF-8", "");*/

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setSupportMultipleWindows(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(false);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setLoadsImagesAutomatically(true);

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mLoadUrl = url;
                /**
                 * 利用js解决padding设置没有用的问题
                 */
                /*mWebView.loadUrl("javascript:document.body.style.paddingTop" +
                        "=\"12%\";void 0");*/
                mWebView.loadUrl("javascript:document.body.style.paddingTop" +
                        "=\""+(getResources().getDimensionPixelSize(R.dimen.title_height)+2)+"px\";void 0");
            }
        });
    }

    public void setTitleText(String title){
        titleTv.setText(title);
    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    public boolean canGoBack() {
        return null != mWebView ? mWebView.canGoBack() : false;
    }

    public boolean canGoForward() {
        return null != mWebView ? mWebView.canGoForward() : false;
    }

    public void goBack() {
        if (null != mWebView) {
            mWebView.goBack();
        }
    }

    public void goForward() {
        if (null != mWebView) {
            mWebView.goForward();
        }
    }

    public WebView getWebView() {
        return mWebView != null ? mWebView : null;
    }

    public void setOnImgClickListener(OnImgClickCallback imgcallback){
        callback = imgcallback;
    }

    public interface OnImgClickCallback{
        public void OnImgClick();
    }
}
