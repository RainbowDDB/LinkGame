package com.example.zyt.link;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class StartActivity extends BaseActivity {
    private static final String TAG = "StartActivity";
    private Button startButton,endButton,continueButton;
    public static StartActivity instance = null;
    int singleSelectedId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        instance = this;
        startButton = (Button) findViewById(R.id.button1);
        endButton = (Button) findViewById(R.id.button2);
        continueButton = (Button) findViewById(R.id.button3);
        //设置开始游戏按钮-->
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //弹出选择难度的对话框-->
                final String item[] = {"简单","中等","困难"};
                new AlertDialog.Builder(StartActivity.this,R.style.MyAlertDialogStyle)
                        .setTitle("请选择难度")
                        .setSingleChoiceItems(item, 1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                singleSelectedId = i;
                                Log.d(TAG,i + "");
                            }
                        })
                        .setSingleChoiceItems(item, 2, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                singleSelectedId = i;
                                Log.d(TAG,i + "");
                            }
                        })
                        .setSingleChoiceItems(item, 3, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                singleSelectedId = i;
                                Log.d(TAG,i + "");
                            }
                        })
                        .setCancelable(false)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //不加任何代码即可取消
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch(singleSelectedId){
                                    case 0:
                                        Intent intentSimple = new Intent(StartActivity.this,LinkGameSimple.class);
                                        startActivity(intentSimple);
                                        break;
                                    case 1:
                                        Intent intentMedium = new Intent(StartActivity.this,LinkGameMedium.class);
                                        startActivity(intentMedium);
                                        break;
                                    case 2:
                                        Intent intentHard = new Intent(StartActivity.this,LinkGameHard.class);
                                        startActivity(intentHard);
                                        break;
                                }
                            }
                        })
                        .show();
            }
        });
        //设置结束游戏按钮-->
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCollector.finishAll();
            }
        });

        //设置继续游戏按钮-->(此按钮被我废掉了）

    }
}



