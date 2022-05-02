package com.apps.main;

import java.util.List;
import java.util.Scanner;

import com.apps.dao.UserDAO;
import com.apps.dto.UserDTO;

public class Main {

	public static void main(String[] args) {

		UserDTO ud = null;
		UserDAO ua = null;
		String menu = "";
		boolean isFlag = true;

		try (Scanner sc = new Scanner(System.in)) {

			while (isFlag) {
				System.out.println("=========================메뉴=========================");
				System.out.println("1. 가입");
				System.out.println("2. 사용자정보");
				System.out.println("3. 종료");
				System.out.print("======================================================\n> ");
				menu = sc.next();
				sc.nextLine();

				if (menu.equals("1")) {
					ud = new UserDTO();
					ua = new UserDAO();

					System.out.print("아이디 입력: ");
					ud.setUserId(sc.nextLine());
					System.out.print("비밀번호 입력: ");
					ud.setPassword(sc.nextLine());
					System.out.print("이름 입력: ");
					ud.setName(sc.nextLine());

					int executeRtn = ua.register(ud);

					if (executeRtn == 1) {
						System.out.println("가입완료!\n");
					} else {
						System.out.println("가입실패\n");
					}

				} else if (menu.equals("2")) {

					ua = new UserDAO();
					List<UserDTO> aList = ua.listUser();
					int counter = 1;
					System.out.println("\n======================사용자정보======================\n");
					for (UserDTO l : aList) {
						System.out.println(counter++ + ") 아이디:" + l.getUserId() + "\t\t비밀번호:" + l.getPassword() + "\t\t이름:" + l.getName());
					}
					System.out.println("\n======================================================\n");

				} else if (menu.equals("3")) {
					System.out.println("\n종료합니다");
					isFlag = false;
					System.exit(0);
				} else {
					System.out.println("잘못 눌렀습니다 메뉴를 다시 선택하세요");
				}
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}
}
