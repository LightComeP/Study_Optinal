package junit4;





import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
//Junit4 의 필드의 값이 반복 선언되는 이유는?
public class Optional_test1 {
	String nullTestString = null;	
	String notNullTestString = "text";
	
	//일반적인 널포인트 발생 상황
	@Test(expected = NullPointerException.class)
	public void testOldStyleNPECatch() {
		nullTestString.charAt(0);
	}
	
	//일반적인 널 검토
	@Test
	public void testNullCheck() {
		if(notNullTestString != null) {
			assertNotNull(notNullTestString);
		}
	}
	
	//Optional을 사용한 널 검토
	@Test
	public void testNullCheckbyOptinal() {
		Optional<String> optStr = Optional.ofNullable(notNullTestString);
		optStr.ifPresent(str -> assertEquals(str, notNullTestString));
	}
	
	//Optional.ofNullBale에 null을 넣었다면?
	@Test
	public void testOptionalNull() {
		Optional<String> optStr = Optional.ofNullable(nullTestString);
		assertEquals(optStr, Optional.empty());
	}
	//Optinal .ifPresent함수 사용법
	@Test
	public void testOptionalIfPresent() {
		//널이 아닌 값을 넣었다면.
		Optional<String> optNotNull = Optional.ofNullable(notNullTestString);
		//람다를 이용하여 값을 꺼내 사용할수 있다.
		optNotNull.ifPresent(str -> {
			assertEquals(notNullTestString, str);
		});
		//널을 넣었다면.
		Optional<String> optNull = Optional.ofNullable(nullTestString);
		//람다 식은 인터페이스가 구현되어있어 사용 가능하겠으나 꺼낼 값이 없어 사용할수 없다.
		optNull.ifPresent(str -> System.out.println("null일때는 비어있기때문에 가져올것도 없다 실행될것도 없다."));
	}
	
	@Test
	public void testOptionalorElse() {
		Optional<String> optStrNotNull = Optional.ofNullable(notNullTestString);
		//값이 있다면 넣은 값을.
		assertEquals(optStrNotNull.orElse("isNull"), notNullTestString);
		
		Optional<String> optStrNull = Optional.ofNullable(nullTestString);
		//값이 없다면 내가 지정한 값을
		assertEquals(optStrNull.orElse("isNull"), "isNull");
	}
	//예시 메소드
	public String returnString() {
		System.out.println("널입니다.");
		return "text";
	}
	//.orElseGet 전달하는 메소드는 실행이 되지 않는다.
	@Test
	public void testOptinoalElseDefrenceThing() {
		Optional<String> optNull = Optional.ofNullable(notNullTestString);
		assertEquals(optNull.orElseGet(this::returnString), notNullTestString);
	}
	//.orElse 로 던진 메소드는 실행이 된다.
	@Test
	public void testOptinoalElseDefrenceThing2() {
		Optional<String> optNull = Optional.ofNullable(notNullTestString);
		assertEquals(optNull.orElse(returnString()), notNullTestString);
	}
}
