package com.sundor.qnaBoard.Controller;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;

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
	public String showPage2Post(@RequestParam(defaultValue = "0") int age) {
		System.out.println("...showPage2Post()");
		return String.format("""
				<h1>입력된 나이 : %d </h1>
				<h1>안녕하세요. Post 방식으로 오신걸 환영합니다.</h1>
				""", age);

//		return """
//				<h1>입력된 나이 : %d </h1>
//				<h1>안녕하세요. Post 방식으로 오신걸 환영합니다.</h1>
//				""".formatted(age);
	}

	// http://localhost:8070/plus?a=1&b=5
	@GetMapping("/plus")
	@ResponseBody
	public String showPlus(@RequestParam(defaultValue = "0") int a, @RequestParam(defaultValue = "0") int b) {
		return "" + (a + b);
	}

	// http://localhost:8070/plus2?a=1&b=5
	@GetMapping("/plus2")
	@ResponseBody
	public void showPlus2(HttpServletRequest req, HttpServletResponse res) throws IOException {
		int a = Integer.parseInt(req.getParameter("a"));
		int b = Integer.parseInt(req.getParameter("b"));

		res.getWriter().append((a + b) + "");
	}

	// http://localhost:8070/minus?a=1&b=5
	@GetMapping("/minus")
	@ResponseBody
	public int showMinus(@RequestParam(defaultValue = "0") int a, @RequestParam(defaultValue = "0") int b) {
		return a - b;
	}

	// http://localhost:8070/increase
	@GetMapping("/increase")
	@ResponseBody
	public int showIncrease() {
		increaseNo++;
		return increaseNo;
	}

	// http://localhost:8070/gugudan?dan=3&limit=5
	@GetMapping("/gugudan")
	@ResponseBody
	public String showGugudan(int dan, int limit) {

		String rs = "";
		for (int i = 1; i <= limit; i++) {
			rs += String.format("%d * %d = %d <br>", dan, i, dan * i);
			// rs += "%d * %d = %d <br>".formatted(dan, i, dan * i);
		}
		return rs;
	}

	// http://localhost:8070/gugudan2?dan=3&limit=5
	@GetMapping("/gugudan2")
	@ResponseBody
	public String showGugudan2(Integer dan, Integer limit) {
		if (dan == null) {
			dan = 9;
		}
		if (limit == null) {
			limit = 9;
		}
		Integer finalDan = dan;
		return IntStream.rangeClosed(1, limit).mapToObj(i -> String.format("%d * %d = %d", finalDan, i, finalDan * i))
				.collect(Collectors.joining("<br>"));
	}

	@GetMapping("/mbti/{name}")
	@ResponseBody
	public String showMbti(@PathVariable String name) {
		String rs = switch (name) {
		case "홍길동" -> "INFP";
		case "홍길순" -> "ENFP";
		case "임꺽정" -> "ESFJ";
		case "sundor" -> "ISTJ";
		default -> "모름";
		};

		return rs + ", " + name;
	}

	@GetMapping("/mbti2/{name}")
	@ResponseBody
	public String showMbti2(@PathVariable String name) {
		return switch (name) {
		case "홍길순" -> {
			char j = 'J';
			yield "ENF" + j;
		}
		case "임꺽정" -> "ESFJ";
		case "sundor" -> "ISTJ";
		case "홍길동", "바다미" -> "INFP";
		default -> "모름";
		};
	}

	// 세션에 정보 저장하기
	// http://localhost:8070/saveSession/sundor/10
	@GetMapping("/saveSession/{name}/{value}")
	@ResponseBody
	public String SaveSession(@PathVariable String name, @PathVariable String value, HttpSession session) {
		System.out.println(name);
		System.out.println(value);

		session.setAttribute(name, value);

		// return "세션변수 %s의 값이 %s(으)로 설정되었습니다.".formatted(name, value); //Java SE 15
		// (JDK 15) [since 2020].
		return String.format("세션변수 %s의 값이 %s(으)로 설정되었습니다.", name, value);
	}

	// http://localhost:8070/getSession/sundor
	// 세션 얻기
	@GetMapping("/getSession/{name}")
	@ResponseBody
	public String getSession(@PathVariable String name, HttpSession session) {
		// req => 쿠키 => JSESSIONID => 세션을 얻을 수 있다.
		String value = (String) session.getAttribute(name);

		return String.format("세션변수 %s의 값은 %s입니다.", name, value);

	}

	// http://localhost:8070/addArticle?title=test&body=it's testing
	@GetMapping("/addArticle")
	@ResponseBody
	public String addArticle(String title, String body) {
		System.out.println("title : " + title);
		System.out.println("body : " + body);
		int id = 1;
		Article article = new Article(id, title, body);

		System.out.println("article" + article);
		return String.format("%d번 게시물이 생성되었습니다.", id);
	}

	@Data
	@AllArgsConstructor
	class Article {
		private int id;
		private String title;
		private String body;
	}
}
