package com.comic.common.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Java8New {

    public static void main(String[] args){
        List<Integer> list = Arrays.asList(5, 4, 3, 2, 1);
        List<Integer> intList = list.stream().map(a->a*a).collect(Collectors.toList());
        System.out.println(intList);
    }

}
