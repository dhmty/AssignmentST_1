/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MIP;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author dhmty
 */
public class shiftModel {
    private Date date;
    private int maCL;
    private int maST;
    private String tenCa;
    private int headCount;
    private int tgian;
    dsShortShift listSS=new dsShortShift();
    
    public shiftModel() {
    }

    public shiftModel(Date date, int maCL, int maST, String tenCa, int headCount, int tgian) {
        this.date = date;
        this.maCL = maCL;
        this.maST = maST;
        this.tenCa = tenCa;
        this.headCount = headCount;
        this.tgian = tgian;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public int getMaCL() {
        return maCL;
    }

    public void setMaCL(int maCL) {
        this.maCL = maCL;
    }

    public int getMaST() {
        return maST;
    }

    public void setMaST(int maST) {
        this.maST = maST;
    }

    public String getTenCa() {
        return tenCa;
    }

    public void setTenCa(String tenCa) {
        this.tenCa = tenCa;
    }

    public int getHeadCount() {
        return headCount;
    }

    public void setHeadCount(int headCount) {
        this.headCount = headCount;
    }

    public int getTgian() {
        return tgian;
    }

    public void setTgian(int tgian) {
        this.tgian = tgian;
    }
    public String toString(){
        String s="time : "+this.date+" Ma ST : "+this.maST+" Ma Ca : "+this.maCL+" Ten Ca: "+this.tenCa+" headcount : "+this.headCount+" tgian: "+this.tgian;
        return s; 
    }
    public static void main(String args[]) {
        // TODO code application logic here
    }
}
class shortShift{
    private Date date;
    private int maCL;
    private int maST;
    private int maCN;
    private int tgian;
    private int gio_bd;
    private int gio_kt;
    private int phut_bd;
    private int phut_kt;
    dsJob listJob=new dsJob();
    
    public shortShift() {
    }

    public shortShift(Date date, int maCL, int maST, int maCN, int tgian, int gio_bd, int gio_kt, int phut_bd, int phut_kt) {
        this.date = date;
        this.maCL = maCL;
        this.maST = maST;
        this.maCN = maCN;
        this.tgian = tgian;
        this.gio_bd = gio_bd;
        this.gio_kt = gio_kt;
        this.phut_bd = phut_bd;
        this.phut_kt = phut_kt;
    }

    
   
    public int getMaCN() {
        return maCN;
    }

    public void setMaCN(int maCN) {
        this.maCN = maCN;
    }

    public int getTgian() {
        return tgian;
    }

    public void setTgian(int tgian) {
        this.tgian = tgian;
    }

    public int getGio_bd() {
        return gio_bd;
    }

    public void setGio_bd(int gio_bd) {
        this.gio_bd = gio_bd;
    }

    public int getGio_kt() {
        return gio_kt;
    }

    public void setGio_kt(int gio_kt) {
        this.gio_kt = gio_kt;
    }

    public int getPhut_bd() {
        return phut_bd;
    }

    public void setPhut_bd(int phut_bd) {
        this.phut_bd = phut_bd;
    }

    public int getPhut_kt() {
        return phut_kt;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMaCL() {
        return maCL;
    }

    public void setMaCL(int maCL) {
        this.maCL = maCL;
    }

    public int getMaST() {
        return maST;
    }

    public void setMaST(int maST) {
        this.maST = maST;
    }

    public void setPhut_kt(int phut_kt) {
        this.phut_kt = phut_kt;
    }
    public String toString(){
        String s="   Ma CN : "+this.maCN+" Time : "+this.tgian; 
        return s; 
    }
    
}
class dsShortShift{
    int sl;
    List<shortShift> dsShortList=new ArrayList<>();
    public void show(){
        for (shortShift ss:dsShortList){
            System.out.println(ss.toString());
        }
    }
}
class job{
    private Date date;
    private int maCL;
    private int maST;
    private int maCN;
    private String ten;
    private int loai;
    private int tinhChat;
    private int soNg;
    private int soPh;

    public job(){
        
    }

    public job(Date date, int maCL, int maST, int maCN, String ten, int loai, int tinhChat, int soNg, int soPh) {
        this.date = date;
        this.maCL = maCL;
        this.maST = maST;
        this.maCN = maCN;
        this.ten = ten;
        this.loai = loai;
        this.tinhChat = tinhChat;
        this.soNg = soNg;
        this.soPh = soPh;
    }

   
    public String getTen() {
        return ten;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getMaCL() {
        return maCL;
    }

    public void setMaCL(int maCL) {
        this.maCL = maCL;
    }

    public int getMaST() {
        return maST;
    }

    public void setMaST(int maST) {
        this.maST = maST;
    }

    public int getMaCN() {
        return maCN;
    }

    public void setMaCN(int maCN) {
        this.maCN = maCN;
    }

    public int getLoai() {
        return loai;
    }

    public int getTinhChat() {
        return tinhChat;
    }

    public int getSoNg() {
        return soNg;
    }

    public int getSoPh() {
        return soPh;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }

    public void setTinhChat(int tinhChat) {
        this.tinhChat = tinhChat;
    }

    public void setSoNg(int soNg) {
        this.soNg = soNg;
    }

    public void setSoPh(int soPh) {
        this.soPh = soPh;
    }
    public String toString(){
        String s=this.ten+" Time : "+this.soPh; 
        return s; 
    }
}
class dsJob{
    int sl;
    List<job> dsJob =new ArrayList<>();
    public void show(){
        for (job jb:dsJob){
            System.out.println("      "+jb.toString());
        }
    }
}