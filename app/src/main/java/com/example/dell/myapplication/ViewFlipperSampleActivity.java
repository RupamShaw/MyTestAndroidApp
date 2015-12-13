package com.example.dell.myapplication;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.File;

public class ViewFlipperSampleActivity extends Activity {

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private ViewFlipper mViewFlipper;
    private AnimationListener mAnimationListener;
    private Context mContext;

    Handler handler;
    Runnable runnable;
    @SuppressWarnings("deprecation")
    private final GestureDetector detector = new GestureDetector(new SwipeGestureDetector());

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        System.out.println("oncreate" );
        mContext = this;
        mViewFlipper = (ViewFlipper) this.findViewById(R.id.view_flipper);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.color_baloons);
        mViewFlipper.addView(imageView);
        mViewFlipper.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });


        findViewById(R.id.play).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //sets auto flipping
                mViewFlipper.setAutoStart(true);
                mViewFlipper.setFlipInterval(500);
                mViewFlipper.startFlipping();
            }
        });

        findViewById(R.id.stop).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //stop auto flipping
                mViewFlipper.stopFlipping();
            }
        });
        //get all images from local device which by default stores in sdcard/DCIM
        String   photoDir = Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_DCIM + "/.thumbnails/";


        //get the path of the images folder of our sdcard using the following statement.
// getting the path of the images folder in the sdcard
       // File sdcardPath = new File(Environment.getExternalStorageDirectory().getPath() + "/images");
        File sdcardPath = new File(photoDir);
         System.out.println("chk sdcardpath"+sdcardPath);
      //  if (sdcardPath.exists()& (sdcardPath.listFiles()!=null)) {
        if (sdcardPath.exists()) {

            System.out.println("in sdcardaa");
            setImagesToFlipper(mViewFlipper, sdcardPath);
            System.out.println("1");
            runnable = new Runnable() {

                public void run() {
                    System.out.println("2");
                    handler.postDelayed(runnable, 3000);
                    mViewFlipper.showNext();
                    System.out.println("3");
                }
            };
            handler = new Handler();
            handler.postDelayed(runnable, 500);

        }
        else
            Toast.makeText(this, "No folder with name images..", Toast.LENGTH_LONG).show();
        System.out.println("4");
        /* setImagesToFlipper() add ImageViews dynamically to the ViewFlipper.*/

        //animation listener
        mAnimationListener = new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
                //animation started event
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                //TODO animation stopped event
            }
        };



    }

private void setImagesToFlipper(ViewFlipper flipper, File sdcardPath){
    System.out.println("setImagesToFlipper");
    System.out.println("sdcardPath.listFiles()"+sdcardPath.listFiles());
    int imageCount=sdcardPath.listFiles().length;
    System.out.println("setImagesToFlipper imageCount"+imageCount);
        for(int count=0;count<imageCount-1;count++){
            System.out.println("1 imageCount"+imageCount);
        ImageView imageView=new ImageView(this);
            System.out.println("22");
        Bitmap bmp=BitmapFactory.decodeFile(sdcardPath.listFiles()[count]
        .getAbsolutePath());
            System.out.println("333 bmp"+sdcardPath.listFiles()[count]
                    .getAbsolutePath()+"bmpKKKKKK "+bmp);
        imageView.setImageBitmap(bmp);
        flipper.addView(imageView);

        }
        }
    class SwipeGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_in));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.left_out));
                    // controlling animation
                    mViewFlipper.getInAnimation().setAnimationListener(mAnimationListener);
                    mViewFlipper.showNext();
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.right_in));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext,R.anim.right_out));
                    // controlling animation
                    mViewFlipper.getInAnimation().setAnimationListener(mAnimationListener);
                    mViewFlipper.showPrevious();
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    }
}


