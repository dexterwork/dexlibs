import java.util.TreeMap;



public class TestCode {
	
    

	public TestCode() {
	}

	public static void main(String[] args) {
		
		
		new DateTest();
		
		
	}
	
	
	private static void checkValue(){
		CheckValue cv=new CheckValue();
		cv.showCheckValue("1433845901","50","201403010109");

	}
	
	private static void tree(){
		TreeMap<String,String> tree=new TreeMap<String,String>();
		tree.put("2", "B");
		tree.put("1", "A");
		tree.put("4", "D");
		tree.put("3", "C");
		for(String s:tree.values()){
			System.out.println(s);
		}
		
	}
	 

}
