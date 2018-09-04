package com.example.weihuagu.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;

import com.example.weihuagu.utils.blurutils.Blur;
import com.example.weihuagu.utils.blurutils.ImageUtils;
import com.example.weihuagu.views.blur.ScrollableImageView;
import com.example.weihuagu.websocket.R;

import java.io.File;

public class BlurActivity extends AppCompatActivity {

    private static final String BLURRED_IMG_PATH = "wugeng.jpg";
    private static final int TOP_HEIGHT = 700;
    private ListView mList;
    private ImageView mBlurredImage;
    private View headerView;
    private ImageView mNormalImage;
    private ScrollableImageView mBlurredImageHeader;
    private Switch mSwitch;
    private float alpha;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur);


        final int screenWidth = ImageUtils.getScreenWidth(this);

        // Find the view
        mBlurredImage = findViewById(R.id.blurred_image);
        mBlurredImageHeader = findViewById(R.id.blurred_image_header);
        mSwitch = findViewById(R.id.background_switch);
        mBlurredImageHeader.setScreenWidth(screenWidth);

        // Action for the switch
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mBlurredImage.setAlpha(alpha);
                } else {
                    mBlurredImage.setAlpha(0f);

                }

            }
        });

        // Try to find the blurred image
        final File blurredImage = new File(getFilesDir() + BLURRED_IMG_PATH);



//        if (!blurredImage.exists()) {


            Log.e("Asfakf",""+blurredImage);

            // launch the progressbar in ActionBar
            setProgressBarIndeterminateVisibility(true);

            new Thread(new Runnable() {

                @Override
                public void run() {

                    // No image found => let's generate it!
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;
                    Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.bg_img_write, options);
                    Bitmap newImg = Blur.fastblur(BlurActivity.this, image, 25);
                    ImageUtils.storeImage(newImg, blurredImage);
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            updateView(screenWidth);

                            // And finally stop the progressbar
                            setProgressBarIndeterminateVisibility(false);
                        }
                    });

                }
            }).start();

//        }
//        else {
//
//            Log.e("Asfakf","blurredImage");
//
//
//            // The image has been found. Let's update the view
//            updateView(screenWidth);
//
//        }




        String[] strings = getResources().getStringArray(R.array.list_content);

        // Prepare the header view for our list
        headerView = new View(this);
        headerView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, TOP_HEIGHT));
//        mList.addHeaderView(headerView);
//        mList.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, strings));
//        mList.setOnScrollListener(new AbsListView.OnScrollListener() {
//
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            /**
//             * Listen to the list scroll. This is where magic happens ;)
//             */
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//                // Calculate the ratio between the scroll amount and the list
//                // header weight to determinate the top picture alpha
//                alpha = (float) -headerView.getTop() / (float) TOP_HEIGHT;
//                // Apply a ceil
//                if (alpha > 1) {
//                    alpha = 1;
//                }
//
//                // Apply on the ImageView if needed
//                if (mSwitch.isChecked()) {
//                    mBlurredImage.setAlpha(alpha);
//                }
//
//                // Parallax effect : we apply half the scroll amount to our
//                // three views
//                mBlurredImage.setTop(headerView.getTop() / 2);
//                mNormalImage.setTop(headerView.getTop() / 2);
//                mBlurredImageHeader.handleScroll(headerView.getTop() / 2);
//
//            }
//        });



        float s = (float)0.99;

        mBlurredImage.setAlpha(s);


    }


    private void updateView(final int screenWidth) {
        Bitmap bmpBlurred = BitmapFactory.decodeFile(getFilesDir() + BLURRED_IMG_PATH);
        bmpBlurred = Bitmap.createScaledBitmap(bmpBlurred, screenWidth, (int) (bmpBlurred.getHeight()
                * ((float) screenWidth) / (float) bmpBlurred.getWidth()), false);



        mBlurredImage.setImageBitmap(bmpBlurred);

        mBlurredImageHeader.setoriginalImage(bmpBlurred);
    }

}
