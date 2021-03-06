/**
 * Copyright Plugtree LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.idealista.solrmeter.model.extractor;

import java.util.List;

import com.idealista.solrmeter.model.FieldExtractor;


public class FileFieldExtractor implements FieldExtractor{

	private FileStringExtractor fileExtractor;
	
	public FileFieldExtractor(String filePath) {
		fileExtractor = new FileStringExtractor(filePath);
	}

	@Override
	public List<String> getFields() {
		return fileExtractor.getAllStrings();
	}

	@Override
	public String getRandomFacetField() {
		return fileExtractor.getRandomString();
	}

	/**
	 * Considering all fields as 'facet-ables'
	 */
	@Override
	public List<String> getFacetFields() {
		return fileExtractor.getAllStrings();
	}

}
