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
        Double uAccountFrom=0.0;
        Double uAccountTo=0.0;
        String sql = "INSERT INTO `transaction` (jId,uIdFrom,uIdTo,tMoney) VALUES ("+jIdFrom+","+uIdFrom+","+uIdTo+","+money+");";
        Log.d("addTrans",sql);
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
        //Log.d("money",money);
        //Log.d("ujMoneyFrom",String.valueOf(ujMoneyFrom));
        String ujMoneyFromNew=String.valueOf(ujMoneyFrom+Double.parseDouble(money));
        //Log.d("ujMoneyFromNew",ujMoneyFromNew);


        String sql3 = "UPDATE `userjourney` SET `ujMoney`="+ujMoneyFromNew+" WHERE `uId`="+uIdFrom+" AND `jId`="+jIdFrom+";";
        //Log.d("ujMoneyFromNew", sql3);
        dbManager.DBexecuteUpdate(sql3);


        //更新uIdTo的錢
        String sql4="SELECT * FROM  `userjourney` where `uId`="+uIdTo+" AND jId="+jIdFrom+";";
        Log.d("sql4", sql4);
        ResultSet resultSet2 = dbManager.DBexecute(sql4);
        try {
            while (resultSet2.next()){
                ujMoneyTo=resultSet2.getDouble("ujMoney");
            }
            //dbManager.statement.close();
            //resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Log.d("money",money);
        Log.d("ujMoneyTo",String.valueOf(ujMoneyTo));
        String ujMoneyToNew=String.valueOf(ujMoneyTo-Double.parseDouble(money));
        Log.d("ujMoneyToNew",ujMoneyToNew);



        String sql5 = "UPDATE `userjourney` SET `ujMoney`="+ujMoneyToNew+" WHERE `uId`="+uIdTo+" AND `jId`="+jIdFrom+";";
        Log.d("ujMoneyToNew", sql5);
        dbManager.DBexecuteUpdate(sql5);



        //更新uIdFrom的錢
        String sql6="SELECT * FROM  `user` where `uId`="+uIdFrom+";";
        //Log.d("addUserConsume", sql6);
        ResultSet resultSet3 = dbManager.DBexecute(sql6);
        try {
            while (resultSet3.next()){
                uAccountFrom=resultSet3.getDouble("uAccount");
            }
            //dbManager.statement.close();
            //resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Log.d("money",money);
        //Log.d("ujMoneyFrom",String.valueOf(ujMoneyFrom));
        String uAccountFromNew=String.valueOf(uAccountFrom-Double.parseDouble(money));
        //Log.d("ujMoneyFromNew",ujMoneyFromNew);


        String sql7 = "UPDATE `user` SET `uAccount`="+uAccountFromNew+" WHERE `uId`="+uIdFrom+";";
        //Log.d("ujMoneyFromNew", sql3);
        dbManager.DBexecuteUpdate(sql7);


        //更新uIdTo的錢
        String sql8="SELECT * FROM  `user` where `uId`="+uIdTo+";";
        //Log.d("addUserConsume", sql8);
        ResultSet resultSet4 = dbManager.DBexecute(sql8);
        try {
            while (resultSet4.next()){
                uAccountTo=resultSet4.getDouble("uAccount");
            }
            //dbManager.statement.close();
            //resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Log.d("money",money);
        //Log.d("ujMoneyFrom",String.valueOf(ujMoneyFrom));
        String uAccountToNew=String.valueOf(uAccountTo+Double.parseDouble(money));
        //Log.d("ujMoneyFromNew",ujMoneyFromNew);


        String sql9 = "UPDATE `user` SET `uAccount`="+uAccountToNew+" WHERE `uId`="+uIdTo+";";
        //Log.d("ujMoneyFromNew", sql3);
        dbManager.DBexecuteUpdate(sql9);

    }

}
