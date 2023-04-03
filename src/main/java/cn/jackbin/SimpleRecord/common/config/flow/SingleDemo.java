package cn.jackbin.SimpleRecord.common.config.flow;

/**
 * @author: d6day
 * @date: 2022/12/22/022 9:57
 * @description:
 */
public class SingleDemo {
    private volatile static SingleDemo instance;

    private SingleDemo() {

    }

    public static SingleDemo getInstance() {
        if (instance == null) {
            synchronized (SingleDemo.class) {
                if (instance == null) {
                    instance = new SingleDemo();
                }
            }
        }

        return instance;
    }

    public void message() {
        System.out.println("hello world");
    }

}
