package com.hecy.jdbctools.bean.pojo







import java.util.Date; 
import java.util.Date; 


public class User {

    /**
    * 主键ID
    * 
    */
    private  Long  id;

    /**
    * 别名
    * 别名
    */
    private  String  nickname;

    /**
    * 姓名
    * 姓名
    */
    private  String  name;

    /**
    * 电话号码
    * 
    */
    private  String  phone;

    /**
    * 电话号码
    * 
    */
    private  String  phone;

    /**
    * 住址
    * 
    */
    private  String  address;

    /**
    * 认证状态:1-已认证，2-为认证
    * 
    */
    private  String  status;

    /**
    * 
    * 入库时间
    */
    private  Date  itime;

    /**
    * 
    * 更新时间
    */
    private  Date  utime;


    public   Long  getId(){
        return id;
    }
    public   String  getNickname(){
        return nickname;
    }
    public void setNickname( String  nickname){
        this.nickname = nickname;
    }
    public   String  getName(){
        return name;
    }
    public void setName( String  name){
        this.name = name;
    }
    public   String  getPhone(){
        return phone;
    }
    public void setPhone( String  phone){
        this.phone = phone;
    }
    public   String  getPhone(){
        return phone;
    }
    public void setPhone( String  phone){
        this.phone = phone;
    }
    public   String  getAddress(){
        return address;
    }
    public void setAddress( String  address){
        this.address = address;
    }
    public   String  getStatus(){
        return status;
    }
    public void setStatus( String  status){
        this.status = status;
    }
    public   Date  getItime(){
        return itime;
    }
    public void setItime( Date  itime){
        this.itime = itime;
    }
    public   Date  getUtime(){
        return utime;
    }
    public void setUtime( Date  utime){
        this.utime = utime;
    }



}