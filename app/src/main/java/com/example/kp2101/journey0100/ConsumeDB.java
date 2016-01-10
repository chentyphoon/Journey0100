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

    public static String addConsume(String jId,String cName,Double cDollar){
        String sql = "INSERT INTO `consume` (cName,cDollar,jId) VALUES ('"+cName+"',"+cDollar+","+jId+");";
        Log.d("addConsume", sql);
        String id = dbManager.DBexecuteUpdate(sql);
        return id;
    }
    public static void addUserConsume(String jId,String cId,List<ConsumeMember> consumemembers){
        String uId;
        String need;
        String paid;
        Double ujMoney=0.0;
        String pay;
        for(int pos=0;pos<consumemembers.size();pos++) {
            uId=consumemembers.get(pos).getuId();
            need=String.valueOf(consumemembers.get(pos).getNeed());
            paid=String.valueOf(consumemembers.get(pos).getPaid());

            String sql = "INSERT INTO `userconsume` (uId,cId,need,paid) VALUES ("+uId+","+cId+","+need+","+paid+");";
            Log.d("addUserConsume", sql);
            dbManager.DBexecuteUpdate(sql);

            String sql2="SELECT * FROM  `userjourney` where `uId`="+uId+" AND jId="+jId+";";
            Log.d("addUserConsume", sql2);
            ResultSet resultSet = dbManager.DBexecute(sql2);
            try {
                while (resultSet.next()){
                    ujMoney=resultSet.getDouble("ujMoney");
                }
                //dbManager.statement.close();
                //resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }


            pay=String.valueOf(ujMoney+consumemembers.get(pos).getPaid()-consumemembers.get(pos).getNeed());


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
        Log.d("consumeList sql",sql);
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
