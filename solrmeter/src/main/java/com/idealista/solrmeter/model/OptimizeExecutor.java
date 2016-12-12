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
package com.idealista.solrmeter.model;

/**
 * Interface that all optimize executors must implement.
 * @author tflobbe
 *
 */
public interface OptimizeExecutor {

	/**
	 * Asynchonize index optimization
	 */
	public void execute();

	/**
	 * Add a Statistic to observ this executor
	 * @param observer
	 */
	public void addStatistic(OptimizeStatistic observer);

	/**
	 * Indicates whether the index is being optimized or not at this time
	 */
	public boolean isOptimizing();

}