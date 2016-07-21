/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
		if(list.size() <= 1) {
			return list;
		}
		int middle = list.size()/2;
		List<T> leftList = list.subList(0, middle);
		List<T> rightList = list.subList(middle, list.size());

		//Rucursively mergesort
		leftList = mergeSort(leftList, comparator);
		rightList = mergeSort(rightList, comparator);

		//merge
		List<T>mergedList = mergeSortedLists(leftList, rightList, comparator);
        return mergedList;
	}

	/**
	 * Merges two sorted lists into one sorted list
	 * @param leftList
	 * @param rightList
	 * @param comparator
	 */
	private List<T> mergeSortedLists(List<T> leftList, List<T> rightList, Comparator<T> comparator) {
		int leftIndex = 0;
		int rightIndex = 0;

		List<T> mergedList = new ArrayList<T>();
		while(leftIndex < leftList.size() || rightIndex < rightList.size()) {
			if(leftIndex < leftList.size() && rightIndex < rightList.size()) {
				T leftElem = leftList.get(leftIndex);
				T rightElem =  rightList.get(rightIndex);
				if(comparator.compare(leftElem, rightElem) > 0) {
					mergedList.add(rightElem);
					rightIndex++;
				} else {
					mergedList.add(leftElem);
					leftIndex++;
				}
			} else if(leftIndex < leftList.size()) {
				T leftElem = leftList.get(leftIndex);
				mergedList.add(leftElem);
				leftIndex++;
			} else {
				T rightElem =  rightList.get(rightIndex);
				mergedList.add(rightElem);
				rightIndex++;
			}

		}
		return mergedList;
	}


	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
        PriorityQueue<T> pq = new PriorityQueue<T>(11, comparator);
        for(T element: list) {
        	pq.offer(element);
        }

        list.clear();
        while(pq.size() > 0) {
        	list.add(pq.poll());
        }
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
        PriorityQueue<T> pq = new PriorityQueue<T>(k, comparator);
        for(T element: list) {
        	if(k != pq.size()) {
  				pq.offer(element);
        	} else {
        		//pq is full
        		T smallest = pq.peek();
        		if(comparator.compare(element, smallest) > 0) {
        			pq.poll();
        			pq.offer(element);
        		}

        	}
        }

        List<T> sortedList =  new ArrayList<T>();
        while(pq.size() > 0) {
        	sortedList.add(pq.poll());
        }
        return sortedList;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
