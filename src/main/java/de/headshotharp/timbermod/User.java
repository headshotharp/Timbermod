package de.headshotharp.timbermod;

public class User
{
	private String name;
	private boolean timber = true;

	public User(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean hasTimber()
	{
		return timber;
	}

	public void setTimber(boolean timber)
	{
		this.timber = timber;
	}

}
