package com.example.zyt.link;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

/**
 * 游戏源代码,强行封装
 * 引用时控件代号：1为简单，2为中等，3为困难
 * n为几行几列，设置参数方便调用
 * 将datalist参数导入函数，以便调用函数内部的datalist
 */

public class Game {
    //public static ArrayList<HashMap<String, Integer>> dataList;

    //引入一个数字图片类-->
    public static class Number {
        static final int one = 1;
        static final int two = 2;
        static final int three = 3;
        static final int four = 4;
        static final int five = 5;
        static final int six = 6;
        static final int seven = 7;
        static final int eight = 8;
    }
    //定义一个坐标类-->
    public static class Point{
        int x;
        int y;
        Point(int px,int py){
            x=px;
            y=py;
        }
        Point(Point p){
            x=p.x;
            y=p.y;
        }
    }
    //这个用来判断三条直线链接的时候用到
    public static class Line{
        Point a,b;
        int direct;//1表示竖线，0表示横线
        Line(int dir, Point a, Point b) {
            this.a=a;
            this.b=b;
            this.direct=dir;
        }
    }

    //定义数据源方法-->

    public static void createPicture(ArrayList<HashMap<String, Integer>> dataList,int n) {
        for(int i = 1;i <= n;i++){
            HashMap<String,Integer> hMap = new HashMap<>();
            switch (i) {
                case Number.one:
                    hMap.put("whichPicture", R.drawable.pic_one);
                    break;
                case Number.two:
                    hMap.put("whichPicture", R.drawable.pic_two);
                    break;
                case Number.three:
                    hMap.put("whichPicture", R.drawable.pic_three);
                    break;
                case Number.four:
                    hMap.put("whichPicture", R.drawable.pic_four);
                    break;
                case Number.five:
                    hMap.put("whichPicture", R.drawable.pic_five);
                    break;
                case Number.six:
                    hMap.put("whichPicture", R.drawable.pic_six);
                    break;
                case Number.seven:
                    hMap.put("whichPicture", R.drawable.pic_seven);
                    break;
                case Number.eight:
                    hMap.put("whichPicture", R.drawable.pic_eight);
                    break;
            }
            for(int k = 1;k <= n;k++){
                dataList.add(hMap);
            }
        }
    }
    //定义随机数表的方法-->
    public static void mix(ArrayList<HashMap<String,Integer>> dataList) {
        for (int i = 0; i < 200; i++) {
            int rd = (int) (Math.random() * dataList.size());
            HashMap<String, Integer> tMap = dataList.get(rd);
            dataList.remove(rd);
            dataList.add(tMap);
        }
    }
    //定义加载数据的方法-->
    public static void dataBind(GridView gridView, ArrayList<HashMap<String, Integer>> dataList) {
        SimpleAdapter adapter=new SimpleAdapter(ActivityCollector.getActivity(1),dataList
                ,R.layout.item, new String[]{"whichPicture"}, new int[]{R.id.imageView});
        gridView.setAdapter(adapter);
    }
    //成功完成游戏的判断，并打开成功完成的界面-->
    public static void checkIsSuccess(ArrayList<HashMap<String, Integer>> dataList) {
        for(HashMap<String,Integer>map: dataList){
            if (map.get("whichPicture")!=R.drawable.blank) {
                return;
            }
        }
        /*弹出对话框*/
        new AlertDialog.Builder(ActivityCollector.getActivity(1))
                .setTitle("恭喜你！")
                .setMessage("你已经成功完成游戏")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //销毁此活动
                        ActivityCollector.getActivity(1).finish();
                    }
                })
                .setCancelable(false)
                .show();
    }
    //能否消除判断-->
    public static boolean checkIsItCanBeDestroyed(Point p1, Point p2,ArrayList<HashMap<String, Integer>> dataList,int n) {
        /*判断一条线是否能连接*/
        if (testVertical(new Point(p1), new Point(p2),dataList,n)) {
            return true;
        }
        if (testHorizontal(new Point(p1), new Point(p2),dataList,n)) {
            return true;
        }
        /*判断两条线是否能连接*/
        Point newPoint1 = new Point(p2.x, p1.y);/*交换坐标保证np1与p1,p2有相同的横纵坐标*/
        int position1 = pointToPosition(newPoint1,n);
        if (dataList.get(position1).get("whichPicture") == R.drawable.blank) {
            if (testVertical(p2, new Point(newPoint1),dataList,n) && testHorizontal(p1, new Point(newPoint1),dataList,n)) {
                return true;
            }
        }
        Point newPoint2 = new Point(p1.x, p2.y);/*交换坐标保证np2与p1,p2有相同的横纵坐标*/
        position1 = pointToPosition(newPoint2,n);
        if (dataList.get(position1).get("whichPicture") == R.drawable.blank) {
            if (testVertical(p1, new Point(newPoint2),dataList,n) && testHorizontal(p2, new Point(newPoint2),dataList,n)) {
                return true;
            }
        }
        /*判断三条线是否能连接*/
        Vector<Line> vector;
        vector = scan(new Point(p1), new Point(p2),dataList,n);
        if (!vector.isEmpty()) {
            for (int i = 0; i < vector.size(); i++) {
                Line line=vector.elementAt(i);
                //横线
                if (line.direct == 0) {
                    if (testVertical(new Point(p1), new Point(line.a),dataList,n) && testVertical(new Point(p2), new Point(line.b),dataList,n)) {
                        return true;
                    }
                }
                //竖线
                else {
                    if (testHorizontal(new Point(p1), new Point(line.a),dataList,n) && testHorizontal(new Point(p2), new Point(line.b),dataList,n)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    //垂直消除判断方法-->
    public static boolean testVertical(Point p1, Point p2,ArrayList<HashMap<String, Integer>> dataList,int n){
        boolean b=true;
        if (p1.x==p2.x) {
            /*差值为正负1，循环时用到*/
            int j=(p1.y-p2.y)/Math.abs(p1.y-p2.y);
            while(p1.y!=p2.y){
                p2.y+=j;
                int k=pointToPosition(p2,n);
                /*如果对应坐标点不为空(中间y相差仅为1)*/
                if(dataList.get(k).get("whichPicture") != R.drawable.blank && p1.y != p2.y){
                    b=false;
                    break;
                }
            }
        }
        else{
            b=false;
        }
        return b;
    }
    //水平消除判断方法-->
    public static boolean testHorizontal(Point p1,Point p2,ArrayList<HashMap<String, Integer>> dataList,int n){
        boolean b=true;
        if (p1.y==p2.y) {
            int j=(p1.x-p2.x)/Math.abs(p1.x-p2.x);
            while(p1.x!=p2.x){
                p2.x+=j;
                int k=pointToPosition(p2,n);
                if(dataList.get(k).get("whichPicture")!=R.drawable.blank && p1.x!=p2.x){
                    b=false;
                    break;
                }
            }
        }
        else{
            b=false;
        }
        return b;
    }

    //扫描line方法
    public static Vector<Line> scan(Point p1,Point p2,ArrayList<HashMap<String, Integer>> dataList,int n) {
        Vector<Line> v=new Vector<>();
        //查找A上边的线
        for (int y = p1.y; y >= 0; y--) {
            if (dataList.get(pointToPosition(new Point(p1.x, y),n)).get("whichPicture")==R.drawable.blank &&
                    dataList.get(pointToPosition(new Point(p2.x, y),n)).get("whichPicture")==R.drawable.blank &&
                    testHorizontal(new Point(p1.x,y),new Point(p2.x,y),dataList,n)) {
                v.add(new Line(0, new Point(p1.x,y), new Point(p2.x,y)));
            }
        }
        //查找A下边的线
        for (int y = p1.y; y < n; y++) {
            if (dataList.get(pointToPosition(new Point(p1.x, y),n)).get("whichPicture")==R.drawable.blank&&
                    dataList.get(pointToPosition(new Point(p2.x, y),n)).get("whichPicture")==R.drawable.blank&&
                    testHorizontal(new Point(p1.x,y), new Point(p2.x,y),dataList,n)) {
                v.add(new Line(0, new Point(p1.x,y), new Point(p2.x,y)));
            }
        }
        //查找A左面的线
        for (int x = p1.x; x >= 0; x--) {
            if (dataList.get(pointToPosition(new Point(x,p1.y),n)).get("whichPicture")==R.drawable.blank&&
                    dataList.get(pointToPosition(new Point(x, p2.y),n)).get("whichPicture")==R.drawable.blank&&
                    testVertical(new Point(x,p1.y),new Point(x,p2.y),dataList,n)) {
                v.add(new Line(1, new Point(x,p1.y), new Point(x,p2.y)));
            }
        }
        //查找A右面的线
        for (int x = p1.x; x < n; x++) {
            if (dataList.get(pointToPosition(new Point(x,p1.y),n)).get("whichPicture")==R.drawable.blank&&
                    dataList.get(pointToPosition(new Point(x, p2.y),n)).get("whichPicture")==R.drawable.blank&&
                    testVertical(new Point(x,p1.y), new Point(x,p2.y),dataList,n)) {
                v.add(new Line(1, new Point(x,p1.y), new Point(x,p2.y)));
            }
        }
        return v;
    }

    //定义一个clear方法-->
    public static void clear(int c,ArrayList<HashMap<String, Integer>> dataList) {
        HashMap<String, Integer> hMap = new HashMap<>();
        hMap.put("whichPicture", R.drawable.blank);
        dataList.set(c, hMap);
    }
    //把数字转换为坐标点-->
    public static Point positionToPoint(int a,int n){
        int px=a%n;
        int py=a/n;
        //见图片注标识
        return new Point(px, py);
    }

    //把坐标点转换为数字-->
    public static int pointToPosition(Point a,int n){
        return a.y*n+a.x;
    }

}

