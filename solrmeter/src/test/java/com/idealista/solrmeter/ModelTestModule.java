package com.idealista.solrmeter;

import com.google.inject.AbstractModule;
import com.idealista.solrmeter.controller.StatisticsRepository;
import com.idealista.solrmeter.mock.StatisticsRepositorySpy;

public class ModelTestModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(StatisticsRepository.class).to(StatisticsRepositorySpy.class);
	}
}
