package net.catharos.lib.shank.service.lifecycle;

import net.catharos.lib.core.collections.ArrayUtil;

/**
 * Represents a LifecycleTimeline
 */
public class LifecycleTimeline {

    private Lifecycle[] lifecycles;
    private Lifecycle currentLifecycle;

    public LifecycleTimeline(Lifecycle... lifecycles) {
        this.lifecycles = lifecycles;
        setCurrentLifecycle(lifecycles[0]);
    }

    public Lifecycle[] getPrevious() {
        return getPrevious(currentLifecycle);
    }

    public Lifecycle[] getPrevious(Lifecycle lifecycle) {
        int index = indexOf(lifecycle);

        int length = index + 1;

        Lifecycle[] previous = new Lifecycle[length];
        System.arraycopy(lifecycles, 0, previous, 0, length);
        ArrayUtil.reverse(previous);

        return previous;
    }

    public void setCurrentLifecycle(Lifecycle lifecycle) {
        indexOf(lifecycle);
        this.currentLifecycle = lifecycle;
    }

    public void nextLifecycle() {
        setCurrentLifecycle(getNextLifecycle());
    }

    public Lifecycle getNextLifecycle() {
        int index = indexOf(getCurrentLifecycle());

        if (index >= lifecycles.length) {
            return getCurrentLifecycle();
        }

        return lifecycles[index + 1];
    }

    private int indexOf(Lifecycle lifecycle) {
        int index = ArrayUtil.indexOf(lifecycles, lifecycle);
        if (index == ArrayUtil.INDEX_NOT_FOUND) {
            throw new IllegalStateException("Lifecycle " + lifecycle + " not in this timeline!");
        }
        return index;
    }

    public Lifecycle getCurrentLifecycle() {
        return currentLifecycle;
    }
}
