package com.idealista.solrmeter.mock;

import com.idealista.solrmeter.model.operation.Operation;

public class MockOperation implements Operation {
	
	private int executions = 0;

	@Override
	public boolean execute() {
		executions++;
		return true;
	}
	
	public int getExecutionTimes() {
		return executions;
	}

}
