package studio.dexter.http_module;

import java.util.concurrent.ExecutionException;

/**
 * Created by Dexter on 2015/7/16.
 */
public class Sample {

    public void sample() {
        HttpGetPost httpGetPost = new HttpGetPost("your URL", SampleBundle[].class, HttpGetPost.HttpType.GET);
        try {
            SampleBundle[] bundle = (SampleBundle[]) httpGetPost.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * your bundle
     */
    class SampleBundle {
        String sampleTitle;
        String sampleMessage;
    }
}
