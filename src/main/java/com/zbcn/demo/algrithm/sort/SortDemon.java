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

    /**
     * 并归排序(有问题)
     * @param arrays
     */
    public static int[] mergerSort(int[] arrays){

        if(arrays.length < 2){
            return arrays;
        }
        int mid = arrays.length/2;//获取中间的长度
        int[] left = Arrays.copyOfRange(arrays, 0, mid);//
        int[] right = Arrays.copyOfRange(arrays, mid, arrays.length);
        return merger(mergerSort(left),mergerSort(right));//回调函数

    }

    /**
     * 并归算法
     * @param left
     * @param right
     * @return
     */
    public static int[] merger(int[] left,int[] right){
        int[] result = new int[left.length + right.length];//新数据的长度
        for (int index = 0, i=0, j=0; index < result.length; index++) {
            if (i >= left.length){
                result[index] = right[j++];
            }else if(j >= right.length){
                result[index] = right[i++];
            }else if(left[i] >right[j]){
                result[index] = right[j++];
            }else {
                result[index] = left[i++];
            }
        }
        return result;
    }

    /**
     * 快速排序
     * @param arrays
     * @return
     */
    public static int[] quickSort(int[] arrays,int left,int right){
        //
        if(left > right){
            return arrays;
        }
        int temp,i,j,t;
        temp = arrays[left];//存储基准数
        i = left; //i为左哨兵
        j = right; // j 为右哨兵

        while(i != j){//顺序很重要，要先从右边开始找
            while (arrays[j] >= temp && i < j){ //右 - 左
                j--;
            }
            while(arrays[i] <= temp &&  i < j){ //左 - 右
                i++;
            }

            if(i < j){ //交换两个数在数组中的位置
                t = arrays[i];
                arrays[i] = arrays[j];
                arrays[j] = t;
            }
            //最终将基准数归位
            arrays[left] = arrays[i];
            arrays[i] = temp;
        }
        quickSort(arrays,left, i-1);//继续处理左边的，这里是一个递归的过程
        quickSort(arrays,i+1, right);//继续处理右边的 ，这里是一个递归的过程

//        int smallIndex = partition(arrays,start,end);
//
//        if(smallIndex > start){
//            quickSort(arrays,start,smallIndex - 1);
//        }
//        if(smallIndex < end){
//            quickSort(arrays,smallIndex + 1,end);
//        }
        //快速排序算法
        return arrays;
    }

    /**
     * 快速排序算法
     * @param arrays
     * @param end
     * @param start
     * @return
     */
    public static int partition(int[] arrays,int end,int start){

        int pivot = (int)(start + Math.random()*(end - start + 1));
        int smallIndex = start - 1;
        swap(arrays,pivot,end);
        for (int i = start; i <= end ; i++) {
            if(arrays[i] <= arrays[end]){
                smallIndex++;
                if(i > smallIndex){
                    swap(arrays,i,smallIndex);
                }
            }
        }
        return smallIndex;

    }

    /**
     * 数据转换
     * @param arrays
     * @param i
     * @param j
     * @return
     */
    private static int[] swap(int[] arrays,int i, int j){

        int temp = arrays[i];
        arrays[i] = arrays[j];
        arrays[j] = temp;
        return arrays;
    }



    public static void main(String[] args) {
        int[] arrays = {6,1,2,7,9,3,4,5,10,8};
        //冒泡排序
        //System.out.println(Arrays.toString(bubbleSort(arrays)));
        //选择排序
        //System.out.println(Arrays.toString(selectionSort(arrays)));
        //插入排序
        //System.out.println(Arrays.toString(insertSort(arrays)));
        //希尔排序
        //System.out.println(Arrays.toString(shellSort(arrays)));
        //并归排序
        //System.out.println(Arrays.toString(mergerSort(arrays)));
        //快速排序
        System.out.println(Arrays.toString(quickSort(arrays,0,arrays.length-1)));
    }
}
