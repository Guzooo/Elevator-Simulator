package pl.guzooo.elevatorsimulator.readme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import pl.guzooo.elevatorsimulator.R;
import pl.guzooo.elevatorsimulator.fullscreen.FullScreenUtils;
import pl.guzooo.elevatorsimulator.utils.ColorUtils;

public class ReadmeActivity extends AppCompatActivity {

    private final String README_URL = "https://raw.githubusercontent.com/Guzooo/Elevator-Simulator/main/README.md";

    SwipeRefreshLayout refreshLayout;
    WebView webView;
    View infoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readme);

        initialization();
        loadInstanceState(savedInstanceState);
        setFullScreen();
        setRefreshLayout();
        setWebView();
        loadReadme();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    private void initialization(){
        refreshLayout = findViewById(R.id.refresh_layout);
        webView = findViewById(R.id.web_view);
    }

    private void loadInstanceState(Bundle savedState){
        if(savedState != null)
            webView.restoreState(savedState);
    }

    private void setFullScreen(){
        View scrollView = findViewById(R.id.scroll);
        FullScreenUtils.setInsets(this, webView, (view, left, top, right, bottom) -> {
            int newProgressViewEndPosition = refreshLayout.getProgressViewEndOffset() + top;
            refreshLayout.setProgressViewEndTarget(false, newProgressViewEndPosition);
            scrollView.setPadding(left, top, right, bottom);
        });
    }

    private void setRefreshLayout(){
        SwipeRefreshLayout.OnRefreshListener refreshListener = getRefreshListener();
        int primaryColor = ColorUtils.getColorFromAttr(R.attr.colorGPrimary, this);
        int accentColor = ColorUtils.getColorFromAttr(R.attr.colorGAccent, this);
        refreshLayout.setOnRefreshListener(refreshListener);
        refreshLayout.setProgressBackgroundColorSchemeColor(primaryColor);
        refreshLayout.setColorSchemeColors(accentColor);
    }

    private void setWebView(){
        WebViewClient webClient = getWebViewClient();
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setWebViewClient(webClient);
    }

    private void loadReadme(){
        getRefreshListener().onRefresh();
    }

    private SwipeRefreshLayout.OnRefreshListener getRefreshListener(){
        return () -> {
            refreshLayout.setRefreshing(true);
            webView.loadUrl(README_URL);
        };
    }

    private WebViewClient getWebViewClient(){
        return new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if(!url.equals(README_URL))
                    getRefreshListener().onRefresh();

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                refreshLayout.setRefreshing(false);
            }
        };
    }
}