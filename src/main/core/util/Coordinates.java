package main.core.util;

import javafx.util.Pair;

public class Coordinates extends Pair<Integer, Integer> implements Comparable<Pair<Integer, Integer>> {

	public Coordinates(Integer key, Integer value) {
		super(key, value);
	}

	@Override
	public int compareTo(Pair<Integer, Integer> o) {
		if (this.getKey() < o.getKey()) {
			return -1;
		} else if (this.getKey() > o.getKey()) {
			return 1;
		} else {
			if (this.getValue() < o.getValue()) {
				return -1;
			} else if (this.getValue() > o.getValue()) {
				return 1;
			} else {
				return 0;
			}
		}
	}

}
