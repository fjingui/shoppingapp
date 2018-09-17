package com.shop.myapplication;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bean.list.Global_Final;
import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;
import org.xutils.x;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddWithDrawAcc extends AppCompatActivity {

    private final static int RESULT_LOAD_IMAGE = 1;
    private final static int RESULT_CAPTURE = 2;
    private final static int RESULT_PICKED_CROP = 3;
    private File capImage;
    private Uri contentUri;
    private Uri cropUri;
    private EditText tx_account;
    private EditText tx_account_name;
    private ImageView upzfbimg;
    private Button upzfbimg_text;
    private Button submitbtn;
    private String cust_acct;
    private String accounttype="支付宝";
    private String fmtdate;
    private String imagepath;
    private final static int UPSTATE=11 ;

    private Handler upServHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what ==11){
                Log.e("zfb","开始上传账号");
                upZfbToServ(Global_Final.withdraw_insert);
            }
        }
    };
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_with_draw_acc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Date dt =	new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        fmtdate = sdf.format(dt);
        upzfbimg = (ImageView) findViewById(R.id.upzfbimg);
        upzfbimg_text = (Button) findViewById(R.id.upzfbimg_text);
        submitbtn = (Button) findViewById(R.id.submitbtn);
        tx_account = (EditText) findViewById(R.id.tx_account);
        tx_account_name = (EditText) findViewById(R.id.tx_account_name);
        cust_acct = getIntent().getStringExtra("cust_acct");
        imagepath=Global_Final.setwithimagepath+fmtdate+"/"+cust_acct+".png";
        upzfbimg_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopUpWin();
            }
        });
        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    upImageToServ(Global_Final.withdraw_saveAcctImage);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void showPopUpWin() {
        View zfbpop = View.inflate(BaseApplication.getContext(), R.layout.zfbpopwin, null);
        Button bt_album = zfbpop.findViewById(R.id.btn_pop_album);
        Button bt_camera = zfbpop.findViewById(R.id.btn_pop_camera);
        Button bt_cancle = zfbpop.findViewById(R.id.btn_pop_cancel);
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        int heightp = getResources().getDisplayMetrics().heightPixels / 3;
        final PopupWindow pwin = new PopupWindow(zfbpop, widthPixels, heightp);
        pwin.setFocusable(true);
        pwin.setOutsideTouchable(true);
        bt_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent album = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(album, RESULT_LOAD_IMAGE);
                pwin.dismiss();
            }
        });
        bt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent capture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                capImage = createImageFile();
                if (capture.resolveActivity(BaseApplication.getContext().getPackageManager()) != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //临时添加一个拍照权限
                        capture.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        //通过FileProvider获取uri
                        contentUri = FileProvider.getUriForFile(AddWithDrawAcc.this, "com.shop.myapplication.fileProvider", capImage);
                        capture.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
                    } else {
                        capture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(capImage));
                    }
                }
                if (ContextCompat.checkSelfPermission(AddWithDrawAcc.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // 进入这儿表示没有权限
                    if (ActivityCompat.shouldShowRequestPermissionRationale(AddWithDrawAcc.this, Manifest.permission.CAMERA)) {
                        // 提示已经禁止
                        Toast.makeText(AddWithDrawAcc.this, "you_have_cut_down_the_permission", Toast.LENGTH_LONG);
                    } else {
                        ActivityCompat.requestPermissions(AddWithDrawAcc.this, new String[]{Manifest.permission.CAMERA}, 100);
                    }
                }else{
                    startActivityForResult(capture, RESULT_CAPTURE);
                }

                pwin.dismiss();
            }
        });
        bt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pwin.dismiss();
            }
        });
        pwin.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        pwin.showAtLocation(upzfbimg_text, Gravity.BOTTOM, 0, 10);
    }

    public File createImageFile() {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            cachePath = this.getExternalCacheDir().getPath();
        } else {
            cachePath = this.getCacheDir().getPath();
        }
        File dir = new File(cachePath);
        File image = null;
        String filename = generateFileName();
        image = new File(dir, filename + ".jpg");
        if (image.exists()) {
            image.delete();
        }
        try {
            image.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public static String generateFileName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "ZFB_" + timeStamp;
        return imageFileName;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_LOAD_IMAGE && null != data) {
                Uri selectedimage = data.getData();
                startPhotoZoom(selectedimage);
            } else if (requestCode == RESULT_CAPTURE) {
                Uri capture;
                //如果是7.0android系统，直接获取uri
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    capture = contentUri;
                } else {
                    capture = Uri.fromFile(capImage);
                }
                startPhotoZoom(capture);
            } else if (requestCode == RESULT_PICKED_CROP) {
                Picasso.with(BaseApplication.getContext()).load(cropUri).into(upzfbimg);
            }
        }
    }

    public void startPhotoZoom(Uri uri) {
        File uritempFile = createImageFile();
        Intent crop = new Intent("com.android.camera.action.CROP");
        crop.setDataAndType(uri, "image/*");
        crop.putExtra("aspectX", 1);
        crop.putExtra("aspectY", 1);
        crop.putExtra("outputX", 200);
        crop.putExtra("outputY", 200);
        crop.putExtra("scale", true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //开启临时权限
            crop.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            crop.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //重点:针对7.0以上的操作
            //crop.setClipData(ClipData.newRawUri(MediaStore.EXTRA_OUTPUT, uri));
            cropUri = uri;
        } else {
            cropUri = Uri.fromFile(uritempFile);
        }
        crop.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        crop.putExtra("return-data", false);
        crop.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        crop.putExtra("noFaceDetection", true); // no face detection
        crop = Intent.createChooser(crop, "裁剪图片");
        startActivityForResult(crop, RESULT_PICKED_CROP);
    }

    //android Uri转file:
    private File getUriFile(Uri uri) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        String path = Environment.getExternalStorageDirectory().getPath();
//获取外部储存目录
        File upfiledir = new File(path);
        //以当前时间重新命名文件
        long i = System.currentTimeMillis();
        //生成新的文件
        File upfile = new File(upfiledir.getAbsolutePath() + "/" + i + ".png");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    100);
        }else{
            if(!upfiledir.exists()) {
                //创建新目录
                upfiledir.mkdirs();
            }
            OutputStream out = new FileOutputStream(upfile.getAbsolutePath());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        }
        return upfile;
    }

    public void upImageToServ(String url) throws URISyntaxException, IOException {
        RequestParams params = new RequestParams(url);
        params.setMultipart(true); //以表单方式上传
        // upfileimage = getUriFile(cropUri);
        params.addBodyParameter("image",getUriFile(cropUri));
        params.addQueryStringParameter("cust_acct",cust_acct);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                upServHandler.sendEmptyMessage(UPSTATE);
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }
        });
    }

    public void upZfbToServ(String url) {
        RequestParams params = new RequestParams(url);
        params.addQueryStringParameter("cust_acct", cust_acct);
        params.addQueryStringParameter("tx_account_name", tx_account_name.getText().toString());
        params.addQueryStringParameter("tx_account", tx_account.getText().toString());
        params.addQueryStringParameter("imagepath",imagepath);
        params.addQueryStringParameter("account_type",accounttype);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(AddWithDrawAcc.this, "上传成功！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }
        });
    }
}
