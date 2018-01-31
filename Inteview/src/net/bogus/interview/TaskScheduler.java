package net.bogus.interview;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class TaskScheduler {

	public static void main(String[] args) {
		List<Task> list = new ArrayList<>();
		list.add(new Task("A", "", 5));
		list.add(new Task("B", "C", 7));
		list.add(new Task("C", "", 2));
		list.add(new Task("D", "B", 3));
		list.add(new Task("E", "C", 8));
		
		System.out.println(process(list));
		
		
		list = new ArrayList<>();
		list.add(new Task("A", "", 1));
		list.add(new Task("B", "", 2));
		list.add(new Task("C", "", 3));
		list.add(new Task("D", "", 4));
		
		System.out.println(process(list));
	}
	
	public static String process(List<Task> tasks) {
		StringBuffer output = new StringBuffer();
		
		HashMap<Task, List<Task>> map = new HashMap<Task, List<Task>>();
		PriorityQueue<Task> pq = new PriorityQueue<>(new Comparator<Task>() {

			@Override
			public int compare(Task o1, Task o2) {
				// TODO Auto-generated method stub
				return o2.priority - o1.priority;
			}
			
		});
		
		// First identify the first level tasks with no dependency and identify tasks depending first tier those
		for (Task t : tasks) {
			map.put(t, new ArrayList<>());
			if (t.dependencyName.length() == 0) {
				pq.offer(t);
			} 
		}
		
		for (Task t : tasks) {
			if (t.dependencyName.length() == 0) {
				continue;
			}
			List<Task> taskList = map.get(new Task(t.dependencyName, "", 0));
			if (taskList != null) {
				taskList.add(t);
			}
		}
		
		while (!pq.isEmpty()) {
			Task t = pq.poll();
			output.append(t.name);
			List<Task> taskList = map.get(t);
			for (Task t2 : taskList) {
				pq.offer(t2);
			}
		}
		
		return output.toString();
	}
	
	private static class Task {
		String name;
		String dependencyName;
		int priority;
		
		public Task(String name, String dependencyName, int priority) {
			super();
			this.name = name;
			this.dependencyName = dependencyName;
			this.priority = priority;
		}

		@Override
		public int hashCode() {
			return name.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			if (obj != null && obj instanceof Task) {
				Task t  = (Task) obj;
				return name.equals(t.name);
			}
			
			return false;
		}
		
		
		
	}

}
