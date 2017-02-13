package test3;        
import java.lang.reflect.Field;  
import java.lang.reflect.Method;  
      
public class Test {  
    public static void main(String[] args) {  
        TestDto testDto = new TestDto("123", "34");  
        TestDto testDto1 = new TestDto("123", "test1");  
        TestDto testDto2 = new TestDto("", "test1,test2,test3,test4");  
        String sql = assembleSqlFromObj(testDto);  
        String sql1 = assembleSqlFromObj(testDto1);  
        String sql2 = assembleSqlFromObj(testDto2);  
        System.out.println(sql);  
        System.out.println(sql1);  
        System.out.println(sql2);  
    }  
      
        /** 
         * 通过注解来组装查询条件，生成查询语句 
         *  
         * @param obj 
         * @return 
         */  
    public static String assembleSqlFromObj(Object obj) {  
    	//通过对象，获取该对象的类的相关信息（类的相关信息包括：①用了什么注解 ②类的名称 ③各成员变量的名称等。本例中是通过对象testDto、testDto1、testDto2获取类的名为“Table”的注解），这里就用到了java的反射机制。
        Table table = obj.getClass().getAnnotation(Table.class); 
        StringBuffer sbSql = new StringBuffer();  
        String tableName = table.value();  //获取成员变量“value”
        sbSql.append("select * from " + tableName + " where 1=1 ");  
        Field[] fileds = obj.getClass().getDeclaredFields();  
        for (Field f : fileds) {  
            String fieldName = f.getName();  
            String methodName = "get" + fieldName.substring(0, 1).toUpperCase()  
                    + fieldName.substring(1);  

            try {  
                Column column = f.getAnnotation(Column.class);  
                if (column != null) {  
                    Method method = obj.getClass().getMethod(methodName);  
                    String value = (String) method.invoke(obj);  
                    if (value != null && !value.equals("")) {  
                        if (!isNum(column.value()) && !isNum(value)) {  
                            // 判断参数是不是 in 类型参数 1,2,3  
                            if (value.contains(",")) {  
                                sbSql.append(" and " + column.value() + " in (" + value + ") ");  
                            } else {  
                                sbSql.append(" and " + column.value() + " like '%" + value + "%' ");  
                            }  
                        } else {  
                            sbSql.append(" and " + column.value() + "=" + value + " ");  
                        }  
                    }  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return sbSql.toString();  
    }  
      
        /** 
         * 检查给定的值是不是 id 类型 1.检查字段名称 2.检查字段值 
         *  
         * @param target 
         * @return 
         */  
	public static boolean isNum(String target) {  
	    boolean isNum = false;  
	    if (target.toLowerCase().contains("id")) {  
	        isNum = true;  
	    }  
	    if (target.matches("\\d+")) {  
	        isNum = true;  
	    }  
	    return isNum;  
	}  
}  