package tagl;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class Gestion_cle_valeurTest {
	Gestion_cle_valeur gkey;
	
	@Before
	public void setUp() throws Exception {
		gkey= new Gestion_cle_valeur();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testNewSet(){
		assertEquals("test set", 1, gkey.set("test","2"));
	}
	@Test
	public void testAlreadySet(){
		gkey.set("test","2");
		assertEquals("test unset", 1, gkey.set("test","3"));
	}
	@Test
	public void testGetSetted(){
		gkey.set("test","3");
		assertEquals("test getsetted", "3", gkey.get("test"));
	}
	@Test
	public void testGetUnsetted(){
		assertEquals("test getunsetted", null, gkey.get("tomate"));
	}
}
