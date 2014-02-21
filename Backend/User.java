import java.util.HashMap;


public class User {
	HashMap<String, Short> cities;
	String[] categories;
	String[] keywords;
	
	public User(HashMap<String, Short> c, String[] cat, String[] key){
		cities = c;
		categories = cat;
		keywords = key;
	}
	
	public String getCats(){
		StringBuffer buff = new StringBuffer();
		for(int i = 0; i < categories.length; i++){
			buff.append(categories[i]);
			if(i != categories.length - 1){
				buff.append(",");
			}
		}
		return buff.toString();
	}
	public String getKeys(){
		StringBuffer buff = new StringBuffer();
		for(int i = 0; i < keywords.length; i++){
			buff.append(keywords[i]);
			if(i != keywords.length - 1){
				buff.append(",");
			}
		}
		return buff.toString();
	}
}
