package com.zbcn.demo.exam;

public class GMExam {

    public static void main(String[] args) {
        String a = "aa aa aaaa aaa a a a aa aa a";
        //选择排序算法中的并归算法
//        String des = getLongestSubStr(a);
//        if(StringUtils.isNotBlank(des)){
//            System.out.println(des.length());
//        }
        ;
        System.out.println(getLong(a));
    }

//    private static int getLongest(String str){
//        int length = 0;
//        while(true){
//            String splitStr = null;
//            int j = str.indexOf(" ");
//            if(j<0) {
//                break;
//            }
//            splitStr = str.substring(0, j);
//            if(length < splitStr.length()){
//                length = splitStr.length();
//            }
//            str = str.substring(j+1);
//        }
//        return length;
//    }

//    private static int getLongest(String str){
//        int currentPosition = 0;
//        int startPosition = 0;
//        int total = 0;
//        for (int i = 0; i <str.length() ; i++) {
//            startPosition = i;
//            char c = str.charAt(i);
//            if( Character.isSpaceChar(c)){
//                int temp = startPosition - currentPosition;
//                if(temp > total){
//                   total = temp;
//                    currentPosition = startPosition;
//                }
//            }
//        }
//        return startPosition -currentPosition;
//    }

    private static int getLongest(String str){
        int maxLength = 0;
        int currentLength = 0;
        for (int i = 0; i <str.length() ; i++) {
            char c = str.charAt(i);
            if( Character.isSpaceChar(c)){
                if(currentLength > maxLength){
                    maxLength = currentLength;
                }
                currentLength = 0;
                continue;
            }
            currentLength ++;
        }
        return maxLength;
    }

    private static int getLong(String str){
        int maxLength = 0;
        int currentLength = 0;
        for (int i = 0; i <str.length() ; i++) {
            char c = str.charAt(i);
            if (!Character.isSpaceChar(c)){
                //currentLength = 0;
                currentLength ++;
                continue;
            }else{
                if (currentLength > maxLength){
                    maxLength = currentLength;
                    //currentLength = 0;
                }
              currentLength = 0;
            }




        }
        return maxLength;
    }


    private static String getLongestSubStr(String str){

        String[] split = str.split(" ");
        if(split.length == 0){
            return null;
        }
        return mergeSort(split,0,split.length-1);

    }
    //并归排序
    private static String mergeSort(String[] a, int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            // 左边
            mergeSort(a, low, mid);
            // 右边
            mergeSort(a, mid + 1, high);
            // 左右归并
            merge(a, low, mid, high);
        }
        return a[a.length-1];
    }

    private static void merge(String[] a, int low, int mid, int high) {
        String[] temp = new String[high - low + 1];
        int i = low;// 左指针
        int j = mid + 1;// 右指针
        int k = 0;
        // 把较小的数先移到新数组中
        while (i <= mid && j <= high) {
            if (a[i].length() < a[j].length()) {
                temp[k++] = a[i++];
            } else {
                temp[k++] = a[j++];
            }
        }
        // 把左边剩余的数移入数组
        while (i <= mid) {
            temp[k++] = a[i++];
        }
        // 把右边边剩余的数移入数组
        while (j <= high) {
            temp[k++] = a[j++];
        }
        // 把新数组中的数覆盖nums数组
        for (int k2 = 0; k2 < temp.length; k2++) {
            a[k2 + low] = temp[k2];
        }
    }
}
