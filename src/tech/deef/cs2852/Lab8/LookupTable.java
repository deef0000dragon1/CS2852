/*
 * @Author Jeffrey Koehler
 * @date 5/10/2016
 * @purpose Data Structures Lab 8
 * cs2852 -051
 * 
 * */

package tech.deef.cs2852.Lab8;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LookupTable<E> implements Map {
	private List<E> keys;
	private List<E> values;

	public LookupTable() {
		keys = new LinkedList();
		values = new LinkedList();
	}

	@Override
	public void clear() {
		values.clear();
		keys.clear();
	}

	@Override
	public boolean containsKey(Object value) {
		if (keys.contains(value + "")) {
			return true;
		}
		return false;
	}

	@Override
	public Object get(Object key) {
		int index = keys.indexOf(key + "");
		return values.get(index);
	}

	@Override
	public boolean isEmpty() {

		return keys.isEmpty();
	}

	@Override
	public Object put(Object key, Object value) {
		// TODO Auto-generated method stub

		if (keys.contains(key)) {
			int index = keys.indexOf(key);
			Object o = keys.get(index);
			keys.set(index, (E) key);
			values.set(index, (E) value);

		}

		if (keys.size() == 0) {// starter
			keys.add(0, (E) key);
			values.add(0, (E) value);
		} else if (key.toString().charAt(0) > (keys.get(0)).toString().charAt(0)) {
			keys.add(0, (E) key);// largest at beginning
			values.add(0, (E) value);
		} else if (key.toString().charAt(0) < (keys.get(keys.size() - 1)).toString().charAt(0)) {
			keys.add((E) key); // smallest at end
			values.add((E) value);
		} else {
			for (int i = 0; i <= keys.size() - 1; i++) {
				System.out.println(key.toString().charAt(0) + "::" + (keys.get(i)).toString().charAt(0));
				System.out
						.println(((int) key.toString().charAt(0)) + "::" + ((int) (keys.get(i)).toString().charAt(0)));

				if (key.toString().charAt(0) > (keys.get(i)).toString().charAt(0)) {
					keys.add(i, (E) key);
					values.add(i, (E) value);
					break;
				}
			}
		}
		
		return null;
	}

	@Override
	public Object remove(Object key) {
		int index = keys.indexOf(key);
		Object object = values.get(index);
		values.remove(index);
		keys.remove(index);
		return object;
	}

	@Override
	public int size() {

		return keys.size();
	}

	@Override
	public boolean containsValue(Object arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set entrySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Set keySet() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putAll(Map arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection values() {
		throw new UnsupportedOperationException();
	}

}
