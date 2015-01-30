/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//Код готов. Тарифная сетка и работа с ней
package com.pusku.smartmonit;

import java.util.ArrayList;

/**
 *
 * @author Panker_Work
 */
public class TarifGrid {
    private class Tarif{
        int value;
        int cost;
        String name;
        
        public Tarif(int num,int money,String s){
            value=num;name=s;cost=money;
        }
    }
    
    ArrayList<Tarif> tarifs=new ArrayList<>();
    /*
    2/2 - 120 грн и 2/2 - 150 грн
    3/3 - 150 грн и 3/3 - 200 грн
    5/5 -300 грн и 1/1 - 100 грн 

    */
    public TarifGrid(){
        tarifs.add(new Tarif(1,120,"2/2 Mb/s"));
        tarifs.add(new Tarif(2,150,"2/2 Mb/s"));
        tarifs.add(new Tarif(3,150,"3/3 Mb/s"));
        tarifs.add(new Tarif(4,200,"3/3 Mb/s"));
        tarifs.add(new Tarif(5,300,"5/5 Mb/s"));
    }
    
    public String toString(int num){
        for(Tarif tarif:tarifs){
            if(num==tarif.value)return tarif.name;
        }
        return"Not found";
    }
    
    public int getMoney(int num){
        for(Tarif tarif:tarifs){
            if(num==tarif.value)return tarif.cost;
        }
        return 0;
    }
    
    public String ToString(){
        String s="";
        for(Tarif tarif:tarifs){
            s+="<option value=\""+tarif.value+"\">"+tarif.name+" "+tarif.cost+" грн.";
        }
        return s;
    }
    
    public String[] getTarifArray(){
        String[] arr=null;
        ArrayList<String> toStr=new ArrayList<>();
        for (Tarif tarif : tarifs) {
            String str=tarif.name+" : "+tarif.cost;
        }
        
        arr=(String[]) toStr.toArray();
        return arr;
    }
    
    
}
