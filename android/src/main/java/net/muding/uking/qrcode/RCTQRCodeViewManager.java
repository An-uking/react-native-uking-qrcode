package net.muding.uking.qrcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.annotation.Nullable;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;


public class RCTQRCodeViewManager extends SimpleViewManager<ImageView> {
    private static final String TAG = "RCTQRCode";//要与类名一致
    private Context context;

    private int qrcodeSize=100;
    private String content;
    private int foreColor=0xFF000000;
    private int backColor=0xFFFFFFFF;
    private String logo;

    @Override
    public String getName() {
        return TAG;
    }

    public RCTQRCodeViewManager(Context context) {
        this.context=context;
    }

    @Override
    protected ImageView createViewInstance(ThemedReactContext reactContext) {

        if (context == null) {
            context = reactContext;
        }
        return new ImageView(context);
    }
    private void asyncCreateQRCode(final ImageView imageView,final String text,final int size,final int forecolor,final int backcolor,final String logo) {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                try {
                    Bitmap tempLogo=null;
                    if(logo!=""){
                        try {
                            FileInputStream fis = new FileInputStream(logo);
                            tempLogo= BitmapFactory.decodeStream(fis);
                        }catch (FileNotFoundException e)
                        {
                            tempLogo=null;
                        }
                    }
                    return QRCodeEncoder.syncEncodeQRCode(text, BGAQRCodeUtil.dp2px(context,size),forecolor,backcolor,tempLogo);

                }catch (Exception e){
                    Log.d("RCTQRCode",e.getMessage());
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    Log.i("IMAGE","false");
                    //Toast.makeText(context, "生成中文二维码失败", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }
    @ReactProp(name = "text")
    public void setContext(ImageView view,String text) {
        Log.d(TAG, "setContext " + text);
        content=text;
    }
    @ReactProp(name = "size",defaultInt = 100)
    public void setQrcodeSize(ImageView view,int size) {
        Log.d(TAG, "setQrcodeSize " + size);
        qrcodeSize=size;

    }
    @ReactProp(name = "forecolor",defaultInt = Color.BLACK, customType = "Color")
    public void setForeColor(ImageView view,int forecolor) {
        Log.d(TAG, "setForeColor " + forecolor);
        foreColor=forecolor;

    }
    @ReactProp(name = "backcolor",defaultInt = Color.WHITE, customType = "Color")
    public void setBackColor(ImageView view,int backcolor) {
        Log.d(TAG, "setBackColor " + backcolor);
        backColor=backcolor;
        //this.setBackgroundColor();
    }
    @ReactProp(name = "logo")
    public void setLogo(ImageView view,String path) {
        Log.d(TAG, "setLogo " + path);
        logo=path;
    }

    @Override
    protected void onAfterUpdateTransaction(ImageView view) {
        asyncCreateQRCode(view,content,qrcodeSize,foreColor,backColor,logo);
        super.onAfterUpdateTransaction(view);
    }
}