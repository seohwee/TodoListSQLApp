package com.todo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList l) {
		int id = 1;
		String title, desc, category, due_date;
		//int num = list.getList().size()+1;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 추가]\n" + "제목 > ");
		title = sc.next();
		//if (l.isDuplicate(title)) {
		//	System.out.println("제목이 중복됩니다!");
		//	return;
		//}
	
		
		System.out.print("카테고리 > ");
		category = sc.next();
		
		//if(l.isDuplicate(category)) {
		//	System.out.println("카테고리가 중복됩니다!");
		//	return;
		//}	
		sc.nextLine();
		
		System.out.print("내용 > ");
		desc=sc.nextLine().trim();
		System.out.print("마감일자 > ");
		due_date = sc.nextLine().trim();
		
		//TodoItem t = new TodoItem(num, category, title, due_date, desc);
		TodoItem t = new TodoItem(id, title, desc, category, due_date);
		if(l.addItem(t)>0) { 
			System.out.println("추가되었습니다.");
		}	
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("[항목 삭제]\n" + "삭제할 항목의 번호를 입력하시오 > ");
		int index = sc.nextInt();
		if(l.deleteItem(index)>0) {
			System.out.println("삭제되었습니다.");
		}
	}

	public static void updateItem(TodoList l) {
		int id=1;
		String new_title, new_desc, new_category, new_due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 수정]\n" + "수정할 항목의 번호를 입력하시오 > ");
		int index = sc.nextInt();
		
		System.out.print("새 제목 > ");
		new_title = sc.next().trim();
		System.out.print("새 카테고리 > ");
		new_category = sc.next();
		sc.nextLine();
		System.out.print("새 내용 > ");
		new_desc = sc.nextLine().trim();
		System.out.print("새 마감일자 > ");
		new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(id, new_title, new_desc, new_category, new_due_date);
		t.setId(index);
		if(l.updateItem(t) > 0) {
			System.out.println("수정되었습니다.");
		}
	}
	
	public static void findItem(TodoList l, String key) {
		
		Scanner sc = new Scanner(System.in);
		int count=0;

		for (TodoItem item : l.getList()) {
			if(item.toString().contains(key)) {
				System.out.println((l.getList().indexOf(item)+1)+". "+item.toString());
				count++;
			}
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	
	
	public static void findCate(TodoList l, String key) {
		
		Scanner sc = new Scanner(System.in);
		int count=0;
		String category;

		for (TodoItem cate : l.getList()) {
			if(cate.toString().contains(key)) {
				System.out.println((l.getList().indexOf(cate)+1)+". "+cate.toString());
				count++;
			}
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	
	
	public static void listCate(TodoList l) {

		HashSet<String> cateList = new HashSet<String>();

		for (TodoItem cate : l.getList()) {
			cateList.add(cate.getCategory().trim());
		}
		
		Iterator<String> it = cateList.iterator();

		while(it.hasNext()) {
			String s = (String)it.next();
			System.out.print(s);
			if(it.hasNext()) {
				System.out.print(" / ");
			}
		}
		System.out.println("\n총 "+cateList.size()+"개의 카테고리가 등록되어 있습니다.");
	}
	

	public static void listAll(TodoList l) {
		int count=0;
		int num=1;
		
		for(TodoItem cnt : l.getList()) {
			count++;
		}

		System.out.println("[전체 목록, 총 "+count+"개]");
		for (TodoItem item : l.getList()) {
			System.out.println(num+". "+item.toString());
			num++;
		}
	}

	public static void loadList(TodoList l, String filename) {
		int count=0;
		//File f = new File(filename);

		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));

				String oneline;
				while((oneline = br.readLine()) != null) {
					StringTokenizer st = new StringTokenizer(oneline, "##");
					String title = st.nextToken();
					String desc = st.nextToken();
					
				//	TodoItem t = new TodoItem(title, desc);
				//	l.addItem(t);
					count++;
				}
				System.out.printf("%d개의 항목을 읽었습니다.\n", count);

			br.close();
		} catch (FileNotFoundException e) {
				System.out.println("todolist.txt 파일이 없습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public static void saveList(TodoList l, String filename) {
		try {
			Writer w = new FileWriter("todolist.txt");
			
			for (TodoItem item : l.getList())  {
				w.write(item.toSaveString());
			}
			w.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		System.out.print("저장되었습니다.");
	}

	public static void findList(TodoList l, String keyword) {
		int count=0;
		for(TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n", count);
	}

	public static void listCateAll(TodoList l) {
		int count=0;
		for(String item : l.getCategories()) {
			System.out.print(item+" ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", count);
	}

	public static void findCateList(TodoList l, String cate) {
		int count=0;
		for(TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다.\n", count);
	}

	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for(TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}

}