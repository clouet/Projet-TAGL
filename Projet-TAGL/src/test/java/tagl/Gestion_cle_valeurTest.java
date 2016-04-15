package tagl;

import static org.junit.Assert.*;

import java.util.ArrayList;

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
	public void testSetNullKey(){		
		assertEquals("test setnullkey", 0, gkey.set(null,"2"));
	}
	@Test
	public void testSetNullVal(){		
		assertEquals("test setnullval", 0, gkey.set("test",null));
	}
	
	@Test
	public void testSetEmptyKey(){		
		assertEquals("test setnullkey", 1, gkey.set("","2"));
	}
	@Test
	public void testSetEmptyVal(){		
		assertEquals("test setnullval", 1, gkey.set("test",""));
	}
	@Test
	public void testGetBadKey() throws WrongTypeValueException{	
		gkey.set("\0test","2");
		assertEquals("test getbadllkey", "2", gkey.get("\0test"));
	}
	@Test
	public void testGetSet() throws WrongTypeValueException{
		gkey.set("test","3");
		assertEquals("test getset", "3", gkey.get("test"));
	}
	@Test
	public void testGetUnset() throws WrongTypeValueException{
		assertEquals("test getunset", null, gkey.get("tomate"));
	}
	@Test
	public void testGetChanged() throws WrongTypeValueException{
		gkey.set("test","1");
		gkey.set("test","2");
		assertEquals("testGetChanged", "2", gkey.get("test"));
	}
	@Test
	public void testSetnxAlreadySet(){
		gkey.set("test","1");
		assertEquals("testSetnxAlreadySet", 0, gkey.setnx("test", "2"));
	}
	@Test
	public void testsetnxnoSet(){
		assertEquals("testsetnxnoSet", 1, gkey.setnx("test", "2"));
	}
	
	
	
	
	
	
	


	@Test
	public void testSetnxNullKey(){		
		assertEquals("test setnxnullkey", 0, gkey.setnx(null,"2"));
	}
	@Test
	public void testSetnxNullVal(){		
		assertEquals("test setnxnullval", 0, gkey.setnx("test",null));
	}
	
	@Test
	public void testSetnxEmptyKey(){		
		assertEquals("test setnxEmptykey", 1, gkey.setnx("","2"));
	}
	@Test
	public void testSetnxEmptyVal(){		
		assertEquals("test setnxEmptyval", 1, gkey.setnx("test",""));
	}

	@Test
	public void testGetSetnx() throws WrongTypeValueException{
		gkey.setnx("test","3");
		assertEquals("test getset", "3", gkey.get("test"));
	}

	@Test
	public void testGetChangedSetnx() throws WrongTypeValueException{
		gkey.setnx("test","1");
		gkey.setnx("test","2");
		assertEquals("testGetChanged", "1", gkey.get("test"));
	}
	
	@Test
	public void testDelReturnValueNormal(){
		gkey.set("test","1");
		assertEquals("testDelReturnValueNormal", 1, gkey.del("test"));
	}
	
	@Test
	public void testDelReturnValueNotSet(){
		assertEquals("testDelReturnValueNotSet", 0, gkey.del("test"));
	}
	@Test
	public void testGetDeleted() throws WrongTypeValueException{
		gkey.set("test","1");
		gkey.del("test");
		assertEquals("testDelReturnValueNormal", null, gkey.get("test"));
	}
	@Test
	public void testGetnullKey(){
		assertEquals("testGetnullKey", 0, gkey.del(null));
	}
	@Test
	public void testGetEmptyKey(){
		gkey.set("", "12 virgule 2");
		assertEquals("testGetEmptyKey", 1, gkey.del(""));
	}
	
	
	@Test
	public void testIncrNotSet() throws NumberFormatException, OverFlowException{
		assertEquals("testIncrNotSet", 1, gkey.incr("test"));
	}
	@Test
	public void testIncrSet() throws NumberFormatException, OverFlowException{
		gkey.set("test", "11");
		assertEquals("testIncrSet", 12, gkey.incr("test"));
	}
	@Test (expected = NumberFormatException.class)
	public void testIncrSetWitNan() throws NumberFormatException, OverFlowException{
		gkey.set("test", "blzbla1");
		gkey.incr("test");
	}
	@Test
	public void testIncrSetNeg() throws NumberFormatException, OverFlowException{
		gkey.set("test", "-11");
		assertEquals("testIncrSet", -10, gkey.incr("test"));
	}
	
	@Test (expected = OverFlowException.class)
	public void testIncrSetIntMax() throws NumberFormatException, OverFlowException{
		gkey.set("test",""+Integer.MAX_VALUE);
		gkey.incr("test");
	}
	
	@Test (expected = NumberFormatException.class)
	public void testIncrHugeNumber() throws NumberFormatException, OverFlowException{
		gkey.set("test","1000000000000000000000000000000000000000000000000000000000000000000000000");
		gkey.incr("test");
	}
	
	@Test (expected = NumberFormatException.class)
	public void testIncrHugeNegNumber() throws NumberFormatException, OverFlowException{
		gkey.set("test","-1000000000000000000000000000000000000000000000000000000000000000000000000");
		gkey.incr("test");
	}
	
	
	
	@Test
	public void testDecrNotSet() throws NumberFormatException, UnderFlowException{
		assertEquals("testIncrNotSet", -1, gkey.decr("test"));
	}
	@Test
	public void testDecrSet() throws NumberFormatException, UnderFlowException{
		gkey.set("test", "11");
		assertEquals("testIncrSet", 10, gkey.decr("test"));
	}
	@Test (expected = NumberFormatException.class)
	public void testDecrSetWitNan() throws NumberFormatException, UnderFlowException{
		gkey.set("test", "blzbla1");
		gkey.decr("test");
	}
	@Test
	public void testDecrSetNeg() throws NumberFormatException, UnderFlowException{
		gkey.set("test", "-11");
		assertEquals("testIncrSet", -12, gkey.decr("test"));
	}
	
	@Test (expected = UnderFlowException.class)
	public void testDecrSetIntMax() throws NumberFormatException, UnderFlowException{
		gkey.set("test",""+Integer.MIN_VALUE);
		gkey.decr("test");
	}
	
	@Test (expected = NumberFormatException.class)
	public void testDecrHugeNumber() throws NumberFormatException, UnderFlowException{
		gkey.set("test","1000000000000000000000000000000000000000000000000000000000000000000000000");
		gkey.decr("test");
	}
	
	@Test (expected = NumberFormatException.class)
	public void testDecrHugeNegNumber() throws NumberFormatException, UnderFlowException{
		gkey.set("test","-1000000000000000000000000000000000000000000000000000000000000000000000000");
		gkey.decr("test");
	}
	
	@Test
	public void testIncrByNotSet() throws NumberFormatException, OverFlowException, UnderFlowException{
		assertEquals("testIncrByNotSet", 12, gkey.incrBy("test", 12));
	}
	@Test
	public void testIncrBySet() throws NumberFormatException, OverFlowException, UnderFlowException{
		gkey.set("test", "11");
		assertEquals("testIncrBySet", 23, gkey.incrBy("test", 12));
	}
	
	@Test
	public void testIncrByNeg() throws NumberFormatException, OverFlowException, UnderFlowException{
		gkey.set("test", "11");
		assertEquals("testIncrBySet", -1, gkey.incrBy("test", -12));
	}
	
	@Test
	public void testIncrBy0Set() throws NumberFormatException, OverFlowException, UnderFlowException{
		gkey.set("test", "11");
		assertEquals("testIncrBySet", 11, gkey.incrBy("test", 0));
	}
	
	@Test
	public void testIncrBy1Set() throws NumberFormatException, OverFlowException, UnderFlowException{
		gkey.set("test", "11");
		assertEquals("testIncrBySet", 12, gkey.incrBy("test", 1));
	}

	@Test (expected = NumberFormatException.class)
	public void testIncrBySetWitNan() throws NumberFormatException, OverFlowException{
		gkey.set("test", "blzbla1");
		gkey.incr("test");
	}
	@Test
	public void testIncrBySetNeg() throws NumberFormatException, OverFlowException, UnderFlowException{
		gkey.set("test", "-11");
		assertEquals("testIncrSet", 0, gkey.incrBy("test",11));
	}
	
	@Test (expected = OverFlowException.class)
	public void testIncrBySetIntMax() throws NumberFormatException, OverFlowException, UnderFlowException{
		gkey.set("test",""+Integer.MAX_VALUE);
		gkey.incrBy("test",12);
	}
	
	@Test (expected = OverFlowException.class)
	public void testIncrBySetIntMax2() throws NumberFormatException, OverFlowException, UnderFlowException{
		gkey.set("test",""+12);
		gkey.incrBy("test",Integer.MAX_VALUE);
	}
	
	
	@Test (expected = NumberFormatException.class)
	public void testIncrByHugeNumber() throws NumberFormatException, OverFlowException, UnderFlowException{
		gkey.set("test","1000000000000000000000000000000000000000000000000000000000000000000000000");
		gkey.incrBy("test",12);
	}
	
	@Test (expected = NumberFormatException.class)
	public void testIncrByHugeNegNumber() throws NumberFormatException, OverFlowException, UnderFlowException{
		gkey.set("test","-1000000000000000000000000000000000000000000000000000000000000000000000000");
		gkey.incrBy("test",12);
	}
	


	
	
	
	@Test
	public void testDecrByNotSet() throws NumberFormatException, OverFlowException, UnderFlowException{
		assertEquals("testIncrByNotSet", -12, gkey.decrBy("test", 12));
	}
	@Test
	public void testDecrBySet() throws NumberFormatException, OverFlowException, UnderFlowException{
		gkey.set("test", "11");
		assertEquals("testIncrBySet", -1, gkey.decrBy("test", 12));
	}
	
	@Test
	public void testDecrByNeg() throws NumberFormatException, OverFlowException, UnderFlowException{
		gkey.set("test", "11");
		assertEquals("testIncrBySet", 23, gkey.decrBy("test", -12));
	}
	
	@Test
	public void testDecrBy0Set() throws NumberFormatException, OverFlowException, UnderFlowException{
		gkey.set("test", "11");
		assertEquals("testIncrBySet", 11, gkey.decrBy("test", 0));
	}
	
	@Test
	public void testDecrBy1Set() throws NumberFormatException, OverFlowException, UnderFlowException{
		gkey.set("test", "11");
		assertEquals("testIncrBySet", 10, gkey.decrBy("test", 1));
	}

	@Test (expected = NumberFormatException.class)
	public void testDecrBySetWitNan() throws NumberFormatException, OverFlowException, UnderFlowException{
		gkey.set("test", "blzbla1");
		gkey.decr("test");
	}
	@Test
	public void testDecrBySetNeg() throws NumberFormatException, OverFlowException, UnderFlowException{
		gkey.set("test", "-11");
		assertEquals("testIncrSet", -22, gkey.decrBy("test",11));
	}
	
	@Test (expected = OverFlowException.class)
	public void testDecrBySetIntMax() throws NumberFormatException, OverFlowException, UnderFlowException{
		gkey.set("test",""+Integer.MAX_VALUE);
		gkey.decrBy("test",-12);
	}
	
	@Test (expected = UnderFlowException.class)
	public void testDecrBySetIntMax2() throws NumberFormatException, OverFlowException, UnderFlowException{
		gkey.set("test","-12");
		gkey.decrBy("test",Integer.MAX_VALUE);
	}
	
	
	@Test (expected = NumberFormatException.class)
	public void testDecrByHugeNumber() throws NumberFormatException, OverFlowException, UnderFlowException{
		gkey.set("test","1000000000000000000000000000000000000000000000000000000000000000000000000");
		gkey.decrBy("test",12);
	}
	
	@Test (expected = NumberFormatException.class)
	public void testDecrByHugeNegNumber() throws NumberFormatException, OverFlowException, UnderFlowException{
		gkey.set("test","-1000000000000000000000000000000000000000000000000000000000000000000000000");
		gkey.decrBy("test",12);
	}
	
	@Test
	public void testExist(){	
		gkey.set("test", "valeur");
		assertEquals("test setnullkey", 1, gkey.exists("test"));
	}
	@Test
	public void testNoExist(){	
		assertEquals("test setnullkey", 0, gkey.exists("test"));
	}
	
	@Test
	public void testExisted(){	
		gkey.set("test", "valeur");
		gkey.del("test");
		assertEquals("test setnullkey", 0, gkey.exists("test"));
	}
	
	@Test
	public void testExistVal(){	
		gkey.set("test", "valeur");
		assertEquals("test setnullkey", 0, gkey.exists("valeur"));
	}
	
	
	@Test (expected = KeyNotExistsException.class)
	public void testRenamenotset() throws SameNameException, KeyNotExistsException{	
		gkey.rename("test", "valeur");
	}
	
	@Test 
	public void testRename() throws SameNameException, KeyNotExistsException{	
		gkey.set("test", "valeur");
		assertEquals("test testRename", 1, gkey.rename("test", "newname"));
	}
	
	@Test 
	public void testRenameNewNameUsed() throws SameNameException, KeyNotExistsException{	
		gkey.set("test", "valeur");
		gkey.set("newname", "valeur");
		assertEquals("test testRename", 1, gkey.rename("test", "newname"));
	}

	

	@Test (expected = SameNameException.class)
	public void testRenameSameNameException() throws SameNameException, KeyNotExistsException{
		gkey.set("test", "valeur");
		gkey.rename("test", "test");
	}
	
	@Test
	public void testRenameSupressionOldKey() throws SameNameException, KeyNotExistsException, WrongTypeValueException{
		gkey.set("test", "valeur1");
		gkey.set("new", "valeur2");
		gkey.rename("test","new");
		assertEquals("testRenameSupressionOldKey", "valeur1", gkey.get("new"));
	}
	
	

	@Test
	public void testRenamenx() throws SameNameException, KeyNotExistsException, WrongTypeValueException{
		gkey.set("test", "valeur1");
		gkey.renamenx("test", "newname");
		assertEquals("testRenamenx", "valeur1", gkey.get("newname"));
	}
	
	@Test
	public void testRenamenxNewNameExists() throws SameNameException, KeyNotExistsException{
		gkey.set("test", "valeur");
		gkey.set("new", "valeur2");
		assertEquals("testRenamenxNewNameExists", 0, gkey.renamenx("test", "new"));
	}
	
	@Test (expected = SameNameException.class)
	public void testRenamenxSameNameException() throws SameNameException, KeyNotExistsException{
		gkey.set("test", "valeur");
		gkey.renamenx("test", "test");
	
	}
	
	@Test (expected = KeyNotExistsException.class)
	public void testRenamenxKeyNotExistsException() throws SameNameException, KeyNotExistsException{
		gkey.renamenx("alpha", "beta");
	}
	
	
	//Debut des tests sur la gestion de liste de valeurs
	@Test
	public void testCreationListWithRpush() throws WrongTypeValueException{
		ArrayList<String> list2 = new ArrayList<String>();
		list2.add("value2");
		assertEquals("testCreationListWithRpush", 1, gkey.rpush("list", list2));
	}
	
	@Test (expected = WrongTypeValueException.class)
	public void testRpushWrongTypeValueException() throws WrongTypeValueException{
		gkey.set("list1", "value1");
		ArrayList<String> list2 = new ArrayList<String>();
		list2.add("value2");
		gkey.rpush("list1", list2);
	}
	
	@Test
	public void testAdditionNewElementRpush() throws WrongTypeValueException{
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("value1");
		gkey.rpush("liste", list1);
		ArrayList<String> list2 = new ArrayList<String>();
		list2.add("value2");
		list2.add("value3");
		assertEquals("testAdditionNewElementRpush", 3, gkey.rpush("liste", list2));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
