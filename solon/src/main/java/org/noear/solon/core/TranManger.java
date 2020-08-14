package org.noear.solon.core;

import org.noear.solon.annotation.XTran;
import org.noear.solon.ext.RunnableEx;

import java.util.function.Function;

/**
 * 事务管理器
 * */
public class TranManger {
    public static Function<XTran, Tran> factory;
    private static ThreadLocal<ValHolder<Tran>> rootLocal = new ThreadLocal<>();

    public static void execute(XTran anno, RunnableEx runnable) throws Throwable {
        if (anno == null) {
            runnable.run();
            return;
        }

        ValHolder<Tran> root = rootLocal.get();


        //根事务不存在
        if (root == null) {
            //::支持但不必需
            if(anno.policy() == TranPolicy.supports){
                runnable.run();
                return;
            }else {
                //新建事务
                Tran tran = factory.apply(anno);

                ValHolder<Tran> vh = new ValHolder<>();
                vh.value = tran;
                vh.tag = anno.value();

                try {
                    rootLocal.set(vh);
                    tran.execute(runnable);
                } finally {
                    rootLocal.remove();
                }
            }
        } else {
            //根事务已经存在
            if (root.value.isMaster()) {
                //如果是主事务，则加入
                //
                Tran tran = factory.apply(anno);

                root.value.add(tran);
                tran.execute(runnable);
                return;
            }

            if(root.tag.equals(anno.value())){
                //如果名字相同，则直接执行
                runnable.run();
            }else{
                //新建事务
                Tran tran = factory.apply(anno);
                tran.execute(runnable);
            }
        }
    }
}
