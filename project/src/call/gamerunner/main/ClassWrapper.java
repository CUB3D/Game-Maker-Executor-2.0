package call.gamerunner.main;

import java.lang.reflect.Method;

import call.game.main.ClassUtils;

public class ClassWrapper
{
	private Class<?> clazz;
	private Object instance;
	private Method updateHook;
	private Method preRenderHook;
	private Method postRenderHook;

	public ClassWrapper(Class<?> claz)
	{
		this.clazz = claz;

		try
		{
			instance = clazz.newInstance();
		}catch(Exception e) {e.printStackTrace();}

		updateHook = ClassUtils.getDefinedMethod("Update", clazz);
		
		preRenderHook = ClassUtils.getDefinedMethod("PreRender", clazz);
		postRenderHook = ClassUtils.getDefinedMethod("PostRender", clazz);
	}

	public void update()
	{
		call(updateHook);
	}

	public void preRender()
	{
		call(preRenderHook);
	}
	
	public void postRender()
	{
		call(postRenderHook);
	}
	
	public void call(Method m)
	{
		if(m != null)
		{
			try
			{
				m.invoke(instance, (Object[]) null);
			}catch(Exception e) {e.printStackTrace();}
		}
	}
}

