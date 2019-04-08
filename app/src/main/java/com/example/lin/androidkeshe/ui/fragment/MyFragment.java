package com.example.lin.androidkeshe.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lin.androidkeshe.R;
import com.example.lin.androidkeshe.entity.MyUser;
import com.example.lin.androidkeshe.json.UserInfoJS;
import com.example.lin.androidkeshe.ui.activity.MyCollectionActivity;
import com.example.lin.androidkeshe.ui.activity.MyCollectionActivity2;
import com.example.lin.androidkeshe.ui.activity.MyLocationActivity;
import com.example.lin.androidkeshe.ui.activity.MySettingActivity;
import com.example.lin.androidkeshe.utils.L;
import com.example.lin.androidkeshe.utils.ShareUtils;
import com.example.lin.androidkeshe.utils.StaticClass;
import com.example.lin.androidkeshe.utils.UtilTools;
import com.example.lin.androidkeshe.view.CustomDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lin on 2018/6/21.
 * 描述:
 */
public class MyFragment extends Fragment implements View.OnClickListener {


    //圆形头像
    private CircleImageView profile_image;

    private TextView myfragment_username_et;
    private TextView myfragment_userphone_et;

    private LinearLayout myfragment_my_location_layout;
    private LinearLayout myfragment_my_collection_layout;
    private LinearLayout myfragment_setting_layout;


    private CustomDialog dialog;

    private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancel;
    private String username;
    private int userid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        username = getActivity().getIntent().getStringExtra("username");
        initView(view);
        return view;
    }

    private void initView(View view) {


        profile_image = (CircleImageView) view.findViewById(R.id.my_img);
        myfragment_username_et = (TextView) view.findViewById(R.id.myfragment_username_et);
        myfragment_userphone_et = (TextView) view.findViewById(R.id.myfragment_userphone_et);
        myfragment_my_location_layout = (LinearLayout) view.findViewById(R.id.myfragment_my_location_layout);
        myfragment_my_collection_layout = (LinearLayout) view.findViewById(R.id.myfragment_my_collection_layout);
        myfragment_setting_layout = (LinearLayout) view.findViewById(R.id.myfragment_setting_layout);


        //设置用户信息
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        Request request = builder.get().url("http://10.130.187.164:8080/userInfo?username="+username).build();
        Call call= client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                    String res  = response.body().string();
                Gson gson = new Gson();
                final UserInfoJS userInfoJS = gson.fromJson(res,new TypeToken<UserInfoJS>(){}.getType());
                if (userInfoJS.getCode() == 502){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ShareUtils.putInt(getContext(),"userid",userInfoJS.getUserid());
                            L.d("55555555555555555"+userInfoJS.getUserid());
                            ShareUtils.putString(getContext(),"username",userInfoJS.getUsername());
                            userid = userInfoJS.getUserid();
                            myfragment_username_et.setText(userInfoJS.getUsername());
                            myfragment_userphone_et.setText(userInfoJS.getPhonenumber());
                        }
                    });
                }else {

                }
            }
        });




        myfragment_my_location_layout.setOnClickListener(this);
        myfragment_my_collection_layout.setOnClickListener(this);
        myfragment_setting_layout.setOnClickListener(this);

        profile_image.setOnClickListener(this);

        UtilTools.getImageToShare(getActivity(), profile_image);

        dialog = new CustomDialog(getActivity(), 100, 100, R.layout.dialog_photo, R.style.Theme_dialog, Gravity.BOTTOM, R.style.pop_anim_style);
        //屏幕外点击无效
        dialog.setCancelable(false);

        btn_camera = (Button) dialog.findViewById(R.id.btn_camera);
        btn_picture = (Button) dialog.findViewById(R.id.btn_picture);
        btn_cancel = (Button) dialog.findViewById(R.id.btn_cancel);
        btn_camera.setOnClickListener(this);
        btn_picture.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_img:
                //点击圆形头像
                dialog.show();
                break;
            case R.id.btn_camera:
                //跳转相机
                toCamera();
                break;
            case R.id.btn_picture:
                //跳转图库
                toPicture();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            case R.id.myfragment_my_location_layout:
                startActivity(new Intent(getContext(), MyLocationActivity.class));
                break;
            case R.id.myfragment_my_collection_layout:
                Intent intent =new Intent(getContext(),MyCollectionActivity2.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                break;
            case R.id.myfragment_setting_layout:
                startActivity(new Intent(getContext(), MySettingActivity.class));
                break;
        }

    }

    private Uri imageUri = null;
    private Uri outputUri;//裁剪完照片保存地址

    private File tempFile = null;


    //跳转相机
    private void toCamera() {
        File outputImage = new File(getContext().getExternalCacheDir(), "output_image.jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT < 24) {
            imageUri = Uri.fromFile(outputImage);
        } else {
            imageUri = FileProvider.getUriForFile(getActivity(), "com.example.lin.internet_plus_game.provider", outputImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, StaticClass.CAMERA_REQUEST_CODE);
        dialog.dismiss();
    }

    //跳转图库
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, StaticClass.IMAGE_REQUEST_CODE);
        dialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != getActivity().RESULT_CANCELED) {
            switch (requestCode) {
                case StaticClass.IMAGE_REQUEST_CODE:
                    //相册的数据
                    startPhotoZoom(data.getData());
                    break;
                case StaticClass.CAMERA_REQUEST_CODE:
                    //相机的数据
//                    tempFile =new File(Environment.getExternalStorageDirectory(),StaticClass.PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(imageUri);
                    break;
                case StaticClass.RESULT_REQUEST_CODE:
                    if (data != null) {
                        //拿到图片设置
                        setImageToView(data);
                        //既然已经设置了图片，我们原先的就应该删除
                        if (tempFile != null) {
                            tempFile.delete();
                        }
                    }
                    break;
            }
        }
    }


    //裁剪图片的方法
    private void startPhotoZoom(Uri uri) {
//          if (uri == null){
//              L.e("uri ==null");
//              return;
//          }
//          L.i("===startPhotoZoom");
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri,"image/*");
//
//        //设置裁剪
//        intent.putExtra("crop","true");
//        //裁剪宽高
//        intent.putExtra("aspectX",1);
//        intent.putExtra("aspectY",1);
//        //裁剪图片的质量
//        intent.putExtra("outputX",320);
//        intent.putExtra("outputY",320);
//
//        //发送数据
//        intent.putExtra("return-data",true);
//        startActivityForResult(intent,StaticClass.RESULT_REQUEST_CODE);
        File file = new File(getContext().getExternalCacheDir(), "crop_image.jpg");
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        outputUri = Uri.fromFile(file);
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        //裁剪图片的宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("crop", "true");//可裁剪
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 200);
        intent.putExtra("scale", true);//支持缩放
        intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());//输出图片格式
        intent.putExtra("noFaceDetection", true);//取消人脸识别
        startActivityForResult(intent, StaticClass.RESULT_REQUEST_CODE);
    }

    //设置头像
    private void setImageToView(Intent data) {
        Bundle bundle = data.getExtras();
        if (bundle != null) {
            Bitmap bitmap = bundle.getParcelable("data");
            profile_image.setImageBitmap(bitmap);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        UtilTools.putImageToShare(getActivity(), profile_image);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //保存
        UtilTools.putImageToShare(getActivity(), profile_image);
        L.i("MyFragment onDestroy执行");
    }


}
