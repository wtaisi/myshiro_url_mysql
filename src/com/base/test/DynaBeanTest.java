package com.base.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;

public class DynaBeanTest {
	public static void main(String[] args)throws Exception {
		//定义动态属性  
        DynaProperty[] props = new DynaProperty[]{  
                new DynaProperty("username", String.class),  
                new DynaProperty("address", java.util.Map.class)  
        };  
        //动态类  
        BasicDynaClass dynaClass = new BasicDynaClass("person", null, props);  
        //动态bean  
        DynaBean person = dynaClass.newInstance();  
        person.set("username", "jhlishero");//设置值  
        Map<String, String> maps = new HashMap<String, String>();  
        maps.put("key1", "value1");  
        maps.put("key2", "value2");  
        person.set("address",maps);//设置值  
        person.set("address", "key3", "value3");//第二种方法设置map中的值  
          
        System.err.println(person.get("username"));//获取字符串值  
        System.err.println(person.get("address", "key1"));//获取map中值  
        System.err.println(person.get("address", "key2"));  
        System.err.println(person.get("address", "key3"));  
        //使用PropertyUtils工具获取属性值  
        System.out.println(PropertyUtils.getSimpleProperty(person, "username"));  
	}

}
