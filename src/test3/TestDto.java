
package test3;        
@Table("tb_test")  
public class TestDto {  
      
    @Deprecated  
    private String tt;  
      
    @Column("_id")  
    private String id;  
      
    @Column("username")  
    private String name;  
    
    private String test;
      
    public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public TestDto(String id, String name) {  
        super();  
        this.id = id;  
        this.name = name;  
    }  
  
    public String getId() {  
        return id;  
    }  
  
    public void setId(String id) {  
        this.id = id;  
    }  
  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }  
      
      
}  