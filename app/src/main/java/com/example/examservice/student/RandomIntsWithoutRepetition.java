package com.example.examservice.student;

import java.util.Random;

public class RandomIntsWithoutRepetition {

    private boolean[] integers ;
    private int size ;

    public RandomIntsWithoutRepetition(int size){
        this.size = size ;
        integers = new boolean[size];
        for(int i =0; i<size; i++){
            integers[i] = false;
        }
    }

    public int get() {

        Random rand = new Random();
        int i = rand.nextInt(size);
        if(integers[i]){
            return get() ;
        }
        else{
            integers[i] = true ;
            return i ;
        }

    }
}
