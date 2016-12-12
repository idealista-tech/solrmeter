package com.idealista.solrmeter.extractor;

import org.apache.solr.common.SolrInputDocument;

import com.idealista.solrmeter.BaseTestCase;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class DocumentExtractorTestCase extends BaseTestCase {

	public void testSingleDoc(DocumentExtractorSpy extractor) throws FileNotFoundException {

		assertEquals(1, extractor.getParsedDocuments().size());
		
		for(int i = 0; i < 10; i++) {
			SolrInputDocument document = extractor.getRandomDocument();
	//		fieldName1=value1;fieldName2=value2;fieldName3=value3
			Iterator<Object> values = document.getFieldValues("fieldName1").iterator();
			assertEquals("value1", values.next());
			assertEquals("value2", values.next());
			assertEquals("value2", document.getFieldValue("fieldName2"));
			assertEquals("value3", document.getFieldValue("fieldName3"));
		}
	}
	
	public void testManyDocs(DocumentExtractorSpy extractor) throws FileNotFoundException {

		assertEquals(21, extractor.getParsedDocuments().size());
		Set<Integer> set = new HashSet<Integer>();
		for(int i = 0; i < 100; i++) {
			SolrInputDocument document = extractor.getRandomDocument();
	//		fieldName1=value1;fieldName2=value2;fieldName3=value3
			assertNotNull(document.getFieldValue("fieldName1"));
			set.add(Integer.parseInt((String)document.getFieldValue("fieldName1")));
			assertEquals("value2", document.getFieldValue("fieldName2"));
			assertEquals("value3", document.getFieldValue("fieldName3"));
		}

		assertTrue(set.size() > 1 && set.size() <=21);
	}
	
	public void testLoadDocuments(DocumentExtractorSpy extractor) throws FileNotFoundException {
		List<SolrInputDocument> documents = extractor.getParsedDocuments();
		assertEquals(5, documents.size());
		SolrInputDocument document = documents.get(0);
		assertEquals("1", document.getFieldValue("documentId"));
		assertEquals("11", document.getFieldValue("entryId"));
		assertEquals("ABC", document.getFieldValue("type"));
		assertEquals("F", document.getFieldValue("active"));
		assertEquals("2", document.getFieldValue("value"));
		assertEquals("Mon Mar 06 00:00:00 ART 2006", document.getFieldValue("date"));
	}
	
	public void testEscapedChars(DocumentExtractorSpy extractor) throws FileNotFoundException {
		List<SolrInputDocument> documents = extractor.getParsedDocuments();
		assertEquals(5, documents.size());
		SolrInputDocument document = documents.get(0);
		assertEquals("1", document.getFieldValue("documentId"));
		assertEquals("11;2", document.getFieldValue("entryId"));
		assertEquals("ABC\\", document.getFieldValue("type"));
		assertEquals("F\\\\", document.getFieldValue("active"));
		assertEquals("2:5", document.getFieldValue("value"));
		assertEquals("Mon Mar 06 00:00:00 ART 2006", document.getFieldValue("date"));
	}
}
