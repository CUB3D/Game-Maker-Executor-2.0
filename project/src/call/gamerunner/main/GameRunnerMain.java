package call.gamerunner.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;
import javax.media.opengl.GL2;

import call.file.api.CFile;
import call.file.layout.Element;
import call.file.layout.Value;
import call.game.entitys.BasicEntity;
import call.game.image.AnimatedSprite;
import call.game.image.Animation;
import call.game.image.BaseSprite;
import call.game.image.Image;
import call.game.image.Sprite;
import call.game.main.Define;
import call.game.main.GameSettings;
import call.game.main.Unknown;
import call.game.utils.AnimationIO;

public class GameRunnerMain
{
	public static List<SpriteWrapper> sprites = new ArrayList<SpriteWrapper>();
	public static List<EntityWrapper> entitys = new ArrayList<EntityWrapper>();
	public static Map<String, ClassWrapper> classes = new HashMap<String, ClassWrapper>();
	public static Map<String, ValueWrapper> vars = new HashMap<String, ValueWrapper>();

	public GameRunnerMain()
	{
		// load sprites
		File file = new File("Test.game");

		try
		{
			ZipFile game = new ZipFile(file);

			Enumeration<? extends ZipEntry> files = game.entries();

			ZipEntry dataEntry = game.getEntry("Sprites/Data.call");

			CFile data = new CFile(game.getInputStream(dataEntry));

			for(Element e : data.getElements())
			{
				if(e.getName().equals("Sprite"))
				{
					int x = e.getValue("X").getInt();
					int y = e.getValue("Y").getInt();
					String imgname = e.getValue("Image").getValue();
					String name = e.getValue("Name").getValue("NULL");
					boolean prefab = e.getValue("Prefab").getBoolean();
					String tag = e.getValue("Tag").getValue("NULL");

					ZipEntry image = game.getEntry("Sprites/" + imgname);

					BufferedImage bufimg = ImageIO.read(game.getInputStream(image));

					Image img = new Image(bufimg);

					Sprite s = new Sprite(x, y, img);

					SpriteWrapper sw = new SpriteWrapper(s, prefab, name, tag);

					sprites.add(sw);
				}
			}

			dataEntry = game.getEntry("Entitys/Data.call");

			data = new CFile(game.getInputStream(dataEntry));

			for(Element e : data.getElements())
			{
				if(e.getName().equals("Entity"))
				{
					int x = e.getValue("X").getInt();
					int y = e.getValue("Y").getInt();
					String imgname = e.getValue("Image").getValue();
					String name = e.getValue("Name").getValue("NULL");
					boolean prefab = e.getValue("Prefab").getBoolean();
					boolean animated = e.getValue("Animation").getBoolean();
					int id = e.getValue("ID").getInt(0);
					String tag = e.getValue("Tag").getValue("NULL");

					ZipEntry image = game.getEntry("Entitys/" + imgname);

					BaseSprite s = null;

					if(!animated)
					{
						BufferedImage bufimg = ImageIO.read(game.getInputStream(image));

						Image img = new Image(bufimg);

						s = new Sprite(x, y, img);
					}
					else
					{
						Animation ani = AnimationIO.loadAnimation(game.getInputStream(image));
						
						s = new AnimatedSprite(x, y, ani);
					}

					EntityWrapper sw = new EntityWrapper(new BasicEntity(s, id), prefab, name, tag);

					entitys.add(sw);
				}
			}
			
			dataEntry = game.getEntry("Data/Vars.call");

			data = new CFile(game.getInputStream(dataEntry));

			for(Element e : data.getElements())
			{
				if(e.getName().equals("Var"))
				{
					Value var = e.getValues().get(0);

					vars.put(var.getName(), new ValueWrapper(var));
				}
			}

			URL[] urls = null;

			try
			{
				urls = new URL[] {new File(file.getParentFile(), file.getName()).toURI().toURL()};
			} catch (Exception e) {e.printStackTrace();}

			URLClassLoader loader = new URLClassLoader(urls, ClassLoader.getSystemClassLoader());


			while(files.hasMoreElements())
			{
				ZipEntry entry = files.nextElement();

				if(!entry.isDirectory())
					if(entry.getName().endsWith(".class"))
					{
						String name = entry.getName();

						name = name.replaceAll("/", ".");
						name = name.replaceAll(".class", "");


						Class<?> claz = Class.forName(name, false, loader);

						ClassWrapper cw = new ClassWrapper(claz);
						
						classes.put(name, cw);
					}
			}

			game.close();
		}catch(Exception e) {e.printStackTrace();}
	}

	@Define("Render")
	public void render()
	{
		GL2 gl = Unknown.getGL();
		
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		
		for(ClassWrapper s : classes.values())
			s.preRender();
		
		for(SpriteWrapper s : sprites)
			if(!s.isPrefab())
			{
				BaseSprite ss = s.getSprite();
				ss.render();
			}
		
		for(EntityWrapper s : entitys)
			if(!s.isPrefab())
			{
				BasicEntity e = s.getEntity();
				
				e.render();
				
				BaseSprite ss = e.getSprite();
				
				if(ss instanceof AnimatedSprite)
				{
					((AnimatedSprite) ss).advance();
				}
			}
		
		for(ClassWrapper s : classes.values())
				s.postRender();
	}
	
	@Define("Update")
	public void update()
	{
		for(ClassWrapper s : classes.values())
			s.update();
	}

	public static void main(String[] args)
	{
		GameSettings gs = new GameSettings(512, 512, "Game", 120, 60);
		Unknown.init(GameRunnerMain.class, gs);
	}
}
