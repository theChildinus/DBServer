package net.jumperz.util;

public class MPair
{
private String key;
private String value;
//--------------------------------------------------------------------------------
public MPair()
{
}
//--------------------------------------------------------------------------------
public MPair( String k, String v )
{
key = k;
value = v;
}
//--------------------------------------------------------------------------------
public String getKey()
{
return key;
}
//--------------------------------------------------------------------------------
public String getValue()
{
return value;
}
//--------------------------------------------------------------------------------
public void setKey( String k )
{
key = k;
}
//--------------------------------------------------------------------------------
public void setValue( String v )
{
value = v;
}
//--------------------------------------------------------------------------------
public String toString()
{
return key + "=" + value;
}
//--------------------------------------------------------------------------------
}