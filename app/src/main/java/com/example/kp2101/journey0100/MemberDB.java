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
public class MemberDB {

    static DBManager dbManager = new DBManager();
    View view;




    public MemberDB(){
    }

    public static void addMemberToJourney(String uId,String jId){
        String sql = "INSERT INTO `userjourney` (uId,jId) VALUES ("+uId+","+jId+");";
        //Log.d("addMemberToJourney",sql);
        dbManager.DBexecuteUpdate(sql);

        //String sql2 = "INSERT INTO userjourney (jId,uId) VALUES ("+id+","+uId+");";
        //Log.d("addJourney sql2",sql2);
        //dbManager.DBexecuteUpdate(sql2);

    }

    public static void deleteJourney(String jId){
        String sql = "DELETE FROM `journey` WHERE jId="+jId+"";
        dbManager.DBexecuteUpdate(sql);
    }


    public static Member memberMe(String jId,String uId){
        String sql="SELECT * FROM `userjourney`  NATURAL JOIN `user` where jId="+jId+" AND uId="+uId+"";
        //Log.d("memberMe sql", sql);
        ResultSet resultSet = dbManager.DBexecute(sql);
        Member member = null;
        try {
            resultSet.next();
            member = new Member(resultSet.getString("uId"), resultSet.getString("uName"), resultSet.getString("ujMoney"));

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

        return member;

    }

    public static List<Member> memberList(String jId,String uId){
        String sql="SELECT * FROM `userjourney`  NATURAL JOIN user where jId="+jId+" AND uId <> "+uId+"";
        Log.d("memberList sql", sql);
        ResultSet resultSet = dbManager.DBexecute(sql);

        List<Member> memberList = new ArrayList<Member>();
        try {
            while (resultSet.next()){
                Member member = new Member(resultSet.getString("uId"), resultSet.getString("uName"), resultSet.getString("ujMoney"));
                memberList.add(member);
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

        return memberList;

    }

    public static List<ConsumeMember> consumeMemberAddList(String jId){
        String sql="SELECT * FROM userjourney  NATURAL JOIN user where jId=\""+jId+"\"";
        //Log.d("memberList sql",sql);
        ResultSet resultSet = dbManager.DBexecute(sql);

        List<ConsumeMember> consumeMemberList = new ArrayList<ConsumeMember>();
        try {
            while (resultSet.next()){
                ConsumeMember consumemember = new ConsumeMember(resultSet.getString("uId"), resultSet.getString("uName"));
                consumeMemberList.add(consumemember);
            }
            //dbManager.statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consumeMemberList;

    }
}
