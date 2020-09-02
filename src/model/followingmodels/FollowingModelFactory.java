/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.followingmodels;

/**
 *
 * @author darkm
 */
public class FollowingModelFactory {
    
    public static FollowingModel getFollowingModel(String type){
        FollowingModel model;
        
        switch(type){
            case IDM.NAME:
                model = new IDM();
                break;
            case Kerner.NAME:
                model = new Kerner();
                break;
            case Krauss.NAME:
                model = new Krauss();
                break;
            case PW2009.NAME:
                model = new PW2009();
                break;
            default:
                model = new Kerner();
                break;
        }
        return model;
    }
}
