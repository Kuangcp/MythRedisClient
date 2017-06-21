package config;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by https://github.com/kuangcp on 17-6-21  下午4:04
 * 验证test的猜想
 * 1.Before 和普通方法的顺序执行是不同的
 * 2.Assert.assertEquals()方法比较两个对象会有诡异的问题，比较的时候加上toString就好了
 */
public class JunitTest {
    Res res;
    @Before
    public void beforeA(){
        res = new Res("AAA");
        System.out.println("A:"+res.getName());
        System.out.println("A");
    }
    @Before
    public void beforeB(){
        res = new Res("BBB");
        System.out.println("B:"+res.getName());
        System.out.println("B");
    }
    @Test
    public void testC(){
        System.out.println("C");
    }
}
class Res{
    String name = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Res(String name) {
        this.name = name;
    }
}