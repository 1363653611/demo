package com.zbcn.demo.algrithm.sort;

import java.util.Arrays;

/**
 * 排序算法
 *
 * @author zbcn
 * @date 2019/1/25 11:11
 */
public class SortDemon {


    /**
     * 冒泡算法
     * 它重复地走访过要排序的数列，一次比较两个元素，如果它们的顺序错误就把它们交换过来。走访数列的工作是重复地进行直到没有再需要交换，也就是说该数列已经排序完成。这个算法的名字由来是因为越小的元素会经由交换慢慢“浮”到数列的顶端。
     * @param arrays
     */
    public static int[] bubbleSort(int[] arrays){
        if(arrays == null){
            return arrays;
        }
        //外层循环-> n 个元素，要循环n次
        for (int i = 0; i < arrays.length-1; i++) {

            //每一个元素和自己后边的ararys.length-i-i 个元素比较
            for (int j = 0; j <arrays.length-1-i ; j++) {
                if(arrays[j]>arrays[j+1]){
                    //交换位置
                    int tmp = arrays[j];
                    arrays[j] = arrays[j+1];
                    arrays[j+1] = tmp;
                }
            }
        }
        return arrays;

    }

    /**
     * 选择排序法：
     * 表现最稳定的排序算法之一，因为无论什么数据进去都是O(n2)的时间复杂度，所以用到它的时候，数据规模越小越好。唯一的好处可能就是不占用额外的内存空间了吧。
     * @param arrays
     */
    private static int[] selectionSort(int[] arrays){

        if(arrays == null){
            return arrays;
        }
        for (int i = 0; i <arrays.length -1 ; i++) {
            int min = i;//设置第一个为默认最小的值
            for (int j = i; j < arrays.length - 1 ; j++) { //找出最小的元素

                if(arrays[j] < arrays[min]){
                    min = j ; //交换索引
                }
            }
            //交换元素的位置
            int temp = arrays[min];
            arrays[min] = arrays[i];
            arrays[i] = temp;
        }
        return arrays;
    }

    /**
     * 插入排序
     * 它的工作原理是通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。
     * @param arrays
     * @return
     */
    private static int[] insertSort(int[] arrays){
        if(arrays == null){
            return arrays;
        }
        int current; //取出当前需要排序的数据
        for (int i = 0; i < arrays.length - 1; i++) {
            current = arrays[i + 1];
            int preIndex = i;
            //默认第一个是排好序的数据
            while(preIndex > 0 && current < arrays[preIndex]){
                // 从priIndex 开始一次往下寻找，将该元素往后移动一位，直到找到小于current的元素，将其插入,
                 arrays[preIndex + 1] = arrays[preIndex];
                 preIndex --;
            }

            arrays[preIndex] = current;
        }
        return arrays;
    }

    /**
     * 希尔排序，TODO  希尔算法没看懂
     * @param arrays
     * @return
     */
    private static int[] shellSort(int[] arrays){
        if(arrays == null){
            return arrays;
        }
        int len = arrays.length;
        int temp; //插入算法的临时值
        int gap = len/2;
        while (gap > 0){
            //小的插入排序
            for (int i = gap; i < len; i++) {
                temp = arrays[i];
                int preIndex = i -gap;
                while(preIndex > 0 && temp < arrays[preIndex]){
                    arrays[preIndex + gap] = arrays[preIndex];
                    preIndex -= gap;
                }
                arrays[preIndex + gap] = temp;
            }
            gap /= 2;
        }
        return arrays;
    }

    public static void main(String[] args) {
        int[] arrays = {1,6,5,4,2,8,4,66,32,22,11};
        //冒泡排序
        //System.out.println(Arrays.toString(bubbleSort(arrays)));
        //选择排序
        //System.out.println(Arrays.toString(selectionSort(arrays)));
        //插入排序
        System.out.println(Arrays.toString(insertSort(arrays)));
    }
}
