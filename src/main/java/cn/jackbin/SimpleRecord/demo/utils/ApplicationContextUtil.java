package cn.jackbin.SimpleRecord.demo.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author: d6day
 * @date: 2023/2/09/009 9:49
 * @description:
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;

    }

    /**
     * 动态注入单例bean实例
     *
     * @param beanName        bean名称
     * @param singletonObject 单例bean实例
     * @return 注入实例
     */
    public static Object registerSingletonBean(String beanName, Object singletonObject) {

        //将applicationContext转换为ConfigurableApplicationContext
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;

        //获取BeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getAutowireCapableBeanFactory();
        // 判断容器中是否存在该bean
        boolean b = configurableApplicationContext.containsBean(beanName);
        if (!b) {
            //动态注册bean.
            defaultListableBeanFactory.registerSingleton(beanName, singletonObject);
        }


        //获取动态注册的bean.
        return configurableApplicationContext.getBean(beanName);
    }
}
