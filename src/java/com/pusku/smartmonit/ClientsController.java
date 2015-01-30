/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pusku.smartmonit;

/**
 *
 * @author Panker_Work
 */
public class ClientsController {
    private ClientsController cc=null;
    private ClientsController(){
        
    }
    
    public ClientsController getCC(){
        if(cc == null)cc=new ClientsController();
        return cc;
    }
    
    
}
