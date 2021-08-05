import com.daddy.concurrent.utils.ThreadPoolUtils;
import com.daddy.service.concurrent.MultiThreadWriteBitmap;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName MultiThreadTest
 * @Description TODO
 * @Author niuyp
 * @Date 2021/7/7 16:11
 * @Version 1.0
 **/
public class MultiThreadTest {
    public static void main(String[] args) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
//        ThreadPoolExecutor threadPool = ThreadPoolUtils.getThreadPool(8, 10, 30L, 1);
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        int delta = 1249;
        for(int i = 0; i < 10000; i+=(delta+1)) {
            System.out.println("开始"+i+"   结束"+(i+delta));
            executorService.submit(new MultiThreadWriteBitmap(i,i+delta, stringRedisTemplate));
        }
    }
}
