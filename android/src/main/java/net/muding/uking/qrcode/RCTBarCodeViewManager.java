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

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;

/**
 * Created by uking on 2018/1/8.
 */

public class RCTBarCodeViewManager extends SimpleViewManager<ImageView> {
    private static final String TAG = "RCTBarCode";

    private Context context;

    private int barcodeWidth=300;
    private int barcodeHeight=100;
    private String content;
    private boolean displayCode;
    private BarcodeFormat barcodeFormat;
    private int i=0;

    @Override
    public String getName() {
        return TAG;
    }

    public RCTBarCodeViewManager(Context context) {
        this.context=context;
    }

    @Override
    protected ImageView createViewInstance(ThemedReactContext reactContext) {

        if (context == null) {
            context = reactContext;
        }
        return new ImageView(context) ;
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
    private void asyncCreateBarCode(final ImageView imageView, final String text,final BarcodeFormat format, final int width, final int height) {
        new AsyncTask<Void, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                Bitmap bmap;
                try {
                    bmap=encodeAsBitmap(text,format,width,height);
                    return bmap;
                }catch (Exception e){
                    Log.d(TAG,"error:"+e.getMessage());
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    Log.i("IMAGE","false");
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
        return  tv.getDrawingCache();
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
        return BarcodeFormat.valueOf(name);
    }
    @ReactProp(name = "text")
    public void setContext(ImageView view, String text) {
        Log.d(TAG, "setContext " + text);
        content=text;
    }
    @ReactProp(name = "width",defaultInt = 300)
    public void setBarcodeWidth(ImageView view, int width) {
        Log.d(TAG, "setBarcodeWidth " + width);
        barcodeWidth=width;

    }
    @ReactProp(name = "height",defaultInt = 100)
    public void setBarcodeHeight(ImageView view,int height) {
        Log.d(TAG, "setBarcodeHeight " + height);
        barcodeHeight=height;

    }
    @ReactProp(name = "format")
    public void setBarcodeFormat(ImageView view,String format) {
        Log.d(TAG, "setBarcodeFormat " + format);
        barcodeFormat=getBarcodeFormat(format);
    }

    @Override
    protected void onAfterUpdateTransaction(ImageView view) {
        asyncCreateBarCode(view,content,barcodeFormat,barcodeWidth,barcodeHeight);
        super.onAfterUpdateTransaction(view);
    }
}
