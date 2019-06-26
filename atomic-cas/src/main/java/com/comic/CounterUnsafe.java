package com.comic;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author wangchen870
 * createAt 2019/6/26
 * updateAt 2019/6/26
 */
public class CounterUnsafe {

    volatile int i = 0;

    private static Unsafe unsafe = null;

    // 偏移量
    private static long valueOffset;

    static {
        //unsafe = Unsafe.getUnsafe();      theUnsafe
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);

            Field ifield = Counter.class.getDeclaredField("i");
            valueOffset = unsafe.objectFieldOffset(ifield);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public void add(){
        //int current = CAS(currentValue,newValue);
        //自旋
        for (;;){
            int currentValue = unsafe.getIntVolatile(this, valueOffset);
            if (unsafe.compareAndSwapInt(this,valueOffset,currentValue,currentValue+1)){
                //操作成功
                break;
            }

        }
    }

    public static void main(String[] args) {

    }

}
 
