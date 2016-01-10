package com.example.kp2101.journey0100;

import android.util.Log;
import android.view.View;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yu on 2016/1/6.
 */
public class TransDB {

    static DBManager dbManager = new DBManager();
    View view;


    public TransDB(){
    }

    public static void addTrans(String jIdFrom,String uIdFrom,String uIdTo,String money){

        Double ujMoneyFrom=0.0;
        Double ujMoneyTo=0.0;
        String sql = "INSERT INTO `transaction` (jId,uIdFrom,uIdTo,tMoney) VALUES ("+jIdFrom+","+uIdFrom+","+uIdTo+","+money+");";
        //Log.d("addTrans",sql);
        dbManager.DBexecuteUpdate(sql);

        //更新uIdFrom的錢
        String sql2="SELECT * FROM  `userjourney` where `uId`="+uIdFrom+" AND jId="+jIdFrom+";";
        //Log.d("addUserConsume", sql2);
        ResultSet resultSet = dbManager.DBexecute(sql2);
        try {
            while (resultSet.next()){
                ujMoneyFrom=resultSet.getDouble("ujMoney");
            }
            //dbManager.statement.close();
            //resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String ujMoneyFromNew=String.valueOf(ujMoneyFrom+Double.parseDouble(money));



        String sql3 = "UPDATE `userjourney` SET `ujMoney`="+ujMoneyFromNew+" WHERE `uId`="+uIdFrom+" AND `jId`="+jIdFrom+";";
        Log.d("ujMoneyFromNew", sql3);
        dbManager.DBexecuteUpdate(sql3);


        //更新uIdFrom的錢
        String sql4="SELECT * FROM  `userjourney` where `uId`="+uIdTo+" AND jId="+jIdFrom+";";
        //Log.d("addUserConsume", sql2);
        ResultSet resultSet2 = dbManager.DBexecute(sql4);
        try {
            while (resultSet2.next()){
                ujMoneyTo=resultSet.getDouble("ujMoney");
            }
            //dbManager.statement.close();
            //resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        String ujMoneyToNew=String.valueOf(ujMoneyTo-Double.parseDouble(money));




        String sql5 = "UPDATE `userjourney` SET `ujMoney`="+ujMoneyToNew+" WHERE `uId`="+uIdTo+" AND `jId`="+jIdFrom+";";
        Log.d("ujMoneyToNew", sql5);
        dbManager.DBexecuteUpdate(sql5);

    }

}
