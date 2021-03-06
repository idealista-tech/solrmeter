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
package com.idealista.stressTestScope;

/**
 * 
 * @author tflobbe
 *
 */
public class StressTestRegistry {
	
	private static long numberOfTests = 0;
	
	private static String stressTestId;

	/**
	 * Start a test scope
	 */
	public static void start() {
		restart();
	}
	
	/**
	 * Restart a test scope. Everything depending on StressTestScope will be reinstantiated.
	 */
	public static void restart(){
		stressTestId = String.valueOf(numberOfTests);
		numberOfTests++;
	}
	
	static String getStressTestId() {
		return stressTestId;
	}
}
