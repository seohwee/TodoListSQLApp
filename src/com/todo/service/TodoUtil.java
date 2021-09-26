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
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		String category;
		String due_date;
		int num = list.getList().size()+1;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 추가]\n" + "항목 > ");
		category = sc.next();
		if(list.isDuplicate(category)) {
			System.out.println("카테고리가 중복됩니다!");
			return;
		}
		sc.nextLine();
		
		
		System.out.print("제목 > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.println("제목이 중복됩니다!");
			return;
		}
		sc.nextLine();
		
		System.out.print("내용 > ");
		desc = sc.nextLine().trim();
		
		System.out.print("마감일자 > ");
		due_date = sc.next();
		
		TodoItem t = new TodoItem(num, category, title, due_date, desc);
		list.addItem(t);
		System.out.println("추가되었습니다.");
		
		

		
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 삭제]\n" + "삭제할 항목의 번호를 입력하시오 > ");
		
		int num = sc.nextInt();

		for (TodoItem item : l.getList()) {
			if (item.getNum() == num) {
				System.out.println(num+". "+item.toString());
				System.out.print("위 항목을 삭제하시겠습니까? (y/n) > ");
				String answer = sc.next();
				if(answer.equals("y")) {
					l.deleteItem(item);
					System.out.println("삭제되었습니다.");
					break;
				}

			}
		}
	}

	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목 수정]\n" + "수정할 항목의 번호를 입력하시오 > ");
		int num = sc.nextInt();
		
	//	for (TodoItem check : l.getList()) {
	//		if(check.getNum() != num) {
	//			System.out.println("없는 제목입니다!");
	//			return;
	//		}
	//	}
		
		for (TodoItem item : l.getList()) {
			if (item.getNum() == num) {
				System.out.println(num+". "+item.toString());
			}
		}

		System.out.print("새 제목 > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복됩니다!");
			return;
		}
		sc.nextLine();
		
		System.out.print("새 카테고리 > ");
		String new_category = sc.next().trim();
		
		System.out.print("새 내용 > ");
		String new_description = sc.nextLine();
		
		System.out.print("새 마감일자 > ");
		String new_dueDate = sc.nextLine();
		
		for (TodoItem item : l.getList()) {
			if(item.getNum() == num) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(num, new_title, new_category, new_dueDate, new_description);
				l.addItem(t);
				System.out.println("수정되었습니다.");
			}
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

}
