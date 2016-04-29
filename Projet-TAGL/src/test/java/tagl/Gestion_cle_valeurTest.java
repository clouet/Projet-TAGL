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
		assertEquals("test setnullkey", 0, gkey.set("","2"));
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
		assertEquals("test setnxEmptykey", 0, gkey.setnx("","2"));
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
		assertEquals("testGetEmptyKey", 0, gkey.del(""));
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
	
	@Test
	public void testCreationListLpush() throws WrongTypeValueException{
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("value1");
		assertEquals("testCreationListLpush", 1, gkey.lpush("list", list1));
	}
	
	@Test (expected = WrongTypeValueException.class)
	public void testLpushWrongTypeValueException() throws WrongTypeValueException{
		gkey.set("list1", "value1");
		ArrayList<String> list2 = new ArrayList<String>();
		list2.add("value2");
		gkey.lpush("list1", list2);
	}
	
	@Test
	public void testRpushxKeyNotExists() throws WrongTypeValueException{
		assertEquals("testRpushxKeyNotExists", 0, gkey.rpushx("carambar", "value2"));
	}
	
	@Test
	public void testRpushxKeyExists() throws WrongTypeValueException{
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("value1");
		gkey.rpush("test", list1);
		assertEquals("testRpushxKeyExists", 2, gkey.rpushx("test","value2"));
	}
	
	@Test
	public void testLpushxKeyNotExists() throws WrongTypeValueException{
		assertEquals("testLpushxKeyNotExists",0,gkey.lpushx("carambar", "value1"));
	}
	
	@Test
	public void testLpushxKeyExists() throws WrongTypeValueException{
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("value1");
		gkey.rpush("test", list1);
		assertEquals("testLpushxKeyExists", 2, gkey.lpushx("test","value2"));
	}
	
	@Test (expected = WrongTypeValueException.class)
	public void testLpushxWrongTypeValue() throws WrongTypeValueException{
		gkey.set("test", "valeur1");
		gkey.lpushx("test", "val");
	}
	
	@Test (expected = WrongTypeValueException.class)
	public void testRpushxWrongTypeValue() throws WrongTypeValueException{
		gkey.set("test", "valeur1");
		gkey.rpushx("test", "val");
	}
	
	@Test
	public void testRpop() throws WrongTypeValueException{
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("value1");
		list1.add("value2");
		gkey.rpush("test", list1);
		assertEquals("testRpop", "value2", gkey.rpop("test"));
	}
	
	@Test (expected = WrongTypeValueException.class)
	public void testRpopWrongTypeValue() throws WrongTypeValueException{
		gkey.set("list1", "value1");
		gkey.rpop("list1");
	}
	
	@Test
	public void testLpop() throws WrongTypeValueException {
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("value1");
		list1.add("value2");
		gkey.rpush("test", list1);
		assertEquals("testLpop", "value1", gkey.lpop("test"));
	}
	
	@Test
	public void testLpopRemove() throws WrongTypeValueException {
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("value1");
		list1.add("value2");
		gkey.rpush("test", list1);
		gkey.lpop("test");
		assertEquals("testLpopRemove", "value2",gkey.lpop("test"));
	}
	
	@Test
	public void testRpopRemove() throws WrongTypeValueException {
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("value1");
		list1.add("value2");
		gkey.rpush("test", list1);
		gkey.rpop("test");
		assertEquals("testLpopRemove", "value1",gkey.rpop("test"));
	}
	
	@Test
	public void testRpopEmptyList() throws WrongTypeValueException{
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("value1");
		gkey.rpush("test", list1);
		gkey.rpop("test");
		assertEquals("testRpopEmptuList", null, gkey.rpop("test"));
	}
	
	@Test
	public void testLpopEmptyList() throws WrongTypeValueException{
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("value1");
		gkey.rpush("test", list1);
		gkey.lpop("test");
		assertEquals("testRpopEmptuList", null, gkey.lpop("test"));
	
	}
	
	@Test
	public void testRenameList() throws WrongTypeValueException, SameNameException, KeyNotExistsException{
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("value1");
		gkey.rpush("test", list1);
		gkey.rename("test", "newname");
		assertEquals("testRenameList","value1", gkey.rpop("newname"));
	}
	
	
	//Tests pour la gestion de la memoire limitee
	
	@Test
	public void testTailleMax() throws WrongTypeValueException{
		gkey.set("a", "valeur");
		for(int i = 0; i <= Gestion_cle_valeur.TAILLE_MAX; i++){
			gkey.set(String.valueOf(i), "aaaa");
		}
		assertEquals("testTailleMax", null, gkey.get("a"));
	}
	
	@Test
	public void testTailleMaxSameKey() throws WrongTypeValueException{
		gkey.set("a", "valeur");
		for(int i = 0; i <= Gestion_cle_valeur.TAILLE_MAX; i++){
			gkey.set("b", "aaaa");
		}
		assertEquals("testTailleMaxSameKey", "valeur", gkey.get("a"));
	}
	
	@Test
	public void testTailleMaxRefreshAgeGet() throws WrongTypeValueException{
		gkey.set("a", "valeur");
		for(int i = 0; i <= Gestion_cle_valeur.TAILLE_MAX; i++){
			gkey.set(String.valueOf(i), "aaaa");
			gkey.get("a");
		}
		assertEquals("testTailleMax", "valeur", gkey.get("a"));
	}
	
	@Test
	public void testTailleMaxRefreshAgeSet() throws WrongTypeValueException{
		gkey.set("a", "valeur");
		for(int i = 0; i <= Gestion_cle_valeur.TAILLE_MAX; i++){
			gkey.set(String.valueOf(i), "aaaa");
			gkey.set("a", "valeur2");
		}
		assertEquals("testTailleMax", "valeur2", gkey.get("a"));
	}
	
	@Test
	public void testTailleMaxRefreshAgeIncr() throws WrongTypeValueException, NumberFormatException, OverFlowException{
		gkey.set("a", "1");
		for(int i = 0; i <= Gestion_cle_valeur.TAILLE_MAX; i++){
			gkey.set(String.valueOf(i), "aaaa");
			gkey.incr("a");
		}
		assertEquals("testTailleMax", String.valueOf(2+Gestion_cle_valeur.TAILLE_MAX), gkey.get("a"));
	}
	
	@Test
	public void testTailleMaxRefreshAgeDecr() throws WrongTypeValueException, NumberFormatException, UnderFlowException{
		gkey.set("a", "1");
		for(int i = 0; i <= Gestion_cle_valeur.TAILLE_MAX; i++){
			gkey.set(String.valueOf(i), "aaaa");
			gkey.decr("a");
		}
		assertEquals("testTailleMax", String.valueOf(-Gestion_cle_valeur.TAILLE_MAX), gkey.get("a"));
	}
	
	@Test
	public void testTailleMaxRefreshAgeRename() throws WrongTypeValueException, SameNameException, KeyNotExistsException{
		gkey.set("-1", "valeur");
		int i = 0;
		for(i = 0; i <= Gestion_cle_valeur.TAILLE_MAX; i++){
			gkey.set("b", "aaaa");
			gkey.rename(String.valueOf(i-1), String.valueOf(i));
		}
		assertEquals("testTailleMax", String.valueOf("valeur"), gkey.get(String.valueOf(i-1)));
	}
	
	@Test
	public void testTailleMaxRefreshAgeIncrBy() throws WrongTypeValueException, NumberFormatException, OverFlowException, UnderFlowException{
		gkey.set("a", "1");
		for(int i = 0; i <= Gestion_cle_valeur.TAILLE_MAX; i++){
			gkey.set(String.valueOf(i), "aaaa");
			gkey.incrBy("a",1);
		}
		assertEquals("testTailleMax", String.valueOf(2+Gestion_cle_valeur.TAILLE_MAX), gkey.get("a"));
	}
	
	@Test
	public void testTailleMaxRefreshAgeDecrBy() throws WrongTypeValueException, NumberFormatException, UnderFlowException, OverFlowException{
		gkey.set("a", "1");
		for(int i = 0; i <= Gestion_cle_valeur.TAILLE_MAX; i++){
			gkey.set(String.valueOf(i), "aaaa");
			gkey.decrBy("a",1);
		}
		assertEquals("testTailleMax", String.valueOf(-Gestion_cle_valeur.TAILLE_MAX), gkey.get("a"));
	}
	
	/**
	 * Ce test verifie le fonctionnement de la gestion de la taille maximale lorsque l'on a des listes et des
	 * cles simples.
	 * @throws WrongTypeValueException
	 */
	@Test
	public void testTailleMaxList() throws WrongTypeValueException{
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("value1");
		list1.add("value2");
		gkey.rpush("test", list1);
		for(int i = 0; i <= Gestion_cle_valeur.TAILLE_MAX; i++){
			gkey.set(String.valueOf(i), "aaaa");
		}
		assertEquals("testTailleMaxList", null, gkey.rpop("test"));
	}
	
	/**
	 * Ce test verifie le fonctionnement de la gestion de la taille maximale lorsque l'on utilise uniquement
	 * des listes.
	 * @throws WrongTypeValueException
	 */
	@Test
	public void testTailleMaxMultipleLists() throws WrongTypeValueException{
		ArrayList<String> list1 = new ArrayList<String>();
		list1.add("value1");
		gkey.rpush("test", list1);
		for(int i = 0; i <= Gestion_cle_valeur.TAILLE_MAX; i++){
			list1 = new ArrayList<String>();
			list1.add("value");
			gkey.rpush(String.valueOf(i), list1);
		}
		assertEquals("testTailleMaxMultipleLists", null, gkey.rpop("test"));
	}
	
	//Tests pour la structure de donnees avec des scores associes aux elements des listes
	
	@Test
	public void testzAddListScoresSuccess(){
		ListScore test = new ListScore(1,"value");
		ArrayList<ListScore> list = new ArrayList<ListScore>();
		list.add(test);
		int result = gkey.zAdd("cle", list);
		assertEquals("testzAddListScoresSuccess", 1, result);
	}
	
	@Test
	public void testzAddListScoresFailure(){
		int result = gkey.zAdd("cle", null);
		assertEquals("testzAddListScoresFailure", 0, result);
	}
	
	@Test
	public void testzAddTwoValues(){
		ListScore test = new ListScore(1,"value");
		ListScore test2 = new ListScore(2,"value2");
		ArrayList<ListScore> list = new ArrayList<ListScore>();
		list.add(test);
		list.add(test2);
		int result = gkey.zAdd("cle", list);
		assertEquals("testzAddTwoValues", 2, result);
	}
	
	@Test
	public void testzGet(){
		ListScore test = new ListScore(1,"value");
		ListScore test2 = new ListScore(2, "value2");
		ArrayList<ListScore> list = new ArrayList<ListScore>();
		list.add(test);
		list.add(test2);
		int result = gkey.zAdd("cle", list);
		ArrayList<String> listResult = gkey.zGet("cle");
		assertEquals("testzGet", "value2", listResult.get(0));
	}
	
	/**
	 * Ce test verifie que le tri par ordre croissant des scores sur les listes fonctionne.
	 */
	@Test
	public void testzGetSort(){
		ListScore test = new ListScore(1,"value");
		ListScore test4 = new ListScore(4, "value4");
		ListScore test2 = new ListScore(2, "value2");
		ArrayList<ListScore> list = new ArrayList<ListScore>();
		list.add(test);
		list.add(test4);
		list.add(test2);
		int result = gkey.zAdd("cle", list);
		ArrayList<String> listResult = gkey.zGet("cle");
		assertEquals("testzGet", "value", listResult.get(2));

	}

}
