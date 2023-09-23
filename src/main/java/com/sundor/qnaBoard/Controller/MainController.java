package com.sundor.qnaBoard.Controller;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	
	private int increaseNo = -1;
	
	@RequestMapping("/sbb")
	// 아래 함수의 리턴값을 그대로 부라우저에 표시
	// 아래 함수의 리턴값을 문자열화 해서 브라우저 응답을 바디에 담는다.
	@ResponseBody
	public String index() {
		System.out.println("첫시작");
		return "첫시작 안녕하세요!!!";
	}

	@GetMapping("/page1")
	@ResponseBody
	public String showGet() {
		System.out.println("...showGet()");
		return """
				<h1>안녕하세요.</h1>
				<form method="POST" action="/page2">
					<input type="number" name="age" placeholder="나이입력" />
					<input type="submit" value="page2로 Post방식으로 이동" />				
				</form>
				""";
	}
	
	@GetMapping("/page2")
	@ResponseBody
	public String showPost() {
		System.out.println("...showPost()");
		return """
				<h1>안녕하세요. Get 방식으로 오신걸 환영합니다.</h1>				
				""";
	}
	
	@PostMapping("/page2")
	@ResponseBody
	public String showPage2Post(@RequestParam(defaultValue="0") int age) {
		System.out.println("...showPage2Post()");
		return """
				<h1>입력된 나이 : %d </h1>
				<h1>안녕하세요. Post 방식으로 오신걸 환영합니다.</h1>				
				""".formatted(age);
	}
	
	// http://localhost:8070/plus?a=1&b=5
	@GetMapping("/plus")
	@ResponseBody
	public String showPlus(@RequestParam(defaultValue="0") int a, @RequestParam(defaultValue="0") int b) {
		return ""+(a+b);
	}
	
	// http://localhost:8070/minus?a=1&b=5	
	@GetMapping("/minus")
	@ResponseBody
	public int showMinus(@RequestParam(defaultValue="0") int a, @RequestParam(defaultValue="0") int b) {
		return a-b;
	}
	
	// http://localhost:8070/increase
	@GetMapping("/increase")
	@ResponseBody
	public int showIncrease() {
		increaseNo ++;
		return increaseNo;
	}
	
	// http://localhost:8070/gugudan?dan=3&limit=5
	@GetMapping("/gugudan")
	@ResponseBody
	public String showGugudan(int dan, int limit) {
		
		String rs = "";
		for(int i=1; i<=limit; i++) {
			rs += "%d * %d = %d <br>".formatted(dan, i, dan*i);
		}
		return rs;
	}
	
	// http://localhost:8070/gugudan?dan=3&limit=5
		@GetMapping("/gugudan2")
		@ResponseBody
		public String showGugudan2(Integer dan, Integer limit) {
			if(dan == null) {
				dan = 9;
			}
			if(limit == null) {
				dan = 9;
			}
			final Integer finalDan = dan;
			return IntStream.rangeClosed(1, limit).mapToObj(i->"%d * %d = %d".formatted(finalDan,i,finalDan*i)).collect(Collectors.joining("<br>"));
		}
}

