package call.gamerunner.main;

import call.game.image.Sprite;

public class SpriteWrapper
{
	private Sprite sprite;
	private boolean prefab;
	private String name;
	private String tag;
	
	public SpriteWrapper(Sprite s, boolean prefab, String name, String tag)
	{
		this.sprite = s;
		this.prefab = prefab;
		this.name = name;
		this.tag = tag;
	}
	
	public Sprite getSprite()
	{
		return sprite;
	}
	
	public boolean isPrefab()
	{
		return prefab;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getTag()
	{
		return tag;
	}
}
