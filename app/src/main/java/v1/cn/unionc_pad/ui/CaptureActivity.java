package v1.cn.unionc_pad.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.zxing.Result;
import com.google.zxing.client.result.ParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerOptions;
import com.mylhyl.zxing.scanner.ScannerView;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import v1.cn.unionc_pad.R;
import v1.cn.unionc_pad.ui.base.BaseActivity;

public class CaptureActivity extends BaseActivity {

    @BindView(R.id.scanner_view)
    ScannerView mScannerView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_back)
    ImageView imBack;

    @OnClick(R.id.img_back)
    void back(){
        finish();
    }


    private boolean lightOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onResume() {
        mScannerView.onResume();
        mScannerView.restartPreviewAfterDelay(1000);
        super.onResume();
    }


    private void init() {
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("登陆");

        mScannerView.setScannerOptions(new ScannerOptions.Builder()
                .setLaserLineColor(R.color.qm_blue)
                .setFrameCornerColor(R.color.qm_blue)
                .setFrameCornerWidth(5)
                .setMediaResId(R.raw.beep).build());
        mScannerView.setOnScannerCompletionListener(new OnScannerCompletionListener() {
            @Override
            public void onScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
                Logger.d(new Gson().toJson(rawResult));
                Logger.d(new Gson().toJson(parsedResult));
                Logger.d(new Gson().toJson(barcode));
                String text = rawResult.getText();
                Log.d("linshi", "rawResult.getText():" + rawResult.getText());
                if (rawResult.getText().contains("weixin.qq.com/")) {
                }
                /**
                 * "医巴士血压二维码"+heartrate+","+lowPresure+","+heartrate+","+savedata.getRate()+","+savedata.getRateName()+","+savedata.getMeasureDate() + " " + savedata.getMeasureTime()
                 */
                else if (rawResult.getText().contains("医巴士血压二维码")) {
                }


                mScannerView.restartPreviewAfterDelay(1000);
            }
//            public void onScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {
//                Logger.d(new Gson().toJson(rawResult));
//                Logger.d(new Gson().toJson(parsedResult));
//                Logger.d(new Gson().toJson(barcode));
//                String text = rawResult.getText();
//                if (rawResult.getText().contains("unionWeb/activity/clinic-activities")) {
//                    //医院二维码
//                    try {
//                        String[] splitText1 = text.split("clinicId=");
//                        Logger.d(Arrays.toString(splitText1));
//                        String clinicId = "";
//                        if (splitText1[splitText1.length - 1].contains("}")) {
//                            String[] splitText2 = splitText1[splitText1.length - 1].split("\\}");
//                            Logger.d(Arrays.toString(splitText2));
//                            clinicId = splitText2[0];
//                        } else if (splitText1[splitText1.length - 1].contains(",")) {
//                            String[] splitText2 = splitText1[splitText1.length - 1].split(",");
//                            Logger.d(Arrays.toString(splitText2));
//                            clinicId = splitText2[0];
//                        } else if (splitText1[splitText1.length - 1].contains("&")) {
//                            String[] splitText2 = splitText1[splitText1.length - 1].split("&");
//                            Logger.d(Arrays.toString(splitText2));
//                            clinicId = splitText2[0];
//                        } else {
//                            clinicId = splitText1[splitText1.length - 1];
//                        }
//                        if (clinicId.contains("\"")) {
//                            clinicId = clinicId.replaceAll("\"", "");
//                        }
//                        clinicActivities(clinicId);
////                        Intent intent = new Intent(context, SignactivityActivity.class);
////                        intent.putExtra("clinicId", clinicId);
////                        startActivityForResult(intent,1);
////                        finish();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                }
////                if (rawResult.getText().contains("unionWeb/scanDoctQRCode.jsp")) {
//                if (rawResult.getText().contains("page/scan.html")) {
//                    //医生二维码
//                    try {
//                        String doctId;
//                        String[] splitText1 = text.split("doctId=");
//                        Logger.d(Arrays.toString(splitText1));
//                        if (splitText1[1].contains("&")) {
//                            String[] splitText2 = splitText1[1].split("&");
//                            doctId = splitText2[0];
//                        } else {
//                            doctId = splitText1[1];
//                        }
//                        if (doctId.contains("\"")) {
//                            doctId = doctId.replaceAll("\"", "");
//                        }
//                        Intent intent = new Intent(context, DoctorDetailActivity.class);
//                        intent.putExtra("doctorId", doctId);
//                        intent.putExtra("source", 1 + "");
//                        startActivityForResult(intent,2);
//                        finish();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                mScannerView.restartPreviewAfterDelay(1000);
//            }
        });
    }


    @Override
    protected void onPause() {
        mScannerView.onPause();
        super.onPause();
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1) {
            finish();
        }
    }





}
