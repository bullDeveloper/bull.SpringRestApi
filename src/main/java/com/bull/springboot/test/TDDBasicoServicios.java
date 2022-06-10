package com.bull.springboot.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.bull.springboot.application.model.Transaction;
import com.bull.springboot.repository.MemoryDatabaseImpl;
import com.bull.springboot.repository.exeption.CustomException;

class TDDBasicoServicios {

	MemoryDatabaseImpl memoryDatabase = new MemoryDatabaseImpl();

	@Test
	void testServicioInsercionesRepository() {

		try {
			memoryDatabase.insertTransaction(new Transaction(10, 5000, "cars", 0));
			memoryDatabase.insertTransaction(new Transaction(11, 10000, "shopping", 10));
			memoryDatabase.insertTransaction(new Transaction(12, 5000, "shopping", 11));
			assertTrue(true);
		} catch (CustomException e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	void testServicioBusquedaRepository() {
		
		try {
			memoryDatabase.insertTransaction(new Transaction(10, 5000, "cars", 0));
			memoryDatabase.insertTransaction(new Transaction(11, 10000, "shopping", 10));
			memoryDatabase.insertTransaction(new Transaction(12, 5000, "shopping", 11));
			assertTrue(true);
		} catch (CustomException e) {
			e.printStackTrace();
			fail();
		}
		
		int cantidad = 0;

		cantidad = memoryDatabase.findByType("cars").size();
		assertTrue(cantidad == 1);

		cantidad = memoryDatabase.findByType("shopping").size();
		assertTrue(cantidad == 2);

	}

	@Test
	void testServicioSumaTransitivaRepository() {

		try {
			memoryDatabase.insertTransaction(new Transaction(10, 5000, "cars", 0));
			memoryDatabase.insertTransaction(new Transaction(11, 10000, "shopping", 10));
			memoryDatabase.insertTransaction(new Transaction(12, 5000, "shopping", 11));
			assertTrue(true);
		} catch (CustomException e) {
			e.printStackTrace();
			fail();
		}
		
		Double suma = Double.valueOf(0);

		suma = memoryDatabase.transitiveRelations(Long.valueOf(10));
		assertTrue(suma == 20000);

		suma = memoryDatabase.transitiveRelations(Long.valueOf(11));
		assertTrue(suma == 15000);

	}
	
	@Test
	void testServicioPutRepository() {

		try {
			memoryDatabase.insertTransaction(new Transaction(10, 5000, "cars", 0));
			memoryDatabase.insertTransaction(new Transaction(11, 10000, "shopping", 10));
			memoryDatabase.insertTransaction(new Transaction(12, 5000, "shopping", 11));
			assertTrue(true);
		} catch (CustomException e) {
			e.printStackTrace();
			fail();
		}
		
		
		Boolean update = null;
		
		update = memoryDatabase.updateOrInsertTransaction(new Transaction(10, 5000, "cars", 12));
		assertTrue(update);

		update = memoryDatabase.updateOrInsertTransaction(new Transaction(13, 5000, "cars", 12));
		assertTrue(!update);

	}

}
