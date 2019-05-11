package com.zbcn.demo.algrithm.solution;

/**
 * @Description: 数组中的重复数字
 * @Auther: zbcn8
 * @Date: 2019/5/11 14:22
 */
public class DumplicateTest {

    /**
     * 长度为n的数组里的所有数字都在0到n-1的范围内。查出数组中某个重复的数据
     * @param nums
     * @param length
     * @param duplication
     */
    public static boolean duplicate(int[] nums,int length,int[] duplication){
        //1.非法的输入，则返回false
        if(nums == null && nums.length <=0 ){
          return false;
        }
        //2.遍历查找第一个重复的数
        for (int i= 0; i< nums.length - 1; i++){
            //如果第i个数字和i不相等
            while (nums[i] != i) {
                //第i个元素和第i个元素值的位置的值相等
                if(nums[i] == nums[nums[i]]){
                    duplication[0] = nums[i];
                    return true;
                }
                swap(nums,i,nums[i]);
            }

        }
        //如果第i个和i相等
        return false;
    }

    /**
     * 将第i个元素的值，复制到第i个元素上
     * @param nums
     * @param i
     * @param j
     */
    private static void swap(int[] nums,int i, int j){

        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;

    }


    public static void main(String[] args) {
        int[] i = {2,3,1,0,2,5,3};
        int[] temp = new int[10];
        boolean duplicate = duplicate(i, i.length, temp);
        System.out.println( temp[0] + "is duplicate :" + duplicate);
    }
}
