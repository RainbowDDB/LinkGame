package com.example.zyt.link;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.GridView;
import java.util.ArrayList;
import java.util.HashMap;


import static android.os.SystemClock.elapsedRealtime;
import static com.example.zyt.link.Game.checkIsItCanBeDestroyed;
import static com.example.zyt.link.Game.checkIsSuccess;
import static com.example.zyt.link.Game.clear;
import static com.example.zyt.link.Game.createPicture;
import static com.example.zyt.link.Game.dataBind;
import static com.example.zyt.link.Game.mix;
import static com.example.zyt.link.Game.positionToPoint;


public class LinkGameHard extends BaseActivity {

    //生成数据源,计时器-->
    private GridView gridView;
    private ArrayList<HashMap<String, Integer>> dataList;
    private Button saveButton;
    private Chronometer timer;
    //定义点击坐标-->
    private int lastClicked;
    private int firstClick = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linkgamehard);
        timer = findViewById(R.id.chronometer_3);
        gridView = findViewById(R.id.gridView_3);
        dataList = new ArrayList<>();
        saveButton = findViewById(R.id.saveButton_3);

        //保存功能未实现，只能返回
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //引入数据源-->
        createPicture(dataList, 8);

        //打乱顺序-->
        mix(dataList);

        //加载数据-->
        dataBind(gridView, dataList);

        timer.setBase(elapsedRealtime());
        /*开始计时*/
        timer.start();

        //加载监听器-->
        gridView.setOnItemClickListener(new adapterOnItemClickListener());

    }

    //外部类方式生成监听事件
    class adapterOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            int secondClick = (dataList.get(position).get("whichPicture"));
            //判断点击是否为空-->
            if (secondClick != R.drawable.blank && lastClicked != position) {
                //点击不为空触发下一个判断，两次点击是否相同-->
                if (firstClick == 0) {
                    firstClick = secondClick;
                } else {
                    //两次点击相同-->
                    if (secondClick == firstClick) {
                        //生成坐标-->
                        Game.Point thisPoint = positionToPoint(position,8);
                        Game.Point lastPoint = positionToPoint(lastClicked,8);
                        //判断能否被删除-->
                        if (checkIsItCanBeDestroyed(thisPoint, lastPoint, dataList,8)) {
                            clear(position, dataList);
                            clear(lastClicked, dataList);
                            dataBind(gridView, dataList);
                            checkIsSuccess(dataList);
                        }
                    }
                    firstClick = 0;
                }
                lastClicked = position;
            }
        }
    }
}





















