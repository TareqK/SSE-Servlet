/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.busr.sse;

/**
 *
 * @author tareq
 */
public class Event {

    private final String eventString;
    
    protected Event(String eventString) {
        this.eventString  = eventString;
    }

    protected String getString() {
        return eventString;
    }
    
}