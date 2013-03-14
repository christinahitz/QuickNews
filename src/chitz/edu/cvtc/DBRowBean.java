package chitz.edu.cvtc;

/**
 * Contains setters and getters for attaching the id, name, and url to each dynamically created
 * button.
 * Allows this information to be used throughout the rest of the application
 * 
 * @author Christina Hitz
 */

public class DBRowBean {

	String id;
	String name;
	String url;

	/**
	 * The id, name, url of each saved webiste is passed in via
	 * QuickNewsActivity here
	 * 
	 * @param s0
	 * @param s1
	 * @param s2
	 */
	public DBRowBean(String s0, String s1, String s2) {
		this.id = s0;
		this.name = s1;
		this.url = s2;
	}

	/**
	 * Getter for ID
	 * 
	 * @return
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter for ID
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Trims the length of a website's name if it is longer than 6 characters to
	 * no more than 6 characters. Appends "..." to trimmed names.
	 * 
	 * @return
	 */
	public String getName() {
		int len = name.length();
		if (len > 7) {
			return (name.substring(0, 6) + "...");
		}
		return name;

	}

	/**
	 * Set name
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for url
	 * 
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Set url
	 * 
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
