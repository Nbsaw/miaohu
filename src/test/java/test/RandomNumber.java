package test;

import org.junit.Test;

import java.util.Random;

/**
 * Created by fz on 17-4-6.
 */
public class RandomNumber {
    public static void main(String[] args){
        String randomNumber = String.valueOf((int)((Math.random()*9+1)*100000));
        System.out.println(randomNumber);
    }
}
