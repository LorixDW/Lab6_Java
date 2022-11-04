import java.lang.reflect.Array;
import java.util.Arrays;

public class Main {
    static final int size = 500000;
    public static void main(String[] args) {
        float[] arr = CreateArrV1();
        System.out.println(arr[0]);
        System.out.println(arr[size - 1]);

        float[] arr1 = CreateArrV2();
        System.out.println(arr1[0]);
        System.out.println(arr1[size - 1]);
    }
    public static float[] CreateArrV1(){
        long s = System.currentTimeMillis();
        float[] arr = new float[size];
        Arrays.fill(arr, 1.0f);
        for (int i = 0; i < size; i++) {
            arr[i] = (float )(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i  / 5) * Math.cos(0.4f + i  / 2));
        }
        long f = System.currentTimeMillis();
        System.out.println(f - s);
        return arr;
    }
    public static float[] CreateArrV2(){
        long s = System.currentTimeMillis();
        float[] arr = new float[size];
        Arrays.fill(arr, 1.0f);
        float[] a1 = Arrays.copyOfRange(arr, 0, size / 2 );
        float[] a2 = Arrays.copyOfRange(arr, size / 2, size );
        Thread t1 = new Thread(() ->{
            for(int i = 0; i < a1.length; i++){
                a1[i] = (float )(a1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i  / 5) * Math.cos(0.4f + i  / 2));
            }
        });

        Thread t2 = new Thread(() -> {
            for(int i = a2.length; i < a2.length * 2; i++){
                a2[i - a2.length] = (float )(a2[i - a2.length] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        }
        catch (InterruptedException e) {

        }
        System.arraycopy(a1, 0, arr, 0, a1.length);
        System.arraycopy(a2, 0, arr, a2.length , a2.length);
        long f = System.currentTimeMillis();
        System.out.println(f - s);
        return arr;
    }
}