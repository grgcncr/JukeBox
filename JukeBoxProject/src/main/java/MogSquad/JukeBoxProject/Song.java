package MogSquad.JukeBoxProject;

public class Song{
	private String name;
	private String path;
	
	

	public Song(String name, String path) {
		super();
		this.name = name;
		this.path = path;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getPath() {
		return path;
	}



	public void setPath(String path) {
		this.path = path;
	}



	@Override
	public String toString() {
		return name;
	}

	

}