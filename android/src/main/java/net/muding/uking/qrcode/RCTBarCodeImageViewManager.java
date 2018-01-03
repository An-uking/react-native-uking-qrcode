package net.muding.uking.qrcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * Created by uking on 2018/1/1.
 */
class RCTBarCodeImageView extends ImageView{
    public RCTBarCodeImageView(Context context){
        super(context);
    }
}
public class RCTBarCodeImageViewManager extends SimpleViewManager<RCTBarCodeImageView> {
    private static final String REACT_CLASS = "RCTBarCode";//要与类名一致
    private Context context;
    public int barcodeWidth=0x0000;
    public int barcodeHeight=0x0000;
    public String content;
    public boolean displayCode;
    public BarcodeFormat barcodeFormat;
    //private Tag TAG="RCTQRCodeImageView";
    //private RCTQRCodeImageView qrcodeview;


    @Override
    public String getName() {
        return REACT_CLASS;
    }

    public RCTBarCodeImageViewManager(Context context) {
        this.context=context;
    }




    @Override
    protected RCTBarCodeImageView createViewInstance(ThemedReactContext reactContext) {

        if (context == null) {
            context = reactContext;
        }
        return new RCTBarCodeImageView(context);
    }
    public static Bitmap creatBarcode(Context context, String contents,BarcodeFormat barcodeFormat,int desiredWidth, int desiredHeight) {
        Bitmap ruseltBitmap = null;
        int marginW = 10;
        //BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;

//        if (displayCode) {
//            Bitmap barcodeBitmap = encodeAsBitmap(contents, barcodeFormat,
//                    desiredWidth, desiredHeight);
//            Bitmap codeBitmap = creatCodeBitmap(contents, desiredWidth, desiredHeight+2*marginW, context);
//            ruseltBitmap = mixtureBitmap(barcodeBitmap, codeBitmap, new PointF(
//                    0, desiredHeight));
//        } else {
//
//        }
        ruseltBitmap = encodeAsBitmap(contents, barcodeFormat,desiredWidth, desiredHeight);

        return ruseltBitmap;
    }


    private static Bitmap encodeAsBitmap(String contents,BarcodeFormat format, int desiredWidth, int desiredHeight) {
        final int WHITE = 0xFFFFFFFF;
        final int BLACK = 0xFF000000;

        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result = null;
        try {
            result = writer.encode(contents, format, desiredWidth,
                    desiredHeight, null);
        } catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        // All are 0, or black, by default
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
    private void asyncCreateBarCode(final RCTBarCodeImageView imageView, final String str,final BarcodeFormat format, final int width, final int height) {
        /*
        这里为了偷懒，就没有处理匿名 AsyncTask 内部类导致 Activity 泄漏的问题
        请开发在使用时自行处理匿名内部类导致Activity内存泄漏的问题，处理方式可参考 https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E6%80%BB%E7%BB%93.md
         */
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                return creatBarcode(context,str,format,width,height);
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

    protected static Bitmap creatCodeBitmap(String contents, int width,int height, Context context) {
        TextView tv = new TextView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv.setLayoutParams(layoutParams);
        tv.setText(contents);
        tv.setHeight(height);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setWidth(width);
        tv.setDrawingCacheEnabled(true);
        tv.setTextColor(Color.BLACK);
        tv.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());

        tv.buildDrawingCache();
        Bitmap bitmapCode = tv.getDrawingCache();
        return bitmapCode;
    }

    private static Bitmap mixtureBitmap(Bitmap first, Bitmap second,PointF fromPoint) {
        if (first == null || second == null || fromPoint == null) {
            return null;
        }
        int marginW = 20;
        Bitmap newBitmap = Bitmap.createBitmap(
                first.getWidth() + second.getWidth() + marginW,
                first.getHeight() + second.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas cv = new Canvas(newBitmap);
        cv.drawBitmap(first, marginW, 0, null);
        cv.drawBitmap(second, fromPoint.x, fromPoint.y, null);
        cv.save(Canvas.ALL_SAVE_FLAG);
        cv.restore();

        return newBitmap;
    }
    private static BarcodeFormat getBarcodeFormat(String name){
        BarcodeFormat format;
        format=BarcodeFormat.valueOf(name);
        if(format==null)
            format=BarcodeFormat.QR_CODE;
        return format;
    }
    @ReactProp(name = "options")
    public void setData(RCTBarCodeImageView imageView, ReadableMap map) {
        this.content=map.getString("code");
        this.barcodeFormat=getBarcodeFormat(map.getString("format"));
        this.barcodeWidth=map.getInt("width");
        this.barcodeHeight=map.getInt("height");
        asyncCreateBarCode(imageView,this.content,this.barcodeFormat,this.barcodeWidth,this.barcodeHeight);
    }
}
