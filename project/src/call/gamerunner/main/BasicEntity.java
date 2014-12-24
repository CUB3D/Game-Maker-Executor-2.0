package call.gamerunner.main;

import call.game.entitys.HealthEntity;
import call.game.image.BaseSprite;

public class BasicEntity extends HealthEntity
{
	private String id;
	
	public BasicEntity(BaseSprite bs, int health, String id)
	{
		super(health, bs);
		
		this.id = id;
	}
	
	@Override
	public String getEntityID()
	{
		return id;
	}
}
