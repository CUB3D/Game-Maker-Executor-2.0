package call.gamerunner.main;

import call.file.layout.Value;

public class ValueWrapper
{
	private Value value;
	
	public ValueWrapper(Value v)
	{
		this.value = v;
	}
	
	public Value getValue()
	{
		return value;
	}
}
