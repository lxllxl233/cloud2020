package com.woqiyounai.order8000.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLB implements LoadBalancer{

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public final int getAndIncrement(){
        int current;
        int next;
        do {
            current = atomicInteger.get();
            next = current >= Integer.MAX_VALUE?0:current+1;
            //比较并交换；如果当前值与内存中的值一致设置值next并返回true，不一致返回false
        }while (!this.atomicInteger.compareAndSet(current,next));
        System.out.println("******next（第几次）："+next);
        System.out.println("***current（当前值）："+current);
        return next;
    }

    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        int index = getAndIncrement()%serviceInstances.size();
        return serviceInstances.get(index);
    }
}
