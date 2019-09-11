package com.zbcn.demo.algrithm.sort;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;

public class BubbleSort implements IArraySort {
    @Override
    public int[] sort(int[] sourceArray) {

        int[] copys = Arrays.copyOf(sourceArray, sourceArray.length);
        //minSort(copys);
        maxSort(copys);
        return copys;
    }

    /**
     * 从开头开始交换
     * @param copys
     */
    private void minSort(int[] copys) {
        for (int i = 0; i <copys.length ; i++) {
            //判断该数组是否已经是排好序的数组
            boolean flag = true;
            for (int j = 0; j <copys.length-1 - i ; j++) {
                if(copys[j] > copys[j+1]){
                  int temp = copys[j];
                  copys[j] = copys[j+1];
                  copys[j+1] = temp;

                  flag = false;
                }
            }
            //未进行交换，说明已经排好序了：如果顺序不对，必定有一次交换
            if(flag){
                break;
            }
        }
    }

    private void maxSort(int[] copys) {
        //控制循环次数
        for (int i = 0; i < copys.length; i++) {

            boolean flag = true;

            for (int j = copys.length -1; (j <= copys.length -1) && (j > i); j--) {
                if(copys[j] < copys[j-1]){
                    int temp = copys[j];
                    copys[j] = copys[j-1];
                    copys[j-1] = temp;
                    flag = false;
                }
            }
            if(flag){
                break;
            }
        }
    }


    public static void main(String[] args) {
        BubbleSort bubbleSort = new BubbleSort();
        int[] arrays = {8,7,6,5,4,3,2,1};
        int[] sort = bubbleSort.sort(arrays);
        System.out.println(JSON.toJSONString(sort));
    }
}
