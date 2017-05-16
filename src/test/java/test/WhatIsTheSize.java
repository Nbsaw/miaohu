package test;

import org.junit.Test;

import javax.validation.constraints.Null;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by nbsaw on 2017/5/16.
 */
public class WhatIsTheSize {
    @Test
    public void SetSize(){
        Set<String> stringSet = new HashSet<>();
        stringSet.add("");
        stringSet.add(null);
        try{
            stringSet.removeIf(String::isEmpty);
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println(stringSet.size());
    }

    @Test
    public void chars(){
        String s = "1we8dfgKwqe";
        s.chars().forEach(c->{System.out.print(  "-"+(char) c + "-");});
    }
}
