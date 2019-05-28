package com.comic.blankredis.service.impl;

import com.comic.blankredis.service.ISeckillService;

import java.util.HashMap;
import java.util.Map;

/**
 * 秒杀接口的实现类
 *
 * @author wangchen870
 * createAt 2019/5/28
 * updateAt 2019/5/28
 */
public class SecKillServiceImpl implements ISeckillService {

    //库存
    public static Map<Long, Long> inventory ;
    static{
        inventory = new HashMap<Long, Long>();
        inventory.put(10000001L, 10000l);
        inventory.put(10000002L, 10000l);
    }

    @Override
    public void secKill(String userID, Long commidityID) {
        //最简单的秒杀，这里仅作为demo示例
        reduceInventory(commidityID);
    }

    //模拟秒杀操作，姑且认为一个秒杀就是将库存减一，实际情景要复杂的多
    public Long reduceInventory(Long commodityId){
        inventory.put(commodityId, inventory.get(commodityId) - 1);
        return inventory.get(commodityId);
    }

}
 
