package v1.cn.demo.media;

import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;

import java.lang.ref.WeakReference;

/**
 * Created by netease on 17/4/6.
 */
public final class NEMeasureHelper {
    private WeakReference<View> mWeakView;

    private int mVideoWidth;
    private int mVideoHeight;
    private int mVideoSarNum;
    private int mVideoSarDen;

    private int mVideoRotationDegree;

    private int mMeasuredWidth;
    private int mMeasuredHeight;

    //    private int mCurrentAspectRatio = NERenderView.AR_16_9_FIT_PARENT;
    private int mCurrentAspectRatio = NEVideoView.VIDEO_SCALING_MODE_FULL;

    public NEMeasureHelper(View view) {
        mWeakView = new WeakReference<View>(view);
    }

    public View getView() {
        if (mWeakView == null)
            return null;
        return mWeakView.get();
    }

    public void setVideoSize(int videoWidth, int videoHeight) {
        mVideoWidth = videoWidth;
        mVideoHeight = videoHeight;
    }

    public void setVideoSampleAspectRatio(int videoSarNum, int videoSarDen) {
        mVideoSarNum = videoSarNum;
        mVideoSarDen = videoSarDen;
    }

    public void setVideoRotation(int videoRotationDegree) {
        mVideoRotationDegree = videoRotationDegree;
    }

