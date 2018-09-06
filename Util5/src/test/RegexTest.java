package test;

import org.junit.Test;

/**	https://blog.csdn.net/z69183787/article/details/54020551
贪婪量词：	整个-> 0	
先看整个字符串是不是一个匹配。如果没有发现匹配，它去掉最后字符串中的最后一个字符，并再次尝试。如果还是没有发现匹配，那么    再次去掉最后一个字符串，这个过程会一直重复直到发现一个匹配或者字符串不剩任何字符。简单量词都是贪婪量词。
惰性量词：1 -> 整个
先看字符串中的第一个字母是不是一个匹配，如果单独着一个字符还不够，就读入下一个字符，组成两个字符的字符串。如果还没有发现匹配，惰性量词继续从字符串中添加字符直到发现一个匹配或者整个字符串都检查过也没有匹配。惰性量词和贪婪量词的工作方式恰好相反。
支配量词：
只尝试匹配整个字符串。如果整个字符串不能产生匹配，不做进一步尝试。
    贪婪量词  		 惰性量词    		支配量词        		              描述
    -------------------------------------------------------------------------------------
      ?             ??             ?+                      可以出现0次或1次，但至多出现1次
      *             *?            *+                      可以出现任意次，也可以不出现
      +             +?            ++                      出现1次或多次，但至少出现1次
      {n}        {n}?           {n}+                   一定出现n次
      {n,m}    {n,m}?       {n,m}+               至少出现n次，但至多不能超过m次
      {n,}       {n,}?          {n,}+                 可以出现任意次，但至少出现n次
      
 * https://blog.csdn.net/hz_lizx/article/details/55211104
零宽断言
(exp)匹配exp,并捕获文本到自动命名的组里
(?<name>exp) 匹配exp,并捕获文本到名称为name的组里
(?:exp) 匹配exp,不捕获匹配的文本
位置指定
(?=exp) 匹配exp前面的位置
(?<=exp) 匹配exp后面的位置
(?!exp) 匹配后面跟的不是exp的位置
(?<!exp) 匹配前面不是exp的位置
注释
(?#comment) 这种类型的组不对正则表达式的处理产生任何影响，只是为了提供让人阅读注释
 * @author wlh
 *
 */
public class RegexTest {
	@Test
	public void last() {
		String s = "util.base._StringBuffer.GetAppoint";
		int i = s.lastIndexOf('.');
		System.out.println(s.substring(0, i));;
		System.out.println(s.substring(i+1));;
		System.out.println("=======targe=====");
		print( s.split("[.](?:.*)$"));
		s.lastIndexOf(".");
	}
	private void print(String array []) {
		for (String string : array) {
			System.out.println(string);
		}
	}
}
