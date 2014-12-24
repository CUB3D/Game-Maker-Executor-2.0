package call.gamerunner.main;

import java.util.ArrayList;
import java.util.List;

import call.game.image.Sprite;


public class GameHelper
{
	public static SpriteWrapper getSprite(String name)
	{
		for(SpriteWrapper sw : GameRunnerMain.sprites)
			if(sw.getName().equalsIgnoreCase(name))
				return sw;

		return null;
	}

	public static List<SpriteWrapper> getSpritesByTag(String tag)
	{
		List<SpriteWrapper> lst = new ArrayList<SpriteWrapper>();

		for(SpriteWrapper sw : GameRunnerMain.sprites)
			if(sw.getTag().equalsIgnoreCase(tag))
				lst.add(sw);

		return lst;
	}

	public static void addSprite(String name, String tag, Sprite s)
	{
		SpriteWrapper sw = new SpriteWrapper(s, false, name, tag);

		addSprite(sw);
	}

	public static void addSprite(SpriteWrapper sprite)
	{
		GameRunnerMain.sprites.add(sprite);
	}

	// initiate  

	public static SpriteWrapper initiateSprite(SpriteWrapper sw, String newName)
	{	
		if(!sw.isPrefab())
			return sw;

		Sprite newSprite = (Sprite) sw.getSprite().clone();

		SpriteWrapper newWrapper = new SpriteWrapper(newSprite, false, newName, sw.getTag());

		addSprite(newWrapper);

		return newWrapper;
	}

	public static SpriteWrapper initiateSprite(String name, String newName)
	{
		return initiateSprite(getSprite(name), newName);
	}



	public static EntityWrapper getEntity(String name)
	{
		for(EntityWrapper ew : GameRunnerMain.entitys)
			if(ew.getName().equalsIgnoreCase(name))
				return ew;

		return null;
	}
	
	public static List<EntityWrapper> getEntitysByTag(String tag)
	{
		List<EntityWrapper> lst = new ArrayList<EntityWrapper>();

		for(EntityWrapper sw : GameRunnerMain.entitys)
			if(sw.getTag().equalsIgnoreCase(tag))
				lst.add(sw);

		return lst;
	}

	public static void addEntity(String name, String tag, BasicEntity e)
	{
		EntityWrapper ew = new EntityWrapper(e, false, name, tag);

		addEntity(ew);
	}

	public static void addEntity(EntityWrapper ew)
	{
		GameRunnerMain.entitys.add(ew);
	}

	public static EntityWrapper initiateEntity(EntityWrapper ew, String newName)
	{
		if(!ew.isPrefab())
			return ew;

		BasicEntity newEntity = (BasicEntity) ew.getEntity().clone();

		EntityWrapper newWrapper = new EntityWrapper(newEntity, false, newName, ew.getTag());

		addEntity(newWrapper);

		return newWrapper;
	}
	
	public static EntityWrapper initiateEntity(String name, String newName)
	{
		return initiateEntity(getEntity(name), newName);
	}


	public static ValueWrapper getGlobal(String name)
	{
		return GameRunnerMain.vars.get(name);
	}
}
