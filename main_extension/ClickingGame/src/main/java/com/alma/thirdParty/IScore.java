package com.alma.thirdParty;

public interface IScore {

	/* (non-Javadoc)
	 * @see com.alma.thirdParty.IScore#getName()
	 */
	public abstract String getName();

	/* (non-Javadoc)
	 * @see com.alma.thirdParty.IScore#setName(java.lang.String)
	 */
	public abstract void setName(String name);

	/* (non-Javadoc)
	 * @see com.alma.thirdParty.IScore#getValue()
	 */
	public abstract int getValue();

	/* (non-Javadoc)
	 * @see com.alma.thirdParty.IScore#setValue(int)
	 */
	public abstract void setValue(int value);

	public abstract String toString();

}