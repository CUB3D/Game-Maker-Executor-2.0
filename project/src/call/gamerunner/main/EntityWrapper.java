package call.gamerunner.main;

import call.game.entitys.BaseEntity;

public class EntityWrapper
{
	private BaseEntity sprite;
	private boolean prefab;
	private String name;
	private String tag;
	
	public EntityWrapper(BaseEntity s, boolean prefab, String name, String tag)
	{
		this.sprite = s;
		this.prefab = prefab;
		this.name = name;
		this.tag = tag;
	}
	
	public BaseEntity getEntity()
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
