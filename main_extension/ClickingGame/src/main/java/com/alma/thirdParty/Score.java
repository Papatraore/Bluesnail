package com.alma.thirdParty;

import com.alma.app.IIScore;

public class Score implements IIScore {
	
	private String name;
	private int value;
	
	
	/* (non-Javadoc)
	 * @see com.alma.thirdParty.IScore#getName()
	 */
	/* (non-Javadoc)
	 * @see com.alma.thirdParty.IScore#getName()
	 */
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see com.alma.thirdParty.IScore#setName(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see com.alma.thirdParty.IScore#setName(java.lang.String)
	 */
	public void setName(String name) {
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see com.alma.thirdParty.IScore#getValue()
	 */
	/* (non-Javadoc)
	 * @see com.alma.thirdParty.IScore#getValue()
	 */
	public int getValue() {
		return value;
	}
	/* (non-Javadoc)
	 * @see com.alma.thirdParty.IScore#setValue(int)
	 */
	/* (non-Javadoc)
	 * @see com.alma.thirdParty.IScore#setValue(int)
	 */
	public void setValue(int value) {
		this.value = value;
	}
	
	
	public Score() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Score(String name, int value) {
		super();
		this.name = name;
		this.value = value;
	}
	/* (non-Javadoc)
	 * @see com.alma.thirdParty.IScore#toString()
	 */
	@Override
	public String toString() {
		return "Score [name=" + name + ", value=" + value + "]";
	}

	
	
	
	
}
