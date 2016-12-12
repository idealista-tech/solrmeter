package com.idealista.solrmeter.mock;

import com.idealista.solrmeter.controller.StatisticsRepository;

public class StatisticsRepositorySpy extends StatisticsRepository{

	public StatisticsRepositorySpy() {
		super(null);
	}

	protected void parseAvailableStatyistics() {
		
	}
}