    /**
     * Must be called by View.onMeasure(int, int)
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    public void doMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Log.i("@@@@", "onMeasure(" + MeasureSpec.toString(widthMeasureSpec) + ", "
        //        + MeasureSpec.toString(heightMeasureSpec) + ")");
        Log.i("MeasureHelper", "widthMeasureSpec = " + MeasureSpec.toString(widthMeasureSpec) + ", heightMeasureSpec = " + MeasureSpec.toString(heightMeasureSpec));
        if (mVideoRotationDegree == 90 || mVideoRotationDegree == 270) {
            int tempSpec = widthMeasureSpec;
            widthMeasureSpec  = heightMeasureSpec;
            heightMeasureSpec = tempSpec;
        }

        Log.i("MeasureHelper", "mCurrentAspectRatio = " + mCurrentAspectRatio);
        int width = View.getDefaultSize(mVideoWidth, widthMeasureSpec);
        int height = View.getDefaultSize(mVideoHeight, heightMeasureSpec);
        Log.i("MeasureHelper", "width = " + width + ", height = " + height);
        if (mCurrentAspectRatio == NEVideoView.VIDEO_SCALING_MODE_FILL) {
            width = widthMeasureSpec;
            height = heightMeasureSpec;
        } else if (mVideoWidth > 0 && mVideoHeight > 0) {
            int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
            int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
            int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
            int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

            if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
                float specAspectRatio = (float) widthSpecSize / (float) heightSpecSize;
                float displayAspectRatio;
                switch (mCurrentAspectRatio) {
//                    case NERenderView.AR_16_9_FIT_PARENT:
//                        displayAspectRatio = 16.0f / 9.0f;
//                        if (mVideoRotationDegree == 90 || mVideoRotationDegree == 270)
//                            displayAspectRatio = 1.0f / displayAspectRatio;
//                        break;
//                    case NERenderView.AR_4_3_FIT_PARENT:
//                        displayAspectRatio = 4.0f / 3.0f;
//                        if (mVideoRotationDegree == 90 || mVideoRotationDegree == 270)
//                            displayAspectRatio = 1.0f / displayAspectRatio;
//                        break;
//                    case NERenderView.AR_ASPECT_FIT_PARENT:
//                    case NERenderView.AR_ASPECT_FILL_PARENT:
//                    case NERenderView.AR_ASPECT_WRAP_CONTENT:
                    case NEVideoView.VIDEO_SCALING_MODE_NONE:
                    case NEVideoView.VIDEO_SCALING_MODE_FIT:
                    case NEVideoView.VIDEO_SCALING_MODE_FULL:
                    default:
                        displayAspectRatio = (float) mVideoWidth / (float) mVideoHeight;
                        if (mVideoSarNum > 0 && mVideoSarDen > 0)
                            displayAspectRatio = displayAspectRatio * mVideoSarNum / mVideoSarDen;
                        break;
                }
                boolean shouldBeWider = displayAspectRatio > specAspectRatio;

                switch (mCurrentAspectRatio) {
//                    case NERenderView.AR_ASPECT_FIT_PARENT:
//                    case NERenderView.AR_16_9_FIT_PARENT:
//                    case NERenderView.AR_4_3_FIT_PARENT:
                    case NEVideoView.VIDEO_SCALING_MODE_FIT:
                        if (shouldBeWider) {
                            // too wide, fix width
                            width = widthSpecSize;
                            height = (int) (width / displayAspectRatio);
                        } else {
                            // too high, fix height
                            height = heightSpecSize;
                            width = (int) (height * displayAspectRatio);
                        }
                        break;
//                    case NERenderView.AR_ASPECT_FILL_PARENT:
                    case NEVideoView.VIDEO_SCALING_MODE_FULL:
                        if (shouldBeWider) {
                            // not high enough, fix height
                            height = heightSpecSize;
                            width = (int) (height * displayAspectRatio);
                        } else {
                            // not wide enough, fix width
                            width = widthSpecSize;
                            height = (int) (width / displayAspectRatio);
                        }
                        break;
//                    case NERenderView.AR_ASPECT_WRAP_CONTENT:
                    case NEVideoView.VIDEO_SCALING_MODE_NONE:
                    default:
                        if (shouldBeWider) {
                            // too wide, fix width
                            width = Math.min(mVideoWidth, widthSpecSize);
                            height = (int) (width / displayAspectRatio);
                        } else {
                            // too high, fix height
                            height = Math.min(mVideoHeight, heightSpecSize);
                            width = (int) (height * displayAspectRatio);
                        }
                        break;
                }
            }
//            else if (widthSpecMode == View.MeasureSpec.EXACTLY && heightSpecMode == View.MeasureSpec.EXACTLY) {
//                // the size is fixed
//                width = widthSpecSize;
//                height = heightSpecSize;
//
//                // for compatibility, we adjust size based on aspect ratio
//                if (mVideoWidth * height < width * mVideoHeight) {
//                    //Log.i("@@@", "image too wide, correcting");
//                    width = height * mVideoWidth / mVideoHeight;
//                } else if (mVideoWidth * height > width * mVideoHeight) {
//                    //Log.i("@@@", "image too tall, correcting");
//                    height = width * mVideoHeight / mVideoWidth;
//                }
//            } else if (widthSpecMode == View.MeasureSpec.EXACTLY) {
//                // only the width is fixed, adjust the height to match aspect ratio if possible
//                width = widthSpecSize;
//                height = width * mVideoHeight / mVideoWidth;
//                if (heightSpecMode == View.MeasureSpec.AT_MOST && height > heightSpecSize) {
//                    // couldn't match aspect ratio within the constraints
//                    height = heightSpecSize;
//                }
//            } else if (heightSpecMode == View.MeasureSpec.EXACTLY) {
//                // only the height is fixed, adjust the width to match aspect ratio if possible
//                height = heightSpecSize;
//                width = height * mVideoWidth / mVideoHeight;
//                if (widthSpecMode == View.MeasureSpec.AT_MOST && width > widthSpecSize) {
//                    // couldn't match aspect ratio within the constraints
//                    width = widthSpecSize;
//                }
//            } else {
//                // neither the width nor the height are fixed, try to use actual video size
//                width = mVideoWidth;
//                height = mVideoHeight;
//                if (heightSpecMode == View.MeasureSpec.AT_MOST && height > heightSpecSize) {
//                    // too tall, decrease both width and height
//                    height = heightSpecSize;
//                    width = height * mVideoWidth / mVideoHeight;
//                }
//                if (widthSpecMode == View.MeasureSpec.AT_MOST && width > widthSpecSize) {
//                    // too wide, decrease both width and height
//                    width = widthSpecSize;
//                    height = width * mVideoHeight / mVideoWidth;
//                }
//            }
//        } else {
//            // no size yet, just adopt the given spec sizes
        }

        mMeasuredWidth = width;
        mMeasuredHeight = height;
    }

    public int getMeasuredWidth() {
        return mMeasuredWidth;
    }

    public int getMeasuredHeight() {
        return mMeasuredHeight;
    }

    public void setAspectRatio(int aspectRatio) {
        mCurrentAspectRatio = aspectRatio;
    }
}
