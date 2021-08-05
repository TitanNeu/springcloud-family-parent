import com.RedisRelativeApp;
import com.daddy.concurrent.utils.ThreadPoolUtils;
import com.daddy.service.concurrent.MultiThreadWriteBitmap;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName RedisTest
 * @Description TODO
 * @Author niuyp
 * @Date 2021/5/18 16:07
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisRelativeApp.class)
@Slf4j
public class RedisTest {
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
      * @Author niuyp
      * @Description 测试String数据结构, 键值为<String,String>格式的数据
      * @Date 14:59 2021/7/1
      * @Param []
      * @return void
      **/
    @Test
    public void getStringValue() throws Exception {

        stringRedisTemplate.opsForValue().set("user_1", "test1");

        String str = stringRedisTemplate.opsForValue().get("user_1");

        //json string to object
        System.out.println(str);

    }

    /**
      * @Author niuyp
      * @Description 测试增加反序列化配置RedisConfig后，操作数字自增
      * @Date 14:58 2021/7/1
      * @Param []
      * @return void
      **/

    @Test
    public void getIntegerValue() throws Exception {


        redisTemplate.opsForValue().set("test_1", 3);
        Long test_1 = redisTemplate.opsForValue().increment("test_1",19);

        System.out.println(test_1);

    }

    /**
      * @Author niuyp
      * @Description 测试hashmap
      * @Date 15:02 2021/7/1
      * @Param
      * @return
      **/
    @Test
    public void testHashMap(){
        String key = "redis_hash1";
        Map<String, Object> map = new HashMap<>();

        map.put("hash_key1","hash_value1");
        map.put("hash_key2",1);

        redisTemplate.opsForHash().putAll(key,map);
        String value = (String) redisTemplate.opsForHash().get(key, "hash_key1");
        System.out.println(value);

        List<String> ll = new ArrayList<>();
        ll.add("list_elem1");
        ll.add("list_elem2");

        redisTemplate.opsForList().rightPushAll("list_test", ll);

        Object list_test = redisTemplate.opsForList().rightPop("list_test");
        System.out.println(list_test);


    }

    /**
      * @Author niuyp
      * @Description list数据结构测试
      * @Date 15:52 2021/7/1
      * @Param
      * @return
      **/

    @Test
    public void testRedisList() {
        String listKey = "list_key";

        List<String> li = new ArrayList<>();
        li.add("1");
        li.add("2");
        redisTemplate.opsForList().rightPushAll(listKey,li);


        Object o = redisTemplate.opsForList().rightPop(listKey);
        System.out.println(o);

//        Object o = redisTemplate.opsForList().rightPop(listKey);
//        System.out.println(o);

    }


/**
  * @Description bit操作
  * @return void
  **/
    @Test
    public void bitTest() {

        Boolean test_bitmap = null;

        long count = 0L;

        long begin = System.currentTimeMillis();

        while(count++ < 1000000000L) {
//            test_bitmap = stringRedisTemplate.opsForValue().getBit("test_bitmap", 1000000004);
//            System.out.println("之前1000000004"+test_bitmap);
            //操作
            stringRedisTemplate.opsForValue().setBit("test_bitmap", 1000000004,count%2==0?false:true);

            test_bitmap = stringRedisTemplate.opsForValue().getBit("test_bitmap", 1000000004);
//            System.out.println("之后1000000004"+test_bitmap);

            if(count % 10000 == 0) {
                System.out.println("+++++++++++++++++++++"+count+"++++++++++++++");
                long end = System.currentTimeMillis();
                //测试本机10000个操作约为4s
                System.out.println("10000个操作 used(s): "+(end-begin)/1000 );

                begin = System.currentTimeMillis();
                System.out.println("+++++++++++++++++++++++++++++++++++");
            }

        }

    }

    @Test
    public void multiThreadBitmapWrite() {
        ThreadPoolExecutor threadPool = ThreadPoolUtils.getThreadPool(8, 10, 30L, 1);
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        int delta = 1249;

        for(int i = 0; i < 10000; i+=(delta+1)) {
            System.out.println("开始"+i+"   结束"+(i+delta));
//            executorService.submit(new MultiThreadWriteBitmap(i,i+delta, stringRedisTemplate));
        }

    }
}
