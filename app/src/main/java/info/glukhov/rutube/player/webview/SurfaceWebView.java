package info.glukhov.rutube.player.webview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import java.util.List;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

public class SurfaceWebView extends WebView {
    private final String URL_BLANK = "about:blank";

    private boolean isVideoFullscreen;

    private EventListener listener;

    private FrameLayout mFullscreenContainer;

    private WebChromeClient mWebChromeClient;

    private Integer ratioWH;

    private String urlToLoad;

    public SurfaceWebView(Context paramContext) {
        this(paramContext, null);
    }

    public SurfaceWebView(Context paramContext, AttributeSet paramAttributeSet) {
        this(paramContext, paramAttributeSet, 0);
    }

    public SurfaceWebView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        setBackgroundColor(-16777216);
    }

    private final void initListeners() {
        setWebViewClient(new SurfaceWebView$initListeners$1());
        SurfaceWebView$initListeners$2 surfaceWebView$initListeners$2 = new SurfaceWebView$initListeners$2();
        this.mWebChromeClient = surfaceWebView$initListeners$2;
        setWebChromeClient(surfaceWebView$initListeners$2);
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private final void initParams() {
        getSettings().setJavaScriptEnabled(true);
        getSettings().setDomStorageEnabled(true);
    }

    private final void startWebView() {
        Log.e("SurfaceWebView", "::startWebView");
        initListeners();
        initParams();
        String str = this.urlToLoad;
        if (str != null) {
            loadUrl(str);
            return;
        }
        stopWebView();
        EventListener eventListener = this.listener;
        //if (eventListener != null)
        //EventListener.DefaultImpls.onWebViewEndedSomehow(eventListener, null, 1, null);
    }

    private final void stopWebView() {
        Log.e("SurfaceWebView", "::stopWebView");
        stopLoading();
        clearCache(true);
        loadUrl(this.URL_BLANK);
    }

    public final void clear() {
        Log.e("SurfaceWebView", "::clear");
        stopWebView();
    }

    public final EventListener getListener() {
        return this.listener;
    }

    public final FrameLayout getMFullscreenContainer() {
        return this.mFullscreenContainer;
    }

    public final Integer getRatioWH() {
        return this.ratioWH;
    }

    protected void onConfigurationChanged(Configuration paramConfiguration) {
        super.onConfigurationChanged(paramConfiguration);
    }

    protected void onMeasure(int paramInt1, int paramInt2) {
        if (this.ratioWH == null) {
            super.onMeasure(paramInt1, paramInt2);
            return;
        }
        paramInt2 = View.MeasureSpec.getSize(paramInt1);
        Integer integer = this.ratioWH;
        if (integer != null) {
            super.onMeasure(paramInt1, View.MeasureSpec.makeMeasureSpec(paramInt2 * integer.intValue(), MeasureSpec.EXACTLY));
            return;
        }
        Intrinsics.throwNpe();
        throw null;
    }

    public final void prepare(String paramString) {
        Intrinsics.checkParameterIsNotNull(paramString, "url");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("::prepare -- ");
        stringBuilder.append(paramString);
        Log.e("SurfaceWebView", stringBuilder.toString());
        this.urlToLoad = paramString;
        startWebView();
    }

    public final void setListener(EventListener paramEventListener) {
        this.listener = paramEventListener;
    }

    public final void setMFullscreenContainer(FrameLayout paramFrameLayout) {
        this.mFullscreenContainer = paramFrameLayout;
    }

    public final void setRatioWH(Integer paramInteger) {
        this.ratioWH = paramInteger;
    }

    @Metadata(d1 = {"\000\030\n\002\030\002\n\002\020\000\n\000\n\002\020\002\n\000\n\002\020\016\n\002\b\002\bf\030\0002\0020\001J\024\020\002\032\0020\0032\n\b\002\020\004\032\004\030\0010\005H&J\b\020\006\032\0020\003H&\006\007"}, d2 = {"Lru/rutube/rutubeplayer/ui/view/SurfaceWebView$EventListener;", "", "onWebViewEndedSomehow", "", "message", "", "onWebViewInitialized", "RutubePlayer_release"}, mv = {1, 1, 15})
    public static interface EventListener {
        void onWebViewEndedSomehow(String param1String);

        void onWebViewInitialized();

        @Metadata(mv = {1, 1, 15})
        public static final class DefaultImpls {
        }
    }

    @Metadata(mv = {1, 1, 15})
    public static final class DefaultImpls {
    }

    @Metadata(d1 = {"\0009\n\000\n\002\030\002\n\000\n\002\020\013\n\000\n\002\030\002\n\000\n\002\020\002\n\000\n\002\030\002\n\000\n\002\020\016\n\002\b\002\n\002\030\002\n\002\b\002\n\002\030\002\n\000*\001\000\b\n\030\0002\0020\001J\020\020\002\032\0020\0032\006\020\004\032\0020\005H\002J\034\020\006\032\0020\0072\b\020\b\032\004\030\0010\t2\b\020\n\032\004\030\0010\013H\026J&\020\f\032\0020\0072\b\020\b\032\004\030\0010\t2\b\020\n\032\004\030\0010\0132\b\020\r\032\004\030\0010\016H\026J\034\020\017\032\0020\0032\b\020\b\032\004\030\0010\t2\b\020\020\032\004\030\0010\021H\027J\034\020\017\032\0020\0032\b\020\b\032\004\030\0010\t2\b\020\n\032\004\030\0010\013H\026J\022\020\017\032\0020\0032\b\020\n\032\004\030\0010\013H\002\006\022"}, d2 = {"ru/rutube/rutubeplayer/ui/view/SurfaceWebView$initListeners$1", "Landroid/webkit/WebViewClient;", "intentAvailable", "", "intent", "Landroid/content/Intent;", "onPageFinished", "", "view", "Landroid/webkit/WebView;", "url", "", "onPageStarted", "favicon", "Landroid/graphics/Bitmap;", "shouldOverrideUrlLoading", "request", "Landroid/webkit/WebResourceRequest;", "RutubePlayer_release"}, mv = {1, 1, 15})
    public final class SurfaceWebView$initListeners$1 extends WebViewClient {
        private final boolean intentAvailable(Intent param1Intent) {
            Context context = SurfaceWebView.this.getContext();
            Intrinsics.checkExpressionValueIsNotNull(context, "context");
            List list = context.getPackageManager().queryIntentActivities(param1Intent, PackageManager.MATCH_DEFAULT_ONLY);
            return (list != null && !list.isEmpty());
        }

        private final boolean shouldOverrideUrlLoading(String param1String) {
            boolean bool;
            String str = SurfaceWebView.this.urlToLoad;
            if (str != null) {
                bool = str.equals(param1String);
            } else {
                bool = false;
            }
            if (bool)
                return false;
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(param1String));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (intentAvailable(intent))
                SurfaceWebView.this.getContext().startActivity(intent);
            return true;
        }

        public void onPageFinished(WebView param1WebView, String param1String) {
            super.onPageFinished(param1WebView, param1String);
            if (!SurfaceWebView.this.URL_BLANK.equals(param1String)) {
                Log.d("SurfaceWebView", "init progress = Page Finished");
                SurfaceWebView.EventListener eventListener = SurfaceWebView.this.getListener();
                if (eventListener != null)
                    eventListener.onWebViewInitialized();
            }
        }

        public void onPageStarted(WebView param1WebView, String param1String, Bitmap param1Bitmap) {
            super.onPageStarted(param1WebView, param1String, param1Bitmap);
        }

        @TargetApi(24)
        public boolean shouldOverrideUrlLoading(WebView param1WebView, WebResourceRequest param1WebResourceRequest) {
            if (param1WebResourceRequest != null) {
                Uri uri = param1WebResourceRequest.getUrl();
                if (uri != null) {
                    String str = uri.toString();
                    return shouldOverrideUrlLoading(str);
                }
            }
            param1WebView = null;
            return shouldOverrideUrlLoading(param1WebView.toString());
        }

        public boolean shouldOverrideUrlLoading(WebView param1WebView, String param1String) {
            return shouldOverrideUrlLoading(param1String);
        }
    }

    @Metadata(d1 = {"\000;\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\030\002\n\000\n\002\020\013\n\002\b\b\n\002\030\002\n\000\n\002\020\002\n\002\b\002\n\002\030\002\n\000\n\002\020\b\n\002\b\003*\001\000\b\n\030\0002\0020\001J\n\020\017\032\004\030\0010\020H\026J\b\020\021\032\0020\022H\026J\030\020\023\032\0020\0222\006\020\024\032\0020\0252\006\020\026\032\0020\027H\026J\034\020\030\032\0020\0222\b\020\024\032\004\030\0010\0032\b\020\031\032\004\030\0010\005H\026R\020\020\002\032\004\030\0010\003X\016\006\002\n\000R\020\020\004\032\004\030\0010\005X\016\006\002\n\000R\032\020\006\032\0020\007X\016\006\016\n\000\032\004\b\b\020\t\"\004\b\n\020\013R\032\020\f\032\0020\007X\016\006\016\n\000\032\004\b\r\020\t\"\004\b\016\020\013\006\032"}, d2 = {"ru/rutube/rutubeplayer/ui/view/SurfaceWebView$initListeners$2", "Landroid/webkit/WebChromeClient;", "mCustomView", "Landroid/view/View;", "mCustomViewCallback", "Landroid/webkit/WebChromeClient$CustomViewCallback;", "noClickFullScreenOnHide", "", "getNoClickFullScreenOnHide", "()Z", "setNoClickFullScreenOnHide", "(Z)V", "noClickFullScreenOnShow", "getNoClickFullScreenOnShow", "setNoClickFullScreenOnShow", "getDefaultVideoPoster", "Landroid/graphics/Bitmap;", "onHideCustomView", "", "onProgressChanged", "view", "Landroid/webkit/WebView;", "newProgress", "", "onShowCustomView", "callback", "RutubePlayer_release"}, mv = {1, 1, 15})
    public final class SurfaceWebView$initListeners$2 extends WebChromeClient {
        private View mCustomView;

        private WebChromeClient.CustomViewCallback mCustomViewCallback;

        public Bitmap getDefaultVideoPoster() {
            if (this.mCustomView == null)
                return null;
            Bitmap bitmap = super.getDefaultVideoPoster();
            return (bitmap != null) ? bitmap : Bitmap.createBitmap(1, 1, Bitmap.Config.RGB_565);
        }

        public void onHideCustomView() {
            // Byte code:
            //   0: new java/lang/StringBuilder
            //   3: dup
            //   4: invokespecial <init> : ()V
            //   7: astore_1
            //   8: aload_1
            //   9: ldc '::onHideCustomView isFullScr='
            //   11: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
            //   14: pop
            //   15: aload_1
            //   16: aload_0
            //   17: getfield this$0 : Lru/rutube/rutubeplayer/ui/view/SurfaceWebView;
            //   20: invokestatic access$isVideoFullscreen$p : (Lru/rutube/rutubeplayer/ui/view/SurfaceWebView;)Z
            //   23: invokevirtual append : (Z)Ljava/lang/StringBuilder;
            //   26: pop
            //   27: ldc 'SurfaceWebView'
            //   29: aload_1
            //   30: invokevirtual toString : ()Ljava/lang/String;
            //   33: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
            //   36: pop
            //   37: aload_0
            //   38: getfield this$0 : Lru/rutube/rutubeplayer/ui/view/SurfaceWebView;
            //   41: invokestatic access$isVideoFullscreen$p : (Lru/rutube/rutubeplayer/ui/view/SurfaceWebView;)Z
            //   44: ifeq -> 170
            //   47: aload_0
            //   48: getfield mCustomView : Landroid/view/View;
            //   51: astore_1
            //   52: aload_1
            //   53: ifnull -> 62
            //   56: aload_1
            //   57: bipush #8
            //   59: invokevirtual setVisibility : (I)V
            //   62: aload_0
            //   63: getfield this$0 : Lru/rutube/rutubeplayer/ui/view/SurfaceWebView;
            //   66: invokevirtual getMFullscreenContainer : ()Landroid/widget/FrameLayout;
            //   69: astore_1
            //   70: aload_1
            //   71: ifnull -> 82
            //   74: aload_1
            //   75: aload_0
            //   76: getfield mCustomView : Landroid/view/View;
            //   79: invokevirtual removeView : (Landroid/view/View;)V
            //   82: aload_0
            //   83: getfield mCustomViewCallback : Landroid/webkit/WebChromeClient$CustomViewCallback;
            //   86: astore_1
            //   87: aload_1
            //   88: ifnull -> 152
            //   91: aload_1
            //   92: ifnull -> 116
            //   95: aload_1
            //   96: invokevirtual getClass : ()Ljava/lang/Class;
            //   99: astore_1
            //   100: aload_1
            //   101: ifnull -> 116
            //   104: aload_1
            //   105: invokevirtual getName : ()Ljava/lang/String;
            //   108: astore_1
            //   109: aload_1
            //   110: ifnull -> 116
            //   113: goto -> 119
            //   116: ldc ''
            //   118: astore_1
            //   119: aload_1
            //   120: ldc '(mCustomViewCallback?.javaClass?.getName() ?: "")'
            //   122: invokestatic checkExpressionValueIsNotNull : (Ljava/lang/Object;Ljava/lang/String;)V
            //   125: aload_1
            //   126: ldc '.chromium.'
            //   128: iconst_0
            //   129: iconst_2
            //   130: aconst_null
            //   131: invokestatic contains$default : (Ljava/lang/CharSequence;Ljava/lang/CharSequence;ZILjava/lang/Object;)Z
            //   134: ifne -> 152
            //   137: aload_0
            //   138: getfield mCustomViewCallback : Landroid/webkit/WebChromeClient$CustomViewCallback;
            //   141: astore_1
            //   142: aload_1
            //   143: ifnull -> 152
            //   146: aload_1
            //   147: invokeinterface onCustomViewHidden : ()V
            //   152: aload_0
            //   153: getfield this$0 : Lru/rutube/rutubeplayer/ui/view/SurfaceWebView;
            //   156: iconst_0
            //   157: invokestatic access$setVideoFullscreen$p : (Lru/rutube/rutubeplayer/ui/view/SurfaceWebView;Z)V
            //   160: aload_0
            //   161: aconst_null
            //   162: putfield mCustomView : Landroid/view/View;
            //   165: aload_0
            //   166: aconst_null
            //   167: putfield mCustomViewCallback : Landroid/webkit/WebChromeClient$CustomViewCallback;
            //   170: return
        }

        public void onProgressChanged(WebView param1WebView, int param1Int) {
            Intrinsics.checkParameterIsNotNull(param1WebView, "view");
        }

        public void onShowCustomView(View param1View, WebChromeClient.CustomViewCallback param1CustomViewCallback) {
            Log.e("SurfaceWebView", "::onShowCustomView");
            if (this.mCustomView != null) {
                if (param1CustomViewCallback != null)
                    param1CustomViewCallback.onCustomViewHidden();
                return;
            }
            this.mCustomView = param1View;
            if (param1View != null)
                param1View.setVisibility(VISIBLE);
            this.mCustomViewCallback = param1CustomViewCallback;
            SurfaceWebView.this.isVideoFullscreen = true;
            FrameLayout frameLayout = SurfaceWebView.this.getMFullscreenContainer();
            if (frameLayout != null)
                frameLayout.addView(param1View);
        }
    }
}