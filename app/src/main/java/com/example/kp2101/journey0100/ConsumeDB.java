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
public class ConsumeDB {

    static DBManager dbManager = new DBManager();
    View view;

    public ConsumeDB(){
    }



    public static String addConsume(String jId, String cName, Integer cDollar, String cLocation, String cLon, String cLat, String cPic, String cDescrip){
        String sql = "INSERT INTO `consume` (`jId`, `cName`, `cDollar`, `cLocation`, `cLon`, `cLat`, `cPic`, `cDescrip`) VALUES ('"+jId+"','"+cName+"','"+cDollar+"','"+cLocation+"','"+cLon+"','"+cLat+"','"+cPic+"','"+cDescrip+"');";
        //Log.d("addConsume", sql);
        String id = dbManager.DBexecuteUpdate(sql);
        return id;
    }
    public static void addUserConsume(String jId,String cId,List<ConsumeMember> consumemembers){
        String uId;
        String need="";
        String paid="";
        String ujMoney="";
        String pay="";
        ResultSet resultSet;
        for(int pos=0;pos<consumemembers.size();pos++) {
            uId=consumemembers.get(pos).getuId();
            need=consumemembers.get(pos).getNeed();
            paid=consumemembers.get(pos).getPaid();

            String sql = "INSERT INTO `userconsume` (uId,cId,need,paid) VALUES ("+uId+","+cId+","+need+","+paid+");";
            Log.d("addUserConsume", sql);
            dbManager.DBexecuteUpdate(sql);

            String sql2="SELECT * FROM  `userjourney` where `uId`="+uId+" AND `jId`="+jId+";";
            Log.d("addUserConsume", sql2);
            resultSet = dbManager.DBexecute(sql2);
            try {
                while (resultSet.next()){

                    Log.d("resultSet.getString(ujMoney)", resultSet.getString("ujMoney"));
                    ujMoney=resultSet.getString("ujMoney");
                }
                //dbManager.statement.close();
                //resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Log.d("ujMoney",ujMoney);
            Log.d("paid",paid);
            Log.d("need",need);
            pay = String.valueOf(Integer.parseInt(ujMoney) + Integer.parseInt(paid) - Integer.parseInt(need));



            String sql3 = "UPDATE `userjourney` SET `ujMoney`="+pay+" WHERE `uId`="+uId+" AND `jId`="+jId+";";
            Log.d("addUserConsume", sql3);
            dbManager.DBexecuteUpdate(sql3);

        }

    }

    public static void deleteJourney(String jId){
        String sql = "DELETE FROM `journey` WHERE jId="+jId+"";
        dbManager.DBexecuteUpdate(sql);
    }

    public static List<Consume> consumeList(String jId){
        String sql="SELECT * FROM  `consume` where jId="+jId+"";
        //Log.d("consumeList sql",sql);
        ResultSet resultSet = dbManager.DBexecute(sql);

        List<Consume> consumeList = new ArrayList<Consume>();
        try {
            while (resultSet.next()){
                Consume consume = new Consume(resultSet.getString("cId"), resultSet.getString("cName"), resultSet.getDouble("cDollar"));
                consumeList.add(consume);
            }
            //dbManager.statement.close();
            //resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consumeList;

    }
}
