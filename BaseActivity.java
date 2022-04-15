package com.example.offline;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private ForceOffLineReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("come.example.yt.broadcastbestpractice.FORCE_OFFLINE");
        receiver=new ForceOffLineReceiver();
        registerReceiver(receiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(receiver!=null){
            unregisterReceiver(receiver);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    class ForceOffLineReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(final Context context, Intent intent) {
            AlertDialog.Builder builder=new AlertDialog.Builder(context);//使用AlertDialog.Builder方法构建对话框。
            builder.setTitle("Warnning");//设置对话框的标题
            builder.setMessage("You are forced to be offLine.Please try to login again.");//对话框的内容
            builder.setCancelable(false);//将对话框设置为不可取消
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
//为对话框 设置 肯定按钮，并构建点击事件，销毁当前全部活动并从新跳转到登陆界面
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ActivityCollector.FinishAll();//销毁全部活动
                    Intent o=new Intent(context,LoginActivity.class);
                    context.startActivity(o);//从新启动LoginActivity;
                }
            });
            builder.show();
        }
    }
}