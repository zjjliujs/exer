import java.io.File;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.Random;
import java.util.Arrays;

public class ForkJoinSort {
    private static final int NUM_CNT = 1000000;

    public static void main(String[] args) throws Exception {
        long[] src = new long[NUM_CNT];
        Random random = new Random(System.currentTimeMillis());
        for (int i=0; i<src.length; ++i) {
            src[i] = random.nextLong();
        }
        System.out.println ("First number:" + src[0]);
        System.out.println ("Last number:" + src[NUM_CNT-1]);

        SortTask st = new SortTask(src, 0, src.length);

        ForkJoinPool pool = new ForkJoinPool();

        long startTime = System.currentTimeMillis();
        pool.invoke(st);
        long endTime = System.currentTimeMillis();

        //System.out.println (Arrays.toString(src));
        System.out.println ("Minimum number:" + src[0]);
        System.out.println ("Maximum number:" + src[NUM_CNT-1]);

        System.out.println(String.format("Sort %d took %d milliseconds.",NUM_CNT, (endTime - startTime))); 
    }
}

class SortTask extends RecursiveAction {
    private final long[] array; 
    private final int lo, hi;

    SortTask(long[] array, int lo, int hi) {
        this.array = array; 
        this.lo = lo; 
        this.hi = hi;
    }

    SortTask(long[] array) { 
        this(array, 0, array.length); 
    }

    void sortSequentially(int lo, int hi) {
        Arrays.sort(array, lo, hi);
    }

    void merge(int lo, int mid, int hi) {
        long[] buf = Arrays.copyOfRange(array, lo, mid);
        for (int i = 0, j = lo, k = mid; i < buf.length; j++)
            array[j] = (k == hi || buf[i] < array[k]) ?
                buf[i++] : array[k++];
    }

    static final int THRESHOLD = 1000;

    @Override
        protected void compute() {
            if (hi - lo < THRESHOLD)
                sortSequentially(lo, hi);
            else {
                int mid = (lo + hi) >>> 1;
                invokeAll(new SortTask(array, lo, mid),
                        new SortTask(array, mid, hi));
                merge(lo, mid, hi);
            }
        }
}
