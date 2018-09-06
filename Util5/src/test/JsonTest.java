package test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Test;

public class JsonTest {
	@Test
	public void jsonObject() throws ScriptException {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("javascript");
		Double d = (Double) engine.eval("var a = 5;");
		System.out.println(d);
	}
}
