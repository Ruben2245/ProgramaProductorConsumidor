import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class sharedMemory {
    public static final List<String> words = new ArrayList<>();
    public static final Object monitor = new Object();

    public static final AtomicInteger linesRead = new AtomicInteger(0);
    public static final AtomicInteger linesWriten = new AtomicInteger(0);
}