package com.redis.utils;

import org.junit.Test;

/**
 * Created by https://github.com/kuangcp on 17-6-21  上午10:10
 */
public class MythTimeTest {
    @Test
    public void getTime() throws Exception {
        System.out.println(MythTime.getTime());
    }

    @Test
    public void getDateTime() throws Exception {
        System.out.println(MythTime.getDateTime());
    }
    @Test
    public void testDate(){
        System.out.println(MythTime.getDate());
    }


}
