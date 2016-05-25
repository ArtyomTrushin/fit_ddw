/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testapp;

/**
 *
 * @author atrushin
 */
public class LondonFCFilter extends GateClientFilter {

    @Override
    String getJapeFileName() {
        return "jape-example.jape";
    }

    @Override
    String getTokenAnnotation() {
        return "FootballTeam";
    }
    
}