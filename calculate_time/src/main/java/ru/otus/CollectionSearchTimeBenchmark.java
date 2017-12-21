package ru.otus;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

@Fork(1)
@Warmup(iterations = 10)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class CollectionSearchTimeBenchmark {

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        Collection<Integer> collection = new ArrayList<>();
        int max = 9_999_999;

        @Setup
        public void initState() {
            for (int i = 0; i < max; i++)
                collection.add(i);
        }
    }

    @Benchmark
    public boolean test(BenchmarkState state) {
        return state.collection.contains(state.max);
    }
}
