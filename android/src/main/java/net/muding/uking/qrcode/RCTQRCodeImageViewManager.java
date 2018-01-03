package net.muding.uking.qrcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

class RCTQRCodeImageView extends ImageView {

    public RCTQRCodeImageView(Context context){
        super(context);
    }
}
public class RCTQRCodeImageViewManager extends SimpleViewManager<RCTQRCodeImageView> {
    private static final String REACT_CLASS = "RCTQRCode";//要与类名一致
    private Context context;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    public RCTQRCodeImageViewManager(Context context) {
        this.context=context;
    }

    @Override
    protected RCTQRCodeImageView createViewInstance(ThemedReactContext reactContext) {

        if (context == null) {
            context = reactContext;
        }
        return new RCTQRCodeImageView(context);
    }
    private void createChineseQRCode(final RCTQRCodeImageView imageView,final String str,final int size,final int forecolor,final int backcolor,final String logo) {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
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
                if(forecolor>0&&backcolor>0 && tempLogo !=null){
                    return QRCodeEncoder.syncEncodeQRCode(str, BGAQRCodeUtil.dp2px(context,size),forecolor,backcolor,tempLogo);
                }else if(forecolor>0&&tempLogo !=null){
                    return QRCodeEncoder.syncEncodeQRCode(str, BGAQRCodeUtil.dp2px(context,size),forecolor,tempLogo);
                }else if(forecolor>0){
                    return QRCodeEncoder.syncEncodeQRCode(str, BGAQRCodeUtil.dp2px(context,size),forecolor);
                }else {
                    return QRCodeEncoder.syncEncodeQRCode(str, BGAQRCodeUtil.dp2px(context,size));
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
    @ReactProp(name = "options")
    public void setData(RCTQRCodeImageView view ,ReadableMap map) {
        String str=map.hasKey("content")?map.getString("content"):"1";
        int size=map.hasKey("size")?map.getInt("size"):100;
        int forecolor=map.hasKey("forecolor")?map.getInt("forecolor"):0;
        int backcolor=map.hasKey("backcolor")?map.getInt("backcolor"):0;
        String logo=map.hasKey("logo")?map.getString("logo"):"";
        createChineseQRCode(view,str,size,forecolor,backcolor,logo);
    }

}