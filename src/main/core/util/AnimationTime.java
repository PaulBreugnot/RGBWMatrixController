package main.core.util;

import javafx.util.Pair;

public class AnimationTime extends Pair<Integer, Integer> implements Comparable<Pair<Integer, Integer>> {

	public AnimationTime(Integer beginTime, Integer Duration) {
		super(beginTime, Duration);
	}

	@Override
	public int compareTo(Pair<Integer, Integer> o) {
		if (this.getKey() < o.getKey()) {
			return -1;
		} else if (this.getKey() > o.getKey()) {
			return 1;
		} else {
			return 0;
		}
	}
}
