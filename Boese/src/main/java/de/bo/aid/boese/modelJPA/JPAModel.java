package de.bo.aid.boese.modelJPA;

public interface JPAModel {
	
	public boolean equals(Object obj);
	
	public boolean equalsWithoutIDAndFK(Object obj);

}
